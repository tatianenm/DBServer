package com.tatiane.restaurante.dto;

import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.util.Objects;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestauranteDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String nome;

	private String endereco;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof RestauranteDTO)) return false;
		RestauranteDTO that = (RestauranteDTO) o;
		return Objects.equals(id, that.id) &&
				Objects.equals(nome, that.nome) &&
				Objects.equals(endereco, that.endereco);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
