package com.tatiane.controller;

import com.tatiane.model.Voto;
import com.tatiane.model.dto.VotoDto;
import com.tatiane.model.dto.VotarDto;
import com.tatiane.service.VotoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.Date;
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

@Api(value = "API Restaurante")
@RestController
@RequestMapping("/voto")
public class VotoController {

	private Logger logger = LoggerFactory.getLogger(VotoController.class);

	private VotoService votoService;

	@Autowired
	public VotoController(VotoService votacaoService) {
		this.votoService = votacaoService;
	}

	@ApiOperation(value = "Retorna uma lista de votos")
	@GetMapping(produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<List<Voto>> findAll() {
		return ResponseEntity.ok(votoService.findAll());
	}

	@ApiOperation(value = "Resultado da votação")
	@GetMapping(path="/restaurante/mostrarResultadoVotacao",
	            produces = { MediaType.APPLICATION_JSON_UTF8_VALUE})
	public ResponseEntity<Voto> mostrarResultadoVotacao() {
		return ResponseEntity.ok(converteParaVoto(votoService.retornaResultadoVotacao(new Date())));
	}

	@ApiOperation(value = "Salvar voto")
	@PostMapping(path = "/votar", 
	             produces = { MediaType.APPLICATION_JSON_UTF8_VALUE }, 
	             consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseStatus(HttpStatus.CREATED)
	public Voto salvarVotoRestaurante(@RequestBody VotarDto votarDto)  {			
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
	               produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity excluirVoto(@ApiParam(name = "id", value = "Voto id", required = true )
	                                  @PathVariable(value = "id", required = true) Integer id ) {
		votoService.excluir(id);
		return ResponseEntity.ok().build();
	} 	
	
}
