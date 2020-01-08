package com.tatiane.funcionario.controller;

import com.tatiane.funcionario.model.Funcionario;
import com.tatiane.funcionario.service.FuncionarioService;
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
@RequestMapping("/funcionario")
public class FuncionarioController {

    private FuncionarioService funcionarioService;

    @Autowired
    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @ApiOperation(value = "Retorna uma lista de funcionários")
    @GetMapping(produces = {APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<List<Funcionario>> findAll() {
        return ResponseEntity.ok(funcionarioService.findAll());
    }

    @ApiOperation(value = "Excluir funcionário")
    @DeleteMapping(path = "/{id}",
            produces = {APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity excluirFuncionario(@ApiParam(name = "id", value = "Funcionário id", required = true)
                                             @PathVariable(value = "id") Integer id) {
        funcionarioService.excluirFuncionario(id);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Pesquisar funcionários")
    @GetMapping(path = "/{nome}",
            produces = {APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<List<Funcionario>> pesquisarFuncionarioPeloNome(@ApiParam(name = "nome", value = "Funcionário nome", required = true)
                                                                          @PathVariable(value = "nome") String nome) {
        return ResponseEntity.ok(funcionarioService.pesquisarFuncionarioPeloNome(nome));
    }

    @ApiOperation(value = "Cadastro de Funcionário")
    @PostMapping(path = "/cadastroFuncionario",
            produces = {APPLICATION_JSON_UTF8_VALUE},
            consumes = {APPLICATION_JSON_UTF8_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public Funcionario cadastroFuncionario(@RequestBody Funcionario funcionario) {
        return funcionarioService.cadastroFuncionario(funcionario);
    }

}
