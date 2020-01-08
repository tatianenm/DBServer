package com.tatiane.voto.dto;

import com.tatiane.funcionario.dto.FuncionarioDTO;
import com.tatiane.restaurante.dto.RestauranteDTO;

import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VotoPesquisaDTO {

    private Integer id;

    private LocalDate data;

    private RestauranteDTO restauranteDTO;

    private FuncionarioDTO funcionarioDTO;

}
