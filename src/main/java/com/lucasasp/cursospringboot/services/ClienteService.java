package com.lucasasp.cursospringboot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lucasasp.cursospringboot.domain.Cidade;
import com.lucasasp.cursospringboot.domain.Cliente;
import com.lucasasp.cursospringboot.domain.Endereco;
import com.lucasasp.cursospringboot.domain.enums.TipoCliente;
import com.lucasasp.cursospringboot.dto.ClienteDTO;
import com.lucasasp.cursospringboot.dto.ClienteNewDTO;
import com.lucasasp.cursospringboot.repositories.ClienteRepository;
import com.lucasasp.cursospringboot.repositories.EnderecoRepository;
import com.lucasasp.cursospringboot.services.exceptions.DataIntegrityException;
import com.lucasasp.cursospringboot.services.exceptions.ObjectNotFoundException;

//@Service serve para definir uma classe como pertencente à camada de Serviço da aplicação.
@Service
public class ClienteService {
	
	//Injeção de dependência
	//@Autowired serve para instanciar uma classe automaticamente
	@Autowired 
	private ClienteRepository repo;

	@Autowired
	private EnderecoRepository enderecoRepository;

	public Cliente find(Integer id) {
		/*
		 * Optional<Cliente> obj = repo.findById(id); return obj.orElse(null);
		 */
		
		Optional<Cliente> obj = repo.findById(id);
		//ObjectNotFoundException lança uma exceção personalizada, ela foi criada na camada de serviços e a camada rest (resources) que vai receber essa exceção
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}

	@Transactional //para garantir que ele vai salvar tanto o cliente quanto os endereços na mesma transação de dados
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		//salvamos o cliente e depois os endereços
		obj = repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}
	
	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há pedidos relacionados");
		}
	}

	public List<Cliente> findAll() {
		return repo.findAll();
	}
	
	//Paginação
	//Page é uma classe do Spring Data que encapsula informações e operações sobre paginações
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String OrderBy, String direction) {
		//PageRequest retorna uma pagina de dados. Objeto que vai preparar as informações para q se faça a consulta e retorne a pagina de dados
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), OrderBy);
		//Aqui automaticamente o findAll do JPA vai considerar PageRequest como argumento, uma sobrecarga de métodos, e ira nos retornar a pagina
		return repo.findAll(pageRequest);
	}
	
	//metodo auxiliar que instancia uma Cliente a partir de um DTO
	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);
	}
	
	//metodo auxiliar que instancia uma Cliente a partir de um DTO
	public Cliente fromDTO(ClienteNewDTO objDto) {
		Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipo()));
		Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDto.getTelefone1());
		if (objDto.getTelefone2()!=null) {
			cli.getTelefones().add(objDto.getTelefone2());
		}
		if (objDto.getTelefone3()!=null) {
			cli.getTelefones().add(objDto.getTelefone3());
		}
		return cli;
	}

	private void updateData(Cliente newObj, Cliente obj){
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
}
