package com.tatiane.restaurante.service;

import com.tatiane.restaurante.converter.RestauranteConverter;
import com.tatiane.restaurante.dto.RestauranteDTO;
import com.tatiane.restaurante.exception.RestauranteNotFoundException;
import com.tatiane.restaurante.model.RestauranteEntity;
import com.tatiane.restaurante.repository.RestauranteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestauranteService {

    private RestauranteRepository restauranteRepository;

    private RestauranteConverter restauranteConverter;

    public RestauranteService(RestauranteRepository restauranteRepository, RestauranteConverter restauranteConverter) {
        this.restauranteRepository = restauranteRepository;
        this.restauranteConverter = restauranteConverter;
    }

    public List<RestauranteEntity> findAll() {
        return restauranteRepository.findAll().stream()
                .sorted((r1, r2) -> r1.getNome().compareToIgnoreCase(r2.getNome()))
                .collect(Collectors.toList());
    }

    public RestauranteEntity findById(Integer id) {
        return restauranteRepository.findById(id).orElseThrow(RestauranteNotFoundException::new);
    }

    public void excluirRestaurante(Integer id) {
        restauranteRepository.findById(id).orElseThrow(RestauranteNotFoundException::new);
        restauranteRepository.deleteById(id);
    }

    public RestauranteEntity cadastroRestaurante(RestauranteDTO restauranteDTO) {
        return restauranteRepository.save(restauranteConverter.converteParaRestaurante(restauranteDTO));
    }

}
