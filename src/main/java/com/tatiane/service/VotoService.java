package com.tatiane.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.tatiane.exception.VotoNotFoundException;
import com.tatiane.model.Funcionario;
import com.tatiane.model.Restaurante;
import com.tatiane.model.Voto;
import com.tatiane.model.dto.VotoDto;
import com.tatiane.repository.VotoRepository;

@Service
public class VotoService {

	private Logger logger = LoggerFactory.getLogger(VotoService.class);
	
	private VotoRepository votoRepository;

	public VotoService(VotoRepository votoRepository) {
		this.votoRepository = votoRepository;
	}

	public List<Voto> findAll() {
	    return votoRepository.findAll()
	    		                 .stream()
	    		                 .sorted((v1,v2)-> v2.getData().compareTo(v1.getData()))
	    		                 .collect(Collectors.toList());
	}

	public Optional<Voto> findOne(Integer id) {
		logger.debug("find:" + id);
		return votoRepository.findById(id);
	}

	public Voto votar(Integer idRestaurante, Integer idFuncionario) throws Exception  {

		Funcionario funcionario = new Funcionario();
		funcionario.setId(idFuncionario);
		Restaurante restaurante = new Restaurante();
		restaurante.setId(idRestaurante);

		if (verificaSeFuncionarioJaVotouRestauranteMesmoDia(funcionario, new Date(), restaurante)) {
			throw new VotoNotFoundException();
		}
		Voto voto = new Voto();
		voto.setData(new Date());
		voto.setFuncionario(funcionario);
		voto.setRestaurante(restaurante);
		voto.setEscolhido(false);
		return votoRepository.save(voto);

	}

	private boolean verificaSeFuncionarioJaVotouRestauranteMesmoDia(Funcionario funcionario, Date data, Restaurante restaurante) {
		return Optional.ofNullable(votoRepository.findByFuncionarioAndDataAndRestaurante(funcionario, data, restaurante)).isPresent();
	}

	public VotoDto retornaResultadoVotacao(Date data) {
		List<Voto> votacoes = votoRepository.findByData(data);
		List<VotoDto> dtos = new ArrayList<>();

		if (votacoes != null) {
			votacoes.forEach(voto -> {
				Restaurante r = voto.getRestaurante();
				VotoDto dto = new VotoDto();
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
		return dtos.stream()
				   .max((VotoDto o1, VotoDto o2) -> o1.getQuantidadeVotos().compareTo(o2.getQuantidadeVotos()))
				   .get();
	}

	public void excluir(Integer id) {
		logger.debug( id + "voto removido" );
		votoRepository.deleteById(id);		
	}
}
