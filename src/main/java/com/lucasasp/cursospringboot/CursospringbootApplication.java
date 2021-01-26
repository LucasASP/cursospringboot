package com.lucasasp.cursospringboot;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.lucasasp.cursospringboot.domain.Categoria;
import com.lucasasp.cursospringboot.domain.Cidade;
import com.lucasasp.cursospringboot.domain.Estado;
import com.lucasasp.cursospringboot.domain.Produto;
import com.lucasasp.cursospringboot.repositories.CategoriaRepository;
import com.lucasasp.cursospringboot.repositories.CidadeRepository;
import com.lucasasp.cursospringboot.repositories.EstadoRepository;
import com.lucasasp.cursospringboot.repositories.ProdutoRepository;

//@SpringBootApplication configura uma aplicação Spring Boot
@SpringBootApplication
public class CursospringbootApplication implements CommandLineRunner  {
	//CursospringbootApplication é a classe principal do projeto
	//CommandLineRunner permite implementar o método auxiliar (run) para executar alguma ação quando a aplicação iniciar
	
	//@Autowired serve para instanciar uma classe automaticamente
	@Autowired 
	private CategoriaRepository categoriaRepository;
	@Autowired 
	private ProdutoRepository produtoRepository;
	@Autowired 
	private EstadoRepository estadoRepository;
	@Autowired 
	private CidadeRepository cidadeRepository;

	public static void main(String[] args) {
		//Executa nosso aplicativo do Spring Boot
		
		SpringApplication.run(CursospringbootApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//A classe run é criada a partir do CommandLineRunner
		
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));

		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));
		
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
	}
}
