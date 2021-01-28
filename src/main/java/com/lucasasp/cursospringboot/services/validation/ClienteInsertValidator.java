package com.lucasasp.cursospringboot.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.lucasasp.cursospringboot.domain.Cliente;
import com.lucasasp.cursospringboot.domain.enums.TipoCliente;
import com.lucasasp.cursospringboot.dto.ClienteNewDTO;
import com.lucasasp.cursospringboot.repositories.ClienteRepository;
import com.lucasasp.cursospringboot.resources.exceptions.FieldMessage;
import com.lucasasp.cursospringboot.services.validation.utils.BR;

// no pdf tem um código pre definido
// ConstraintValidator recebe o nome da nossa anotação e o tipo da classe que vai aceitar nossa anotação
public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
	
	@Autowired
	private ClienteRepository repo;
	
	// nesse metodo podemos colocar alguma programação de inicialização
	@Override
	public void initialize(ClienteInsert ann) {
	}

	// nossa logica de validação deve ir dentro do método isValid
	// isValid é um método da interface ConstraintValidator que verifica se nosso tipo (no caso ClienteNewDTO) será válido ou nao
	// isValid retorna true se for valido e false se invalido e ele será percebido n argumento de requisição onde colocamos @Valid (como no metodo insert em ClienteResources)
	// @Valid agora também vai depender do que colocarmos no método isValid
	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		
		// vamos inserir os erros no metodo FieldMessage que criamos
		List<FieldMessage> list = new ArrayList<>();
		
		// inclua os testes aqui, inserindo erros na lista
		if (objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
		}

		if (objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
		}
		
		Cliente aux = repo.findByEmail(objDto.getEmail());
		if (aux != null) {
			list.add(new FieldMessage("email", "Email já existente"));
		}
		
		// aqui inserimos os erros correspondentes pegando as mensagens e o nome do campo
		// aqui nos permite transportar nossos erros personalizados para a lista de erros do framework, essa lista de erros será tratada e mostrada na resposta em nosso ExceptionHandler
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		
		// se não houver erro na lista de erros a validação sera true se nao false
		return list.isEmpty();
	}
}
