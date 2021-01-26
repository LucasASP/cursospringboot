package com.lucasasp.cursospringboot.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.lucasasp.cursospringboot.domain.Categoria;
import com.lucasasp.cursospringboot.services.CategoriaService;

//@RestController define uma classe como um Controller REST do Spring MVC
//@RequestMapping anotação para definir mapeamentos
@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {
	
	//@Autowired serve para instanciar uma classe automaticamente
	@Autowired 
	private CategoriaService service;

	//@RequestMapping anotação para definir mapeamentos
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id) {    
		// ResponseEntity objeto complexo que vai ter códigos HTTP de resposta e varias informações do protocolo HTTP
		
		Categoria obj = service.buscar(id);
		
		//ResponseEntity.ok() informa que a operação ocorreu com sucesso e essa resposta vai ter como corpo o objeto que buscamos
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody Categoria obj) { //@RequestBody faz com que o objeto Json seja convertido para java automaticamente
		obj = service.insert(obj);
		
		//temos que retornar a uri do novo recurso criado
		//fromCurrentRequest pega a uri relativa (http://localhost:8080/categorias)
		//buildAndExpand atribui o valor do id
		//toUri converte ele para uri
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		
		//created gora o codigo 201 de criação e build gera a resposta
		return ResponseEntity.created(uri).build();
	}

}
