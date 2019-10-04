package com.tatiane.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tatiane.model.Funcionario;
import com.tatiane.service.FuncionarioService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value="API Restaurante")
@RestController
@RequestMapping("/funcionario")
public class FuncionarioController {
	
    private Logger logger = LoggerFactory.getLogger(FuncionarioController.class);
	
	private FuncionarioService funcionarioService;
	
    @Autowired
	public FuncionarioController(FuncionarioService funcionarioService) {
		this.funcionarioService = funcionarioService;
	}
    
    @ApiOperation(value= "Retorna uma lista de funcionários")
	@GetMapping( produces = { MediaType.APPLICATION_JSON_UTF8_VALUE } )
    public ResponseEntity<List<Funcionario>> findAll(){
    	List<Funcionario> funcionarios = funcionarioService.findAll();
    	return ResponseEntity.ok(funcionarios);
    }
    
    @ApiOperation(value = "Excluir um funcionário")
    @DeleteMapping(path = "/{id}",
                   consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity excluirFuncionario(@ApiParam(name = "id", value = "Funcionário id", required = true)
    		                                 @PathVariable(value = "id", required = true) Integer id) {
    	funcionarioService.excluirFuncionario(id);
    	return ResponseEntity.ok().build();
    }
	
}
