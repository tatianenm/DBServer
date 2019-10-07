package com.tatiane.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.tatiane.model.Restaurante;
import com.tatiane.repository.RestauranteRepository;

import exception.RestauranteNotFoundException;

@Service
public class RestauranteService {

	private Logger logger = LoggerFactory.getLogger(RestauranteService.class);

	private RestauranteRepository restauranteRepository;

	public RestauranteService(RestauranteRepository restauranteRepository) {
		this.restauranteRepository = restauranteRepository;
	}

	public List<Restaurante> findAll() {
		List<Restaurante> restaurantes = restauranteRepository.findAll();
		return restaurantes;
	}

	public Restaurante findOne(Integer id) {
		logger.debug("find:" + id);
		return restauranteRepository.findById(id).orElseThrow(RestauranteNotFoundException::new);		
	}

	public void excluirRestaurante(Integer id) {
		logger.debug(id + "restaurante removido");
		restauranteRepository.deleteById(id);
	}

}
