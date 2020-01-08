package com.tatiane.restaurante.converter;

import com.tatiane.restaurante.dto.RestauranteDTO;
import com.tatiane.restaurante.model.RestauranteEntity;

public class RestauranteConverter {

    public RestauranteEntity converteParaRestaurante(RestauranteDTO restauranteDTO) {
        return RestauranteEntity.builder().build().builder()
                .id(restauranteDTO.getId())
                .nome(restauranteDTO.getNome())
                .endereco(restauranteDTO.getEndereco())
                .build();
    }

    public RestauranteDTO converteParaRestauranteDTO(RestauranteEntity restaurante) {
        return RestauranteDTO.builder()
                .id(restaurante.getId())
                .nome(restaurante.getNome())
                .endereco(restaurante.getEndereco())
                .build();
    }

}
