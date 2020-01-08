package com.tatiane.voto.controller;

import com.tatiane.voto.model.Voto;
import com.tatiane.voto.dto.VotarDto;
import com.tatiane.voto.dto.VotoDto;
import com.tatiane.voto.service.VotoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@Api(value = "API Restaurante")
@RestController
@RequestMapping("/voto")
public class VotoController {

    private VotoService votoService;

    @Autowired
    public VotoController(VotoService votacaoService) {
        this.votoService = votacaoService;
    }

    @ApiOperation(value = "Retorna uma lista de votos")
    @GetMapping(produces = {APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<List<Voto>> findAll() {
        return ResponseEntity.ok(votoService.findAll());
    }

    @ApiOperation(value = "Resultado da votação")
    @GetMapping(path = "/restaurante/mostrarResultadoVotacao",
            produces = {APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<Voto> mostrarResultadoVotacao() {
        return ResponseEntity.ok(converteParaVoto(votoService.retornaResultadoVotacao(new Date())));
    }

    @ApiOperation(value = "Salvar voto")
    @PostMapping(path = "/votar",
            produces = {APPLICATION_JSON_UTF8_VALUE},
            consumes = {APPLICATION_JSON_UTF8_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public Voto salvarVotoRestaurante(@RequestBody VotarDto votarDto) {
        return votoService.votar(votarDto.getIdRestaurante(), votarDto.getIdFuncionario());
    }

    private Voto converteParaVoto(VotoDto dto) {
        Voto voto = new Voto();
        voto.setData(dto.getData());
        voto.setRestaurante(dto.getRestaurante());
        voto.setEscolhido(true);
        return voto;
    }

    @ApiOperation(value = "Excluir voto")
    @DeleteMapping(path = "/{id}",
            produces = {APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity excluirVoto(@ApiParam(name = "id", value = "Voto id", required = true)
                                      @PathVariable(value = "id", required = true) Integer id) {
        votoService.excluir(id);
        return ResponseEntity.ok().build();
    }

}
