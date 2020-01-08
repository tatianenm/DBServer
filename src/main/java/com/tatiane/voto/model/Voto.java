package com.tatiane.voto.model;

import com.tatiane.funcionario.model.Funcionario;
import com.tatiane.restaurante.model.RestauranteEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "voto")
public class Voto implements Serializable {

	private static final long serialVersionUID = -6522011289963747955L;

	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Temporal(TemporalType.DATE)
    @Column(name = "data")
    private Date data;

    @ManyToOne
    @JoinColumn(name = "idrestaurante")
    private RestauranteEntity restaurante;

    @ManyToOne
    @JoinColumn(name = "idfuncionario")
    private Funcionario funcionario;

    @Column(name = "escolhido")
    private Boolean escolhido;


    public Voto(Integer id, Date data, RestauranteEntity restaurante, Funcionario funcionario, Boolean escolhido) {
        super();
        this.id = id;
        this.data = data;
        this.restaurante = restaurante;
        this.funcionario = funcionario;
        this.escolhido = Boolean.FALSE;
    }

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
        Voto other = (Voto) obj;
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
