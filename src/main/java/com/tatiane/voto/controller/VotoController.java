package com.tatiane.voto.controller;

import com.tatiane.voto.converter.VotoConverter;
import com.tatiane.voto.dto.VotacaoDto;
import com.tatiane.voto.dto.VotarDto;
import com.tatiane.voto.dto.VotoPesquisaDTO;
import com.tatiane.voto.exception.VotoNotFoundException;
import com.tatiane.voto.model.VotoEntity;
import com.tatiane.voto.service.VotoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@Api(value = "API Restaurante")
@RestController
@RequestMapping("/voto")
public class VotoController {

    private VotoService votoService;

    private VotoConverter votoConverter;

    @Autowired
    public VotoController(VotoService votacaoService, VotoConverter votoConverter) {
        this.votoService = votacaoService;
        this.votoConverter = votoConverter;
    }

    @ApiOperation(value = "Retorna uma lista de votos")
    @GetMapping()
    public ResponseEntity<List<VotoPesquisaDTO>> findAll() {
        List<VotoPesquisaDTO> votoPesquisaDTOS = new ArrayList<>();
        votoService.findAll().forEach(votoEntity -> {
            votoPesquisaDTOS.add(votoConverter.converteParaVotoPesquisaDTO(votoEntity));
        });
        if (Objects.isNull(votoPesquisaDTOS)) {
            throw new VotoNotFoundException();
        }
        return ResponseEntity.ok(votoPesquisaDTOS);
    }

    @ApiOperation(value = "Resultado da votação")
    @GetMapping(path = "/resultadoVotacao",
            produces = {APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<VotacaoDto> mostrarResultadoVotacao() {
        return ResponseEntity.ok(votoService.retornaResultadoVotacao(LocalDate.now()));
    }

    @ApiOperation(value = "Salvar voto")
    @PostMapping(produces = {APPLICATION_JSON_UTF8_VALUE},
            consumes = {APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<VotarDto> salvarVotoRestaurante(@RequestBody @Valid VotarDto votarDto,
                                                          UriComponentsBuilder uriBuilder) {
        VotoEntity votoEntity = votoService.votar(votarDto);
        URI uri = uriBuilder.path("/voto/{id}").buildAndExpand(votoEntity.getId()).toUri();
        return ResponseEntity.created(uri).body(votoConverter.converteParaVotarDto(votoEntity));
    }

    @ApiOperation(value = "Excluir voto")
    @DeleteMapping(path = "/{id}",
            produces = {APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity excluirVoto(@PathVariable Integer id) {
        votoService.excluir(id);
        return ResponseEntity.ok().build();
    }

}
