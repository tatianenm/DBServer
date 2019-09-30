package com.tatiane.controller;

import com.tatiane.model.Votacao;
import com.tatiane.model.dto.VotacaoDto;
import com.tatiane.model.dto.VotarDto;
import com.tatiane.service.VotacaoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "API Restaurante")
@RestController
@RequestMapping("/votacao")
public class VotacaoController {

    private Logger logger = LoggerFactory.getLogger(VotacaoController.class);

    private VotacaoService votacaoService;

    @Autowired
    public VotacaoController(VotacaoService votacaoService) {
        this.votacaoService = votacaoService;
    }

    @ApiOperation(value = "Retorna uma lista de votacao")
    @GetMapping(produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<List<Votacao>> findAll() {
        List<Votacao> votacoes = votacaoService.findAll();
        return ResponseEntity.ok(votacoes);
    }

    @ApiOperation(value = "Resultado da votação")
    @GetMapping("/restaurante/mostrarResultadoVotacao")
    public ResponseEntity<Votacao> mostrarResultadoVotacao() {
        VotacaoDto dto = votacaoService.retornaResultadoVotacao(new Date());
        return ResponseEntity.ok(converteParaVotacao(dto));
    }

    @ApiOperation(value = "Salvar votação")
    @PostMapping(produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}, 
                 consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Boolean> salvarVotoRestaurante(@RequestBody VotarDto votarDto) {
        Boolean resultado = votacaoService.votar(votarDto.getIdRestaurante(), votarDto.getIdFuncionario());
        if (resultado) {
            return ResponseEntity.ok(resultado);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    private Votacao converteParaVotacao(VotacaoDto dto) {
        Votacao votacao = new Votacao();
        votacao.setData(dto.getData());
        votacao.setRestaurante(dto.getRestaurante());
        votacao.setEscolhido(true);
        votacao.setQuantidadeVotos(dto.getQuantidadeVotos());
        return votacao;
    }

}
