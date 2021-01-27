package com.lucasasp.cursospringboot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.lucasasp.cursospringboot.domain.Categoria;
import com.lucasasp.cursospringboot.repositories.CategoriaRepository;
import com.lucasasp.cursospringboot.services.exceptions.DataIntegrityException;
import com.lucasasp.cursospringboot.services.exceptions.ObjectNotFoundException;

//@Service serve para definir uma classe como pertencente à camada de Serviço da aplicação.
@Service
public class CategoriaService {
	
	//Injeção de dependência
	//@Autowired serve para instanciar uma classe automaticamente
	@Autowired 
	private CategoriaRepository repo;

	public Categoria find(Integer id) {
		/*
		 * Optional<Categoria> obj = repo.findById(id); return obj.orElse(null);
		 */
		
		Optional<Categoria> obj = repo.findById(id);
		//ObjectNotFoundException lança uma exceção personalizada, ela foi criada na camada de serviços e a camada rest (resources) que vai receber essa exceção
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}
	
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repo.save(obj);
	}
	
	public Categoria update(Categoria obj) {
		find(obj.getId());
		return repo.save(obj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos");
		}
	}

	public List<Categoria> findAll() {
		return repo.findAll();
	}
}
