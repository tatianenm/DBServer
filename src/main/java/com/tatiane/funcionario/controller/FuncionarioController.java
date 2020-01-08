package com.tatiane.funcionario.controller;

import com.tatiane.funcionario.converter.FuncionarioConverter;
import com.tatiane.funcionario.dto.FuncionarioDTO;
import com.tatiane.funcionario.exception.FuncionarioNotFoundException;
import com.tatiane.funcionario.model.FuncionarioEntity;
import com.tatiane.funcionario.service.FuncionarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@Api(value = "API Restaurante")
@RestController
@RequestMapping("/funcionario")
public class FuncionarioController {

    private FuncionarioService funcionarioService;

    private FuncionarioConverter funcionarioConverter;

    @Autowired
    public FuncionarioController(FuncionarioService funcionarioService, FuncionarioConverter funcionarioConverter) {
        this.funcionarioService = funcionarioService;
        this.funcionarioConverter = funcionarioConverter;
    }

    @ApiOperation(value = "Retorna uma lista de funcionários")
    @GetMapping(produces = {APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<List<FuncionarioDTO>> findAll() {
        List<FuncionarioDTO> funcionarios = new ArrayList<>();
        funcionarioService.findAll().forEach(funcionarioEntity -> {
            funcionarios.add(funcionarioConverter.converteParaFuncionarioDTO(funcionarioEntity));
        });
        if(Objects.isNull(funcionarios)){
            throw new FuncionarioNotFoundException();
        }
        return ResponseEntity.ok(funcionarios);
    }

    @ApiOperation(value = "Excluir funcionário")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity excluirFuncionario(@PathVariable  Integer id ) {
        funcionarioService.excluirFuncionario(id);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Pesquisar funcionários")
    @GetMapping(path = "/{nome}",
            produces = {APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<List<FuncionarioDTO>> pesquisarFuncionarioPeloNome(@PathVariable String nome) {
        List<FuncionarioDTO> funcionarios = new ArrayList<>();
        funcionarioService.pesquisarFuncionarioPeloNome(nome).forEach(funcionarioEntity -> {
            funcionarios.add(funcionarioConverter.converteParaFuncionarioDTO(funcionarioEntity));
        });
        return ResponseEntity.ok(funcionarios);
    }

    @ApiOperation(value = "Cadastro de Funcionário")
    @PostMapping(produces = {APPLICATION_JSON_UTF8_VALUE},
                 consumes = {APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<FuncionarioDTO> cadastroFuncionario(@RequestBody @Valid FuncionarioDTO funcionarioDTO,
                                              UriComponentsBuilder uriBuilder) {
        FuncionarioEntity funcionario = funcionarioService.cadastroFuncionario(funcionarioDTO);
        URI uri = uriBuilder.path("/funcionario/{id}").buildAndExpand(funcionario.getId()).toUri();
        return ResponseEntity.created(uri).body(funcionarioConverter.converteParaFuncionarioDTO(funcionario));
    }

}
