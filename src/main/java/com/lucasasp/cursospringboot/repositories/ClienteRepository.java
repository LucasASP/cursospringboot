package com.lucasasp.cursospringboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lucasasp.cursospringboot.domain.Cliente;

//@Repository serve para definir a interface como pertencente à camada de persistência.
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer>{
	//JpaRepository tipo especial do Spring capaz de acessar os dados com base em um tipo que você passar
	//Um objeto desse tipo será capaz de realizar operações de acesso a dados do seu tipo mapeado com o mesmo tipo no banco de dados
	
	// recurso do spring data com padrão de nomes (findBy). 
	// nesse caso o spring data automaticamente vai identificar que voce qr fazer uma busca por email e vai implementar o metodo para voce
	// @Transactional(readOnly = true) indica que ela nao precisa ser envolvida como uma transação de BD, fazendo ela ficar mais rapida e diminui o locking do gerenciamento de transações do BD
	@Transactional(readOnly = true)
	Cliente findByEmail(String email);
}
