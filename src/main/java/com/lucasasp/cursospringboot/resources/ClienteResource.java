package com.lucasasp.cursospringboot.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

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

import com.lucasasp.cursospringboot.domain.Cliente;
import com.lucasasp.cursospringboot.dto.ClienteDTO;
import com.lucasasp.cursospringboot.dto.ClienteNewDTO;
import com.lucasasp.cursospringboot.services.ClienteService;

//@RestController define uma classe como um Controller REST do Spring MVC
//@RequestMapping anotação para definir mapeamentos
@RestController
@RequestMapping(value="/clientes")
public class ClienteResource {
	
	//@Autowired serve para instanciar uma classe automaticamente
	@Autowired 
	private ClienteService service;

	//@RequestMapping anotação para definir mapeamentos
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Cliente> find(@PathVariable Integer id) {    
		// ResponseEntity objeto complexo que vai ter códigos HTTP de resposta e varias informações do protocolo HTTP
		
		Cliente obj = service.find(id);
		
		//ResponseEntity.ok() informa que a operação ocorreu com sucesso e essa resposta vai ter como corpo o objeto que buscamos
		return ResponseEntity.ok().body(obj);
	}

	//Adiciona um cliente
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO objDto) {
		Cliente obj = service.fromDTO(objDto);
		obj = service.insert(obj);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}

	//Atualiza uma cliente
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO objDto, @PathVariable Integer id) {
		Cliente obj = service.fromDTO(objDto);
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
	public ResponseEntity<List<ClienteDTO>> findAll() {    
		List<Cliente> list = service.findAll();
		
		//Queremos apenas que as categorias sejam listadas, para isso criamos um DTO sem a lista dos produtos
		//Convertemos uma lista de Cliente em uma lista de ClienteDTO
		//Para isso percorremos toda a lista de Cliente a transformando em ClienteDTO (codigo abaixo transforma uma lista em outra)
		//Streams API (https://www.devmedia.com.br/java-streams-api-manipulando-colecoes-de-forma-eficiente/37630), recurso que oferece ao desenvolvedor a possibilidade de trabalhar com conjuntos de elementos de forma mais simples e com um número menor de linhas de código.
		List<ClienteDTO> listDto = list.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}

	//Buscando página
	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity<Page<ClienteDTO>> findPage(
			@RequestParam(value="page", defaultValue = "0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue = "24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue = "nome") String orderBy, 
			@RequestParam(value="direction", defaultValue = "ASC") String direction) {    
		//@RequestParam tornam parametros opcionais na url (ex: /categorias/page?page=0&linesPerPage=20)
		
		Page<Cliente> list = service.findPage(page, linesPerPage, orderBy, direction);
		//Page não precisa de stream
		Page<ClienteDTO> listDto = list.map(obj -> new ClienteDTO(obj));
		return ResponseEntity.ok().body(listDto);
	}

}
