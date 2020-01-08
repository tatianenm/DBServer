package com.tatiane.restaurante.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="restaurante")
@Entity
public class RestauranteEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="nome")
	private String nome;
	
	@Column(name="endereco")
	private String endereco;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof RestauranteEntity)) return false;
		RestauranteEntity that = (RestauranteEntity) o;
		return id.equals(that.id) &&
				Objects.equals(nome, that.nome) &&
				Objects.equals(endereco, that.endereco);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
