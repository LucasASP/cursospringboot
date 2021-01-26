package com.lucasasp.cursospringboot.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucasasp.cursospringboot.domain.Cliente;
import com.lucasasp.cursospringboot.repositories.ClienteRepository;
import com.lucasasp.cursospringboot.services.exceptions.ObjectNotFoundException;

//@Service serve para definir uma classe como pertencente à camada de Serviço da aplicação.
@Service
public class ClienteService {
	
	//Injeção de dependência
	//@Autowired serve para instanciar uma classe automaticamente
	@Autowired 
	private ClienteRepository repo;

	public Cliente buscar(Integer id) {
		/*
		 * Optional<Cliente> obj = repo.findById(id); return obj.orElse(null);
		 */
		
		Optional<Cliente> obj = repo.findById(id);
		//ObjectNotFoundException lança uma exceção personalizada, ela foi criada na camada de serviços e a camada rest (resources) que vai receber essa exceção
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
}