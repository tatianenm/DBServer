package com.tatiane.voto.converter;

import com.tatiane.funcionario.converter.FuncionarioConverter;
import com.tatiane.restaurante.converter.RestauranteConverter;
import com.tatiane.voto.dto.VotarDto;
import com.tatiane.voto.dto.VotoPesquisaDTO;
import com.tatiane.voto.model.VotoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VotoConverter {

    private RestauranteConverter restauranteConverter;

    private FuncionarioConverter funcionarioConverter;

    @Autowired
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

    public VotoEntity converteParaVotoEntity(VotarDto votarDto) {
        VotoEntity votoEntity = new VotoEntity();
        votoEntity.setData(votarDto.getData());
        votoEntity.setRestaurante(restauranteConverter.converteParaRestauranteEntity(votarDto.getRestauranteDTO()));
        votoEntity.setFuncionario(funcionarioConverter.converteParaFuncionarioEntity(votarDto.getFuncionarioDTO()));
        return votoEntity;
    }

    public VotarDto converteParaVotarDto(VotoEntity votoEntity){
        return VotarDto.builder()
                .data(votoEntity.getData())
                .id(votoEntity.getId())
                .funcionarioDTO(funcionarioConverter.converteParaFuncionarioDTO(votoEntity.getFuncionario()))
                .restauranteDTO(restauranteConverter.converteParaRestauranteDTO(votoEntity.getRestaurante()))
                .build();

    }

}
