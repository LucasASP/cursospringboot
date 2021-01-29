package com.lucasasp.cursospringboot.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lucasasp.cursospringboot.domain.Categoria;
import com.lucasasp.cursospringboot.domain.Produto;

//@Repository serve para definir a interface como pertencente à camada de persistência.
@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer>{
	//JpaRepository tipo especial do Spring capaz de acessar os dados com base em um tipo que você passar
	//Um objeto desse tipo será capaz de realizar operações de acesso a dados do seu tipo mapeado com o mesmo tipo no banco de dados
	
	// @Query podemos usar a JPQL que o framework faz um preprocessamento e cria o metodo para a gente
	@Transactional(readOnly=true)
	@Query("SELECT DISTINCT obj FROM Produto obj INNER JOIN obj.categorias cat WHERE obj.nome LIKE %:nome% AND cat IN :categorias")
	Page<Produto> search(@Param("nome") String nome, @Param("categorias") List<Categoria> categorias, Pageable pageRequest);
	
	// montando a mesma query acima com nomes de metodos do JPA
	//Page<Produto> findDistinctByNomeContainingAndCategoriasIn(String nome, List<Categoria> categorias, Pageable pageRequest);
}
