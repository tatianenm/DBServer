package com.tatiane.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.tatiane.model.Restaurante;
import com.tatiane.repository.RestauranteRepository;

@Service
public class RestauranteService {
	
	private Logger logger = LoggerFactory.getLogger(RestauranteService.class);
	
	private RestauranteRepository restauranteRepository;

	public RestauranteService(RestauranteRepository restauranteRepository) {
		this.restauranteRepository = restauranteRepository;
	}
	
	public List<Restaurante> findAll(){
		List<Restaurante> restaurantes= restauranteRepository.findAll();
		return restaurantes;
	}
	
	public Optional<Restaurante> findOne(Integer id) {
    	logger.debug("find:" + id);
    	Optional<Restaurante> ent = restauranteRepository.findById(id);
    	return ent;
    }
	
	public void excluirRestaurante(Integer id) {
		logger.debug(id + "restaurante removido");
		restauranteRepository.deleteById(id);
	}	

}
