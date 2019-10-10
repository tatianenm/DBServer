package com.tatiane.model.dto;

import java.util.Date;

import com.tatiane.model.Restaurante;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VotoDto implements Comparable<VotoDto>{

	private Restaurante restaurante;
	
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