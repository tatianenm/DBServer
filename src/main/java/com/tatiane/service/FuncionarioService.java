package com.tatiane.service;

import com.tatiane.exception.FuncionarioNotFoundException;
import com.tatiane.model.Funcionario;
import com.tatiane.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FuncionarioService {
	
	private FuncionarioRepository funcionarioRepository;

	@Autowired
	public FuncionarioService(FuncionarioRepository funcionarioRepository) {
		this.funcionarioRepository = funcionarioRepository;
	}
    
	public List<Funcionario> findAll(){
		return funcionarioRepository.findAll()
				                    .stream()
				                    .sorted((f1,f2)->f1.getNome().compareToIgnoreCase(f2.getNome()))
				                    .collect(Collectors.toList());		
	}	
	
	public void excluirFuncionario(Integer id) {
		funcionarioRepository.deleteById(id);
	} 
	
	public List<Funcionario> pesquisarFuncionarioPeloNome(String nome) {
		return funcionarioRepository.findByNomeContainingIgnoreCase(nome).orElseThrow(FuncionarioNotFoundException::new);	
		
	} 
	
	public Funcionario cadastroFuncionario(Funcionario funcionario) {
		return funcionarioRepository.save(funcionario);		
	}
	
}
