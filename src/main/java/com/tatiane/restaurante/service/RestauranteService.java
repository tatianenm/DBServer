package com.tatiane.restaurante.service;

import com.tatiane.restaurante.converter.RestauranteConverter;
import com.tatiane.restaurante.dto.RestauranteDTO;
import com.tatiane.restaurante.exception.RestauranteNotFoundException;
import com.tatiane.restaurante.model.RestauranteEntity;
import com.tatiane.restaurante.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class RestauranteService {

    private RestauranteRepository restauranteRepository;

    private RestauranteConverter restauranteConverter;

    @Autowired
    public RestauranteService(RestauranteRepository restauranteRepository, RestauranteConverter restauranteConverter) {
        this.restauranteRepository = restauranteRepository;
        this.restauranteConverter = restauranteConverter;
    }

    public List<RestauranteDTO> findAll() {
        if (Objects.isNull(restauranteRepository.findAll())) {
            throw new RestauranteNotFoundException();
        }
        List<RestauranteDTO> restaurantes = new ArrayList<>();
        ordenaListaRestaurantes(restauranteRepository.findAll())
                .forEach(restauranteEntity -> {
                    restaurantes.add(restauranteConverter.converteParaRestauranteDTO(restauranteEntity));
                });
        return restaurantes;
    }

    public RestauranteEntity findById(Integer id) {
        return restauranteRepository.findById(id).orElseThrow(RestauranteNotFoundException::new);
    }

    public void excluirRestaurante(Integer id) {
        restauranteRepository.findById(id).orElseThrow(RestauranteNotFoundException::new);
        restauranteRepository.deleteById(id);
    }

    public RestauranteEntity cadastroRestaurante(RestauranteDTO restauranteDTO) {
        return restauranteRepository.save(restauranteConverter.converteParaRestauranteEntity(restauranteDTO));
    }

    private List<RestauranteEntity> ordenaListaRestaurantes(List<RestauranteEntity> restauranteList) {
        return restauranteList.stream()
                .sorted((r1, r2) -> r1.getNome().compareToIgnoreCase(r2.getNome()))
                .collect(Collectors.toList());
    }

    public RestauranteDTO edicaoDadosRestaurante(RestauranteDTO restauranteDTO) {
       restauranteRepository.findById(restauranteDTO.getId()).orElseThrow(RestauranteNotFoundException::new);
        return restauranteConverter.converteParaRestauranteDTO(restauranteRepository.
                save(restauranteConverter.converteParaRestauranteEntity(restauranteDTO)));
    }
}
