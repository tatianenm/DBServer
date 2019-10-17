package com.tatiane.controller;

import com.tatiane.model.Restaurante;
import com.tatiane.service.RestauranteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@Api(value = "API Restaurante")
@RestController
@RequestMapping("/restaurante")
public class RestauranteController {

    private RestauranteService restauranteService;

    @Autowired
    public RestauranteController(RestauranteService restauranteService) {
        this.restauranteService = restauranteService;
    }

    @ApiOperation(value = "Retorna uma lista de restaurantes")
    @GetMapping(produces = {APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<List<Restaurante>> findAll() {
        return ResponseEntity.ok(restauranteService.findAll());
    }

    @ApiOperation(value = "Excluir restaurante")
    @DeleteMapping(path = "/{id}",
            produces = {APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity excluirRestaurante(@ApiParam(name = "id", value = "Restaurante id", required = true)
                                             @PathVariable(value = "id", required = true) Integer id) {
        restauranteService.excluirRestaurante(id);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Pesquisar Restaurante")
    @GetMapping(path = "pesquisarRestaurante/{id}",
            produces = {APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<Restaurante> pesquisarRestaurantePeloId(@ApiParam(name = "id", value = "Restaurante id", required = true)
                                                                  @PathVariable(value = "id", required = true) Integer id) {
        return ResponseEntity.ok(restauranteService.findOne(id));
    }

    @ApiOperation(value = "Cadastro Restaurante")
    @PostMapping(path = "/cadastroRestaurante",
            consumes = {APPLICATION_JSON_UTF8_VALUE},
            produces = {APPLICATION_JSON_UTF8_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public Restaurante cadastroRestaurante(@RequestBody Restaurante restaurante) {
        return restauranteService.cadastroRestaurante(restaurante);
    }

}
