package com.lucasasp.cursospringboot.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lucasasp.cursospringboot.domain.enums.TipoCliente;

@Entity
public class Cliente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	
	// @Column(unique=true) faz o banco de dados garantir que não havera repetição nesse campo
	// mas fazendo so isso teremos pouco controle sobre a exceção que vai ser lançada e nao vamos ter muito jeito de tratar esse exceção e dar uma msg personalizada para o nosso cliente
	@Column(unique=true)
	private String email;
	private String cpfOuCnpj;
	private Integer tipo;
	
	//cascade determina qual comportamento em cascata dessa assiciação - nesse caso quando apagar o cliente apagamos em cascata os endereços
	//ALL indica que toda operação que aplicar em cliente será refletida nos endereços
	//Ou seja, quando tiver uma regra de negócio q vc pode apagar em cascata é so usar cascade=CascadeType.ALL que o JPA ja faz a regrinha la no BD automaticamente
	//quando quiser barrar a deleção de uma entidade relacionada é só deixar do jeito padrão que o Spring Data não deixa apagar
	@OneToMany(mappedBy = "cliente", cascade=CascadeType.ALL)
	private List<Endereco> enderecos = new ArrayList<>();
	
	//Entidade fraca - Criada como uma coleção de string para telefones, o Set nao permite repetições
	//@ElementCollection do JPA serve para mapearmos o relacionamento das nossas entidades com listas de classes que NÃO são entidades
	//@CollectionTable é usada para especificar a tabela de banco
	@ElementCollection
	@CollectionTable(name="TELEFONE")
	private Set<String> telefones = new HashSet<>();

	//@JsonIgnore - Ignorada a funcionalidade de serialização (nao permite a criação de Json). Protege contra serialização Json cíclica.
	@JsonIgnore
	@OneToMany(mappedBy = "cliente")
	private List<Pedido> pedidos = new ArrayList<>();
	
	public Cliente() {
	}

	public Cliente(Integer id, String nome, String email, String cpfOuCnpj, TipoCliente tipo) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.cpfOuCnpj = cpfOuCnpj;
		this.tipo = (tipo==null) ? null : tipo.getCod();
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpfOuCnpj() {
		return cpfOuCnpj;
	}

	public void setCpfOuCnpj(String cpfOuCnpj) {
		this.cpfOuCnpj = cpfOuCnpj;
	}

	public TipoCliente getTipo() {
		return TipoCliente.toEnum(tipo);
	}

	public void setTipo(TipoCliente tipo) {
		this.tipo = tipo.getCod();
	}

	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public Set<String> getTelefones() {
		return telefones;
	}

	public void setTelefones(Set<String> telefones) {
		this.telefones = telefones;
	}
	
	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
