package com.tatiane.voto.model;

import com.tatiane.funcionario.model.FuncionarioEntity;
import com.tatiane.restaurante.model.RestauranteEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;


import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "voto")
public class VotoEntity implements Serializable {

	private static final long serialVersionUID = -6522011289963747955L;

	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "data")
    private LocalDate data;

    @ManyToOne
    @JoinColumn(name = "idrestaurante")
    private RestauranteEntity restaurante;

    @ManyToOne
    @JoinColumn(name = "idfuncionario")
    private FuncionarioEntity funcionario;

//    @Column(name = "escolhido")
//    private Boolean escolhido;

//    public VotoEntity(Integer id, LocalDate data, RestauranteEntity restaurante, FuncionarioEntity funcionario, Boolean escolhido) {
//        this.id = id;
//        this.data = data;
//        this.restaurante = restaurante;
//        this.funcionario = funcionario;
//        this.escolhido = escolhido;
//    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        VotoEntity other = (VotoEntity) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }

}
