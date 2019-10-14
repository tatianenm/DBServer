package com.tatiane.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tatiane.model.Restaurante;
import com.tatiane.service.RestauranteService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

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
		return ResponseEntity.ok(restauranteService.findAll());
	}
	
	@ApiOperation(value = "Excluir restaurante")
	@DeleteMapping(path = "/{id}",
                   produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity excluirRestaurante(@ApiParam(name = "id", value = "Restaurante id", required = true)
	                                         @PathVariable(value = "id", required = true) Integer id) {
		restauranteService.excluirRestaurante(id);
		return ResponseEntity.ok().build();		
	}
	
	@ApiOperation(value = "Pesquisar Restaurante" )
	@GetMapping(path = "pesquisarRestaurante/{id}",
			    produces = { MediaType.APPLICATION_JSON_UTF8_VALUE})
	public ResponseEntity<Restaurante> pesquisarRestaurantePeloId(@ApiParam(name = "id", value = "Restaurante id", required = true)
	                                                        @PathVariable(value = "id", required = true) Integer id){		
		 return ResponseEntity.ok(restauranteService.findOne(id));		
	}
	
	@ApiOperation(value = "Cadastro Restaurante")
	@PostMapping(path = "/cadastroRestaurante",
	             consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE },
	             produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseStatus(HttpStatus.CREATED)
	public Restaurante cadastroRestaurante(@RequestBody Restaurante restaurante) {
		return restauranteService.cadastroRestaurante(restaurante);		
	}
	
}
