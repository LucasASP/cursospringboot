package com.lucasasp.cursospringboot.resources.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.lucasasp.cursospringboot.services.exceptions.DataIntegrityException;
import com.lucasasp.cursospringboot.services.exceptions.ObjectNotFoundException;

//classe auxiliar que intercepta as exceções 
//ResourceExceptionHandler é o manipulador de exceções dos recursos
//usamos a annotation @ControllerAdvice sempre que precisar concentrar algum tratamento que seria espalhado em todos os controllers.
//toda vez que um controller lançar uma exception, caso ninguém forneça um tratamento mais específico, ela vai cair no tratamento global do @ControllerAdvice
@ControllerAdvice
public class ResourceExceptionHandler {

	//esse metodo recebe a exceção ObjectNotFoundException e as informações da requisição e ela obrigatoriamente tem que ter essa assinatura
	//para o caso de objeto nao encontrado
	//@ExceptionHandler indica que é um tratador de exceção do tipo ObjectNotFoundException.class
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request){
		
		StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}
	
	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<StandardError> dataIntegrity(DataIntegrityException e, HttpServletRequest request){
		
		StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e, HttpServletRequest request){
		
		//tratando uma exeção de validação
		ValidationError err = new ValidationError(HttpStatus.BAD_REQUEST.value(), "Erro de validação", System.currentTimeMillis());
		
		//acessando todos os erros de campos que aconteceram na enxeção MethodArgumentNotValidException
		for(FieldError x : e.getBindingResult().getFieldErrors()) {
			err.addError(x.getField(), x.getDefaultMessage());
		}
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
}
