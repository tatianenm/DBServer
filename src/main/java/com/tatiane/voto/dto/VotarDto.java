package com.tatiane.voto.dto;

import java.io.Serializable;
import java.time.LocalDate;

import com.tatiane.funcionario.dto.FuncionarioDTO;
import com.tatiane.restaurante.dto.RestauranteDTO;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VotarDto implements Serializable {

	private static final long serialVersionUID = 4646409658092662335L;

	private Integer id;

	private RestauranteDTO restauranteDTO;

	private FuncionarioDTO funcionarioDTO;

	private LocalDate data = LocalDate.now();

}
