package com.lucasasp.cursospringboot.dto;

import java.io.Serializable;

import com.lucasasp.cursospringboot.domain.Categoria;

//DTO (Data Transfer Object - Objeto de Transferência de Dados)  
//DTO é um objeto que vai ter apenas os dados que é preciso para alguma operação do sistema
//DTO é muito utilizado em todas as plataformas de modo que voce exibe apenas os dados que voce quiser
//DTO é muito util para facilitar a comunicação, para simplificar algumas operações
//Nesse caso esse objeto vai ser simplesmente para definir os dados que eu qro trafegar quando for fazer operações básicas de categoria
public class CategoriaDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private	String nome;
	
	public CategoriaDTO() {
	}
	
	public CategoriaDTO(Categoria obj) {
		this.id = obj.getId();
		this.nome = obj.getNome();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
