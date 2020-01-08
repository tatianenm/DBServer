package com.tatiane.restaurante.converter;

import com.tatiane.restaurante.dto.RestauranteDTO;
import com.tatiane.restaurante.model.RestauranteEntity;
import org.springframework.stereotype.Component;

@Component
public class RestauranteConverter {

    public RestauranteEntity converteParaRestauranteEntity(RestauranteDTO restauranteDTO) {
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
