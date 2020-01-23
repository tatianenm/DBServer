package com.tatiane.funcionario.service;

import com.tatiane.funcionario.converter.FuncionarioConverter;
import com.tatiane.funcionario.dto.FuncionarioDTO;
import com.tatiane.funcionario.exception.FuncionarioNotFoundException;
import com.tatiane.funcionario.model.FuncionarioEntity;
import com.tatiane.funcionario.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class FuncionarioService {

    private FuncionarioRepository funcionarioRepository;

    private FuncionarioConverter funcionarioConverter;

    @Autowired
    public FuncionarioService(FuncionarioRepository funcionarioRepository, FuncionarioConverter funcionarioConverter) {
        this.funcionarioRepository = funcionarioRepository;
        this.funcionarioConverter = funcionarioConverter;
    }

    public List<FuncionarioDTO> findAll() {
        if (Objects.isNull(funcionarioRepository.findAll())) {
            throw new FuncionarioNotFoundException();
        }

        List<FuncionarioDTO> funcionarios = new ArrayList<>();
        ordenaListaFuncionarios(funcionarioRepository.findAll()).forEach(funcionarioEntity -> {
            funcionarios.add(funcionarioConverter.converteParaFuncionarioDTO(funcionarioEntity));
        });
        return funcionarios;
    }

    public void excluirFuncionario(Integer id) {
        funcionarioRepository.findById(id).orElseThrow(FuncionarioNotFoundException::new);
        funcionarioRepository.deleteById(id);
    }

    public List<FuncionarioEntity> pesquisarFuncionarioPeloNome(String nome) {
        return funcionarioRepository.findByNomeContainingIgnoreCase(nome).orElseThrow(FuncionarioNotFoundException::new);

    }

    public FuncionarioEntity cadastroFuncionario(FuncionarioDTO funcionarioDTO) {
        return funcionarioRepository.save(funcionarioConverter.converteParaFuncionarioEntity(funcionarioDTO));
    }

    private List<FuncionarioEntity> ordenaListaFuncionarios(List<FuncionarioEntity> funcionarioList) {
        return funcionarioList.stream()
                .sorted((f1, f2) -> f1.getNome().compareToIgnoreCase(f2.getNome()))
                .collect(Collectors.toList());
    }

    public FuncionarioDTO edicaoDadosFuncionario(FuncionarioDTO funcionarioDTO) {
        funcionarioRepository.findById(funcionarioDTO.getId()).orElseThrow(FuncionarioNotFoundException::new);
        return funcionarioConverter.converteParaFuncionarioDTO(funcionarioRepository.
                save(funcionarioConverter.converteParaFuncionarioEntity(funcionarioDTO)));
    }

}
