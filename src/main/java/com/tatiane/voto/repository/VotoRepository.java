package com.tatiane.voto.repository;


import com.tatiane.funcionario.model.Funcionario;
import com.tatiane.restaurante.model.RestauranteEntity;
import com.tatiane.voto.model.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface VotoRepository extends JpaRepository<Voto, Integer> {
	
	@Query(value="Select v From Voto v left join v.restaurante r where r.id = :idRestau and v.data between :dtInicio and :dtFim "
			+ " and v.escolhido=true")
	List<Voto> verificaSeRestauranteJaFoiEscolhidoNaSemana(@Param ("idRestau") Integer idRestaurante,
			                                         @Param("dtInicio") Date dtInicio,
			                                         @Param("dtFim") Date dtFim);
	
	Voto findByFuncionarioAndDataAndRestaurante(Funcionario funcionario, Date data, RestauranteEntity restaurante);
	
	List<Voto> findByData(Date data); 
}
