package com.tatiane.funcionario.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tatiane.funcionario.model.FuncionarioEntity;

public interface FuncionarioRepository extends JpaRepository<FuncionarioEntity, Integer> {
	
	Optional<List<FuncionarioEntity>> findByNomeContainingIgnoreCase(String nome);
   
}
