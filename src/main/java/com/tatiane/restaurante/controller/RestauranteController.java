package com.tatiane.restaurante.controller;

import com.tatiane.restaurante.converter.RestauranteConverter;
import com.tatiane.restaurante.dto.RestauranteDTO;
import com.tatiane.restaurante.model.RestauranteEntity;
import com.tatiane.restaurante.service.RestauranteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@Api(value = "API Restaurante")
@RestController
@RequestMapping("/restaurante")
public class RestauranteController {

    private RestauranteService restauranteService;

    private RestauranteConverter restauranteConverter;

    @Autowired
    public RestauranteController(RestauranteService restauranteService, RestauranteConverter restauranteConverter) {
        this.restauranteService = restauranteService;
        this.restauranteConverter = restauranteConverter;
    }

    @ApiOperation(value = "Retorna uma lista de restaurantes")
    @GetMapping(produces = {APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<List<RestauranteDTO>> findAll() {
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
    @GetMapping(path = "/{id}", produces = {APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<RestauranteDTO> pesquisarRestaurantePeloId(@PathVariable(value = "id", required = true) Integer id) {
        return ResponseEntity.ok(restauranteConverter.converteParaRestauranteDTO(restauranteService.findById(id)));
    }

    @ApiOperation(value = "Cadastro Restaurante")
    @PostMapping(consumes = {APPLICATION_JSON_UTF8_VALUE},
            produces = {APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<RestauranteDTO> cadastroRestaurante(@RequestBody @Valid RestauranteDTO restauranteDTO,
                                                              UriComponentsBuilder uriBuilder) {
        RestauranteEntity restaurante = restauranteService.cadastroRestaurante(restauranteDTO);
        URI uri = uriBuilder.path("/restaurante/{id}").buildAndExpand(restaurante.getId()).toUri();
        return ResponseEntity.created(uri).body(restauranteConverter.converteParaRestauranteDTO(restaurante));
    }

    @ApiOperation(value = "Edição dados do restaurante")
    @PutMapping(consumes = {APPLICATION_JSON_UTF8_VALUE},
            produces = {APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<RestauranteDTO> edicaoDadosRestaurante(@RequestBody @Valid RestauranteDTO restauranteDTO) {
        return ResponseEntity.ok(restauranteService.edicaoDadosRestaurante(restauranteDTO));
    }

}
