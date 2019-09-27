package com.tatiane.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tatiane.model.Restaurante;
import com.tatiane.service.RestauranteService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value="API Restaurante")
@RestController
@RequestMapping("/restaurante")
public class RestauranteController {
	
	private Logger logger = LoggerFactory.getLogger(RestauranteController.class);
	
	private RestauranteService restauranteService;
	
    @Autowired
	public RestauranteController(RestauranteService restauranteService) {
		this.restauranteService = restauranteService;
	}
    
	@ApiOperation(value="Retorna uma lista de restaurantes")
    @GetMapping(produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public  ResponseEntity<List<Restaurante>> findAll(){
		List<Restaurante> restaurantes = restauranteService.findAll();
		return ResponseEntity.ok(restaurantes);
	}

	
}
