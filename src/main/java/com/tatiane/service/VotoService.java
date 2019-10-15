package com.tatiane.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
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
import com.tatiane.util.DateUtil;

@Service
public class VotoService {

	private Logger logger = LoggerFactory.getLogger(VotoService.class);
	
	public static final String MSG_RESTAURANTE_REPETIDO = "O restaurante escolhido já foi selecionado esta semana.";
	
	public static final String MSG_VOTO_REPETIDO = "O funcionário já votou na data de hoje no mesmo restaurante"; 
	
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

	public Voto votar(Integer idRestaurante, Integer idFuncionario)   {

		Funcionario funcionario = new Funcionario();
		funcionario.setId(idFuncionario);
		Restaurante restaurante = new Restaurante();
		restaurante.setId(idRestaurante);

		if (verificaSeFuncionarioJaVotouRestauranteMesmoDia(funcionario, new Date(), restaurante)) {
			throw new VotoNotFoundException(MSG_VOTO_REPETIDO);
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
		VotoDto voto =dtos.stream()
				   .max((VotoDto o1, VotoDto o2) -> o1.getQuantidadeVotos().compareTo(o2.getQuantidadeVotos()))
				   .get(); 
		
		if(verificaSeRestauranteJaFoiEscolhidoNaSemana(voto).size() > 0) {
			throw new VotoNotFoundException(MSG_RESTAURANTE_REPETIDO +"Id: " + voto.getRestaurante().getId() + " "+ voto.getRestaurante().getNome());
		}
		
		return voto;
	}

	public void excluir(Integer id) {
		logger.debug( id + "voto removido" );
		votoRepository.deleteById(id);		
	}

	
    private List<Voto> verificaSeRestauranteJaFoiEscolhidoNaSemana(VotoDto dto) {
    	List<Voto> votos =
	   votoRepository.verificaSeRestauranteJaFoiEscolhidoNaSemana(dto.getRestaurante().getId(),
			                   converteParaDate(DateUtil.converteParaLocalDate(dto.getData()).with(DayOfWeek.MONDAY)),
				               converteParaDate(DateUtil.converteParaLocalDate(dto.getData()).with(DayOfWeek.FRIDAY)));
return votos;
	}	
    
    private Date converteParaDate(LocalDate localDate) {
       return DateUtil.converteParaDate(localDate);
    }
    
    
	
}
