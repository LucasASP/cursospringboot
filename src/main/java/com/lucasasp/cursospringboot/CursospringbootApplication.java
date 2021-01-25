package com.lucasasp.cursospringboot;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.lucasasp.cursospringboot.domain.Categoria;
import com.lucasasp.cursospringboot.repositories.CategoriaRepository;

//@SpringBootApplication configura uma aplicação Spring Boot
@SpringBootApplication
public class CursospringbootApplication implements CommandLineRunner  {
	//CursospringbootApplication é a classe principal do projeto
	//CommandLineRunner permite implementar o método auxiliar (run) para executar alguma ação quando a aplicação iniciar
	
	//@Autowired serve para instanciar uma classe automaticamente
	@Autowired 
	private CategoriaRepository categoriaRepository;

	public static void main(String[] args) {
		//Executa nosso aplicativo do Spring Boot
		
		SpringApplication.run(CursospringbootApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//A classe run é criada a partir do CommandLineRunner
		
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
	}
}
