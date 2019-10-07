package com.tatiane.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tatiane.model.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {
	
	Optional<List<Funcionario>> findByNomeContainingIgnoreCase(String nome);
   
}
