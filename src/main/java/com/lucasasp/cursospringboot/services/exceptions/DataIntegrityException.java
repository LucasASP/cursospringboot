package com.lucasasp.cursospringboot.services.exceptions;

public class DataIntegrityException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public DataIntegrityException(String msg) {
		super(msg);
	}
	
	//Construtor que recebe uma outra exceção que seria a causa de alguma coisa que aconteceu antes
	public DataIntegrityException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
