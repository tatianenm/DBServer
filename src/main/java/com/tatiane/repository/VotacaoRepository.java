package com.tatiane.repository;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tatiane.model.Funcionario;
import com.tatiane.model.Restaurante;
import com.tatiane.model.Votacao;

public interface VotacaoRepository extends JpaRepository<Votacao, Integer> {
	
	@Query(value="Select v From Votacao v left join v.restaurante r where r.id = :idRestau and v.data between :dtInicio and :dtFim "
			+ " and v.escolhido=true")
	List<Votacao> verificaSeExisteIdRestauranteNoBanco(@Param ("idRestau") Integer idRestaurante,
			                                           @Param("dtInicio") Date dtInicio,
			                                           @Param("dtFim") Date dtFim);
	
	Votacao findByFuncionarioAndDataAndRestaurante(Funcionario funcionario, Date data, Restaurante restaurante);
	
	List<Votacao> findByData(Date data); 
}
