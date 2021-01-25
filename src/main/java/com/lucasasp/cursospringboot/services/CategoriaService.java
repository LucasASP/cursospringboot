package com.lucasasp.cursospringboot.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucasasp.cursospringboot.domain.Categoria;
import com.lucasasp.cursospringboot.repositories.CategoriaRepository;

//@Service serve para definir uma classe como pertencente à camada de Serviço da aplicação.
@Service
public class CategoriaService {
	
	//Injeção de dependência
	//@Autowired serve para instanciar uma classe automaticamente
	@Autowired 
	private CategoriaRepository repo;

	public Categoria buscar(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElse(null);
	}
}
