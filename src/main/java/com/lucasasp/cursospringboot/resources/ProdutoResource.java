package com.lucasasp.cursospringboot.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lucasasp.cursospringboot.domain.Produto;
import com.lucasasp.cursospringboot.dto.ProdutoDTO;
import com.lucasasp.cursospringboot.resources.utils.URL;
import com.lucasasp.cursospringboot.services.ProdutoService;

//@RestController define uma classe como um Controller REST do Spring MVC
//@RequestMapping anotação para definir mapeamentos
@RestController
@RequestMapping(value="/produtos")
public class ProdutoResource {
	
	//@Autowired serve para instanciar uma classe automaticamente
	@Autowired 
	private ProdutoService service;

	//@RequestMapping anotação para definir mapeamentos
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Produto> find(@PathVariable Integer id) {    
		// ResponseEntity objeto complexo que vai ter códigos HTTP de resposta e varias informações do protocolo HTTP
		
		Produto obj = service.find(id);
		
		//ResponseEntity.ok() informa que a operação ocorreu com sucesso e essa resposta vai ter como corpo o objeto que buscamos
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Page<ProdutoDTO>> findPage(
			@RequestParam(value="nome", defaultValue="") String nome, 
			@RequestParam(value="categorias", defaultValue="") String categorias, 
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC") String direction) {
		String nomeDecoded = URL.decodeParam(nome);
		List<Integer> ids = URL.decodeIntList(categorias);
		Page<Produto> list = service.search(nomeDecoded, ids, page, linesPerPage, orderBy, direction);
		Page<ProdutoDTO> listDto = list.map(obj -> new ProdutoDTO(obj));  
		return ResponseEntity.ok().body(listDto);
	}

}
