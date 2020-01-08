package com.tatiane.funcionario.converter;

import com.tatiane.funcionario.dto.FuncionarioDTO;
import com.tatiane.funcionario.model.FuncionarioEntity;
import org.springframework.stereotype.Component;

@Component
public class FuncionarioConverter {

    public FuncionarioEntity converteParaFuncionarioEntity(FuncionarioDTO funcionarioDTO){
        return FuncionarioEntity.builder()
                .id(funcionarioDTO.getId())
                .nome(funcionarioDTO.getNome())
                .senha(funcionarioDTO.getSenha())
                .user(funcionarioDTO.getUser())
                .build();
    }

    public FuncionarioDTO converteParaFuncionarioDTO(FuncionarioEntity funcionarioEntity){
        return FuncionarioDTO.builder()
                .id(funcionarioEntity.getId())
                .nome(funcionarioEntity.getNome())
                .senha(funcionarioEntity.getSenha())
                .user(funcionarioEntity.getUser())
                .build();
    }
}
