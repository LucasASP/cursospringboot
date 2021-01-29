package com.lucasasp.cursospringboot.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.lucasasp.cursospringboot.domain.Cliente;
import com.lucasasp.cursospringboot.dto.ClienteDTO;
import com.lucasasp.cursospringboot.repositories.ClienteRepository;
import com.lucasasp.cursospringboot.resources.exceptions.FieldMessage;

// no pdf tem um código pre definido
// ConstraintValidator recebe o nome da nossa anotação e o tipo da classe que vai aceitar nossa anotação
public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {

	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private ClienteRepository repo;
	
	// nesse metodo podemos colocar alguma programação de inicialização
	@Override
	public void initialize(ClienteUpdate ann) {
	}

	// nossa logica de validação deve ir dentro do método isValid
	// isValid é um método da interface ConstraintValidator que verifica se nosso tipo (no caso ClienteNewDTO) será válido ou nao
	// isValid retorna true se for valido e false se invalido e ele será percebido n argumento de requisição onde colocamos @Valid (como no metodo insert em ClienteResources)
	// @Valid agora também vai depender do que colocarmos no método isValid
	@Override
	public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {
		
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriId = Integer.parseInt(map.get("id"));
		
		// vamos inserir os erros no metodo FieldMessage que criamos
		List<FieldMessage> list = new ArrayList<>();
		
		// inclua os testes aqui, inserindo erros na lista
		Cliente aux = repo.findByEmail(objDto.getEmail());
		if (aux != null && !aux.getId().equals(uriId)) {
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
