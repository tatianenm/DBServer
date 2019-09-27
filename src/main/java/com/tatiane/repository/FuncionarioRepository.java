package com.tatiane.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tatiane.model.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {
   
}
