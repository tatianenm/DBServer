package com.tatiane.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.tatiane.exception.RestauranteNotFoundException;
import com.tatiane.model.Restaurante;
import com.tatiane.repository.RestauranteRepository;

@Service
public class RestauranteService {

	private Logger logger = LoggerFactory.getLogger(RestauranteService.class);

	private RestauranteRepository restauranteRepository;

	public RestauranteService(RestauranteRepository restauranteRepository) {
		this.restauranteRepository = restauranteRepository;
	}

	public List<Restaurante> findAll() {
		return restauranteRepository.findAll().stream()
	                                          .sorted((r1,r2)->r1.getNome().compareToIgnoreCase(r2.getNome()))
				                              .collect(Collectors.toList());
	}

	public Restaurante findOne(Integer id) {
		logger.debug("find:" + id);
		return restauranteRepository.findById(id).orElseThrow(RestauranteNotFoundException::new);		
	}

	public void excluirRestaurante(Integer id) {
		logger.debug(id + "restaurante removido");
		restauranteRepository.deleteById(id);
	}
	
	public Restaurante cadastroRestaurante(Restaurante restaurante) {
		return restauranteRepository.save(restaurante);
	}

}
