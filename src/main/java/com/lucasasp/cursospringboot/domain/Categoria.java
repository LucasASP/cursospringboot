package com.lucasasp.cursospringboot.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

//@Entity indica que essa classe será uma entidade do JPA
@Entity
public class Categoria implements Serializable {
	//Serializable interface que diz que os objetos dessa classe poderão ser convertidos para uma sequencia de bytes serve para os objetos serem gravados em arquivos, para trafegar em rede e assim por diante
	
	//Quando a classe implemanta Serializable ela tem que ter um número de versão padrão
	private static final long serialVersionUID = 1L;
	
	//@Id é utilizada para informar ao JPA qual campo/atributo de uma entidade estará relacionado à chave primária da respectiva tabela no banco de dados
	//@GeneratedValue é utilizada para informar que a geração do valor do identificador único da entidade será gerenciada pelo provedor de persistência
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private	String nome;

	//Quado temos uma tabela muitos para muitos temos que criar uma terceira tabela contendo os ids das duas tabelas
	/**@ManyToMany serve para gerar uma tabela que resolva um relacionamento  N - N entre duas classes
		Essa terceira tabela foi nomeada em Produto no atributo categorias**/
	@ManyToMany(mappedBy = "categorias")
	private List<Produto> produtos = new ArrayList<>();
	
	public Categoria() {
	}

	public Categoria(Integer id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
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
	
	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	//@Override informa ao compilador que a intenção seria de sobrescrever um método de uma classe pai.
	@Override
	public int hashCode() {
		//hashCode permite que seja muito rápido recuperar objetos.
		//hashCode e equals andam juntos! Se você implementar um DEVE fazer o mesmo com o outro. Não vai dar erro se não o fizer, mas a chance de alguma lógica dar errado por conta disso é muito grande.
		//A maior vantagem em sobrescrever esses métodos é melhorar a busca dos objetos.
		//Ex: O método .indexOf da interface List retorna o indice do objeto passado, mas isso se torna “inviável” sem uma implementação correta do equals e hashCode
		
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		//equals compara dois objetos pelo conteúdo e não pelo ponteiro
		
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Categoria other = (Categoria) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
