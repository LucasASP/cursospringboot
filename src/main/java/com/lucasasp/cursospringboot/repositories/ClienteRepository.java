package com.lucasasp.cursospringboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lucasasp.cursospringboot.domain.Cliente;

//@Repository serve para definir a interface como pertencente à camada de persistência.
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer>{
	//JpaRepository tipo especial do Spring capaz de acessar os dados com base em um tipo que você passar
	//Um objeto desse tipo será capaz de realizar operações de acesso a dados do seu tipo mapeado com o mesmo tipo no banco de dados
}
