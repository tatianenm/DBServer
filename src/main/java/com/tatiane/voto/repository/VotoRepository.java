package com.tatiane.voto.repository;


import com.tatiane.funcionario.model.FuncionarioEntity;
import com.tatiane.restaurante.model.RestauranteEntity;
import com.tatiane.voto.model.VotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface VotoRepository extends JpaRepository<VotoEntity, Integer> {


    List<VotoEntity> findByRestauranteAndDataBetweenAndDataNot(RestauranteEntity restauranteEntity,
                                                               LocalDate dataInicioSemana, LocalDate dataFinalSemana,
                                                               LocalDate dataDiaSistema);

    VotoEntity findByFuncionarioAndDataAndRestaurante(FuncionarioEntity funcionario, LocalDate data,
                                                      RestauranteEntity restaurante);

    List<VotoEntity> findByData(LocalDate data);
}
