package com.tatiane.voto.dto;

import com.tatiane.restaurante.model.RestauranteEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VotoDto implements Comparable<VotoDto>{

	private RestauranteEntity restaurante;
	
	private Date data;
	
	private Integer quantidadeVotos;

	@Override
	public int compareTo(VotoDto arg0) {
		return this.getQuantidadeVotos().compareTo(arg0.getQuantidadeVotos());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((restaurante == null) ? 0 : restaurante.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VotoDto other = (VotoDto) obj;
		if (restaurante == null) {
			if (other.restaurante != null)
				return false;
		} else if (!restaurante.equals(other.restaurante))
			return false;
		return true;
	}
	
	
}
