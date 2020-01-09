package com.tatiane.voto.dto;

import com.tatiane.restaurante.dto.RestauranteDTO;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VotacaoDto implements Comparable<VotacaoDto> {

    private Integer id;

    private RestauranteDTO restauranteDTO;

    private LocalDate data;

    private Integer quantidadeVotos;

    @Override
    public int compareTo(VotacaoDto arg0) {
        return this.getQuantidadeVotos().compareTo(arg0.getQuantidadeVotos());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VotacaoDto)) return false;
        VotacaoDto that = (VotacaoDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(restauranteDTO, that.restauranteDTO) &&
                Objects.equals(data, that.data) &&
                Objects.equals(quantidadeVotos, that.quantidadeVotos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
