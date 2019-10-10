package com.tatiane.repository;


import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tatiane.model.Funcionario;
import com.tatiane.model.Restaurante;
import com.tatiane.model.Voto;

public interface VotoRepository extends JpaRepository<Voto, Integer> {
	
	@Query(value="Select v From Voto v left join v.restaurante r where r.id = :idRestau and v.data between :dtInicio and :dtFim "
			+ " and v.escolhido=true")
	Voto verificaSeRestauranteJaFoiEscolhidoNaSemana(@Param ("idRestau") Integer idRestaurante,
			                                         @Param("dtInicio") LocalDate dtInicio,
			                                         @Param("dtFim") LocalDate dtFim);
	
	Voto findByFuncionarioAndDataAndRestaurante(Funcionario funcionario, Date data, Restaurante restaurante);
	
	List<Voto> findByData(Date data); 
}
