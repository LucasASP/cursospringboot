package com.lucasasp.cursospringboot.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.lucasasp.cursospringboot.domain.Categoria;
import com.lucasasp.cursospringboot.dto.CategoriaDTO;
import com.lucasasp.cursospringboot.services.CategoriaService;

//@RestController define uma classe como um Controller REST do Spring MVC
//@RequestMapping anotação para definir mapeamentos
@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {
	
	//@Autowired serve para instanciar uma classe automaticamente
	@Autowired 
	private CategoriaService service;

	//Retorna uma categoria
	//@RequestMapping anotação para definir mapeamentos
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Categoria> find(@PathVariable Integer id) {    
		// ResponseEntity objeto complexo que vai ter códigos HTTP de resposta e varias informações do protocolo HTTP
		
		Categoria obj = service.find(id);
		
		//ResponseEntity.ok() informa que a operação ocorreu com sucesso e essa resposta vai ter como corpo o objeto que buscamos
		return ResponseEntity.ok().body(obj);
	}

	//Adiciona uma categoria
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody Categoria obj) { //@RequestBody faz com que o objeto Json seja convertido para java automaticamente
		obj = service.insert(obj);
		
		//temos que retornar a uri do novo recurso criado
		//fromCurrentRequest pega a uri relativa (http://localhost:8080/categorias)
		//buildAndExpand atribui o valor do id
		//toUri converte ele para uri
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		
		//created gera o codigo 201 de criação e build gera a resposta
		return ResponseEntity.created(uri).build();
	}

	//Atualiza uma categoria
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody Categoria obj, @PathVariable Integer id) {
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}

	//Deleta uma categoria
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) { 
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	//Buscando todas as categorias
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<CategoriaDTO>> findAll() {    
		List<Categoria> list = service.findAll();
		
		//Queremos apenas que as categorias sejam listadas, para isso criamos um DTO sem a lista dos produtos
		//Convertemos uma lista de Categoria em uma lista de CategoriaDTO
		//Para isso percorremos toda a lista de Categoria a transformando em CategoriaDTO (codigo abaixo transforma uma lista em outra)
		//Streams API (https://www.devmedia.com.br/java-streams-api-manipulando-colecoes-de-forma-eficiente/37630), recurso que oferece ao desenvolvedor a possibilidade de trabalhar com conjuntos de elementos de forma mais simples e com um número menor de linhas de código.
		List<CategoriaDTO> listDto = list.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}

	//Buscando página
	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity<Page<CategoriaDTO>> findPage(
			@RequestParam(value="page", defaultValue = "0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue = "24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue = "nome") String orderBy, 
			@RequestParam(value="direction", defaultValue = "ASC") String direction) {    
		//@RequestParam tornam parametros opcionais na url (ex: /categorias/page?page=0&linesPerPage=20)
		
		Page<Categoria> list = service.findPage(page, linesPerPage, orderBy, direction);
		//Page não precisa de stream
		Page<CategoriaDTO> listDto = list.map(obj -> new CategoriaDTO(obj));
		return ResponseEntity.ok().body(listDto);
	}
}
