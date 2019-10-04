package com.tatiane.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tatiane.model.Funcionario;
import com.tatiane.repository.FuncionarioRepository;

@Service
public class FuncionarioService {
	
    private static Logger logger = LoggerFactory.getLogger(FuncionarioService.class);
	
	private FuncionarioRepository funcionarioRepository;

	@Autowired
	public FuncionarioService(FuncionarioRepository funcionarioRepository) {
		this.funcionarioRepository = funcionarioRepository;
	}
    
	public List<Funcionario> findAll(){
		List<Funcionario> funcionarios = funcionarioRepository.findAll();
		return funcionarios;
	}	
	
	public void excluirFuncionario(Integer id) {
		logger.debug( id + "funcionário removido");
		funcionarioRepository.deleteById(id);
	} 
	
}
