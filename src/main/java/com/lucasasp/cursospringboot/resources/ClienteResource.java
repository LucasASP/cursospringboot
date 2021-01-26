package com.lucasasp.cursospringboot.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lucasasp.cursospringboot.domain.Cliente;
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
	public ResponseEntity<?> find(@PathVariable Integer id) {    
		// ResponseEntity objeto complexo que vai ter códigos HTTP de resposta e varias informações do protocolo HTTP
		
		Cliente obj = service.buscar(id);
		
		//ResponseEntity.ok() informa que a operação ocorreu com sucesso e essa resposta vai ter como corpo o objeto que buscamos
		return ResponseEntity.ok().body(obj);
	}

}
