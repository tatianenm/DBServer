package com.tatiane.voto.converter;

import com.tatiane.funcionario.converter.FuncionarioConverter;
import com.tatiane.restaurante.converter.RestauranteConverter;
import com.tatiane.voto.dto.VotoDto;
import com.tatiane.voto.dto.VotoPesquisaDTO;
import com.tatiane.voto.model.VotoEntity;
import org.springframework.stereotype.Component;

@Component
public class VotoConverter {

    private RestauranteConverter restauranteConverter;

    private FuncionarioConverter funcionarioConverter;

    public VotoConverter(RestauranteConverter restauranteConverter, FuncionarioConverter funcionarioConverter) {
        this.funcionarioConverter = funcionarioConverter;
        this.restauranteConverter = restauranteConverter;
    }

    public VotoPesquisaDTO converteParaVotoPesquisaDTO(VotoEntity votoEntity) {
        return VotoPesquisaDTO.builder()
                .id(votoEntity.getId())
                .data(votoEntity.getData())
                .funcionarioDTO(funcionarioConverter.converteParaFuncionarioDTO(votoEntity.getFuncionario()))
                .restauranteDTO(restauranteConverter.converteParaRestauranteDTO(votoEntity.getRestaurante()))
                .build();
    }

    public VotoEntity converteParaVoto(VotoDto dto) {
        VotoEntity votoEntity = new VotoEntity();
        votoEntity.setData(dto.getData());
        votoEntity.setRestaurante(dto.getRestaurante());
        votoEntity.setEscolhido(true);
        return votoEntity;
    }

}
