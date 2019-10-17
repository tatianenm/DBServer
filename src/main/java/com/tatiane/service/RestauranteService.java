package com.tatiane.service;

import com.tatiane.exception.RestauranteNotFoundException;
import com.tatiane.model.Restaurante;
import com.tatiane.repository.RestauranteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestauranteService {

    private RestauranteRepository restauranteRepository;

    public RestauranteService(RestauranteRepository restauranteRepository) {
        this.restauranteRepository = restauranteRepository;
    }

    public List<Restaurante> findAll() {
        return restauranteRepository.findAll().stream()
                .sorted((r1, r2) -> r1.getNome().compareToIgnoreCase(r2.getNome()))
                .collect(Collectors.toList());
    }

    public Restaurante findOne(Integer id) {
        return restauranteRepository.findById(id).orElseThrow(RestauranteNotFoundException::new);
    }

    public void excluirRestaurante(Integer id) {
        restauranteRepository.deleteById(id);
    }

    public Restaurante cadastroRestaurante(Restaurante restaurante) {
        return restauranteRepository.save(restaurante);
    }

}
