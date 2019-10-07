package com.tatiane.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.tatiane.model.Funcionario;
import com.tatiane.model.Restaurante;
import com.tatiane.model.Votacao;
import com.tatiane.model.dto.VotacaoDto;
import com.tatiane.repository.VotacaoRepository;

@Service
public class VotacaoService {

	private Logger logger = LoggerFactory.getLogger(VotacaoService.class);
	
	public static final String MSG_VOTO_REPETIDO = "O funcionário já votou na data de hoje no mesmo restaurante";

	private VotacaoRepository votacaoRepository;

	public VotacaoService(VotacaoRepository votacaoRepository) {
		this.votacaoRepository = votacaoRepository;
	}

	public List<Votacao> findAll() {
	    return	 votacaoRepository.findAll()
	    		                  .stream()
	    		                  .sorted((v1,v2)-> v2.getData().compareTo(v1.getData()))
	    		                  .collect(Collectors.toList());
	}

	public Optional<Votacao> findOne(Integer id) {
		logger.debug("find:" + id);
		Optional<Votacao> ent = votacaoRepository.findById(id);
		return ent;
	}

	public Votacao votar(Integer idRestaurante, Integer idFuncionario) throws Exception {

		Funcionario funcionario = new Funcionario();
		funcionario.setId(idFuncionario);
		Restaurante restaurante = new Restaurante();
		restaurante.setId(idRestaurante);

		boolean votoRepetido = verificaSeFuncionarioJaVotouRestauranteMesmoDia(funcionario, new Date(), restaurante);
		if (votoRepetido == true) {
			throw new Exception(MSG_VOTO_REPETIDO);
		}
		Votacao votacao = new Votacao();
		votacao.setData(new Date());
		votacao.setFuncionario(funcionario);
		votacao.setRestaurante(restaurante);
		votacao.setEscolhido(false);
		return votacaoRepository.save(votacao);

	}

	private boolean verificaSeFuncionarioJaVotouRestauranteMesmoDia(Funcionario funcionario, Date data, Restaurante restaurante) {
		Votacao votacao = votacaoRepository.findByFuncionarioAndDataAndRestaurante(funcionario, data, restaurante);
		return Optional.ofNullable(votacao).isPresent();
	}

	public VotacaoDto retornaResultadoVotacao(Date data) {
		List<Votacao> votacoes = votacaoRepository.findByData(data);
		List<VotacaoDto> dtos = new ArrayList<>();
		VotacaoDto dtoRetorno = null;

		if (votacoes != null) {
			votacoes.forEach(voto -> {
				Restaurante r = voto.getRestaurante();
				VotacaoDto dto = new VotacaoDto();
				dto.setRestaurante(r);
				dto.setQuantidadeVotos(1);
				dto.setData(data);
				if (dtos.contains(dto)) {
					int i = dtos.indexOf(dto);
					dtos.get(i).setQuantidadeVotos(dtos.get(i).getQuantidadeVotos() + 1);
				} else {
					dtos.add(dto);
				}
			});
		}
		dtoRetorno = dtos.stream()
				.max((VotacaoDto o1, VotacaoDto o2) -> o1.getQuantidadeVotos().compareTo(o2.getQuantidadeVotos()))
				.get();
		return dtoRetorno;
	}

	public void excluir(Integer id) {
		logger.debug( id + "votação removido" );
		votacaoRepository.deleteById(id);		
	}
}
