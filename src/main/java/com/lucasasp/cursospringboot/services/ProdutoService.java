package com.lucasasp.cursospringboot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.lucasasp.cursospringboot.domain.Categoria;
import com.lucasasp.cursospringboot.domain.Produto;
import com.lucasasp.cursospringboot.repositories.CategoriaRepository;
import com.lucasasp.cursospringboot.repositories.ProdutoRepository;
import com.lucasasp.cursospringboot.services.exceptions.ObjectNotFoundException;

//@Service serve para definir uma classe como pertencente à camada de Serviço da aplicação.
@Service
public class ProdutoService {
	
	//Injeção de dependência
	//@Autowired serve para instanciar uma classe automaticamente
	@Autowired 
	private ProdutoRepository repo;
	
	@Autowired
	private CategoriaRepository categoriaRepository;

	public Produto find(Integer id) {
		/*
		 * Optional<Produto> obj = repo.findById(id); return obj.orElse(null);
		 */
		
		Optional<Produto> obj = repo.findById(id);
		//ObjectNotFoundException lança uma exceção personalizada, ela foi criada na camada de serviços e a camada rest (resources) que vai receber essa exceção
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName()));
	}
	
	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String OrderBy, String direction) {
		//PageRequest retorna uma pagina de dados. Objeto que vai preparar as informações para q se faça a consulta e retorne a pagina de dados
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), OrderBy);
		
		List<Categoria> categorias = categoriaRepository.findAllById(ids);
		return repo.search(nome, categorias, pageRequest);
		
	}
}
