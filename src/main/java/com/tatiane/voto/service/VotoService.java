package com.tatiane.voto.service;

import com.tatiane.funcionario.converter.FuncionarioConverter;
import com.tatiane.funcionario.dto.FuncionarioDTO;
import com.tatiane.restaurante.converter.RestauranteConverter;
import com.tatiane.restaurante.dto.RestauranteDTO;
import com.tatiane.restaurante.model.RestauranteEntity;
import com.tatiane.voto.converter.VotoConverter;
import com.tatiane.voto.dto.VotarDto;
import com.tatiane.voto.dto.VotacaoDto;
import com.tatiane.voto.exception.VotoBusinessException;
import com.tatiane.voto.exception.VotoNotFoundException;
import com.tatiane.voto.model.VotoEntity;
import com.tatiane.voto.repository.VotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class VotoService {

    public static final String MSG_RESTAURANTE_REPETIDO = "O restaurante escolhido já foi selecionado esta semana.";

    public static final String MSG_VOTO_REPETIDO = "O funcionário já votou na data de hoje no mesmo restaurante";

    private VotoRepository votoRepository;

    private VotoConverter votoConverter;

    private FuncionarioConverter funcionarioConverter;

    private RestauranteConverter restauranteConverter;

    @Autowired
    public VotoService(VotoRepository votoRepository, VotoConverter votoConverter,
                       FuncionarioConverter funcionarioConverter, RestauranteConverter restauranteConverter) {
        this.votoRepository = votoRepository;
        this.votoConverter = votoConverter;
        this.funcionarioConverter = funcionarioConverter;
        this.restauranteConverter = restauranteConverter;
    }

    public List<VotoEntity> findAll() {
        return votoRepository.findAll()
                .stream()
                .sorted((v1, v2) -> v2.getData().compareTo(v1.getData()))
                .collect(Collectors.toList());
    }

    public VotoEntity votar(VotarDto votarDto) {
        if(Objects.nonNull(votarDto.getFuncionarioDTO().getId())  && Objects.nonNull(votarDto.getRestauranteDTO().getId())) {
            if (verificaSeFuncionarioJaVotouRestauranteNoMesmoDia(votarDto.getFuncionarioDTO(), LocalDate.now(),
                    votarDto.getRestauranteDTO())) {
                throw new VotoBusinessException(MSG_VOTO_REPETIDO);
            }
        }
        return votoRepository.save(votoConverter.converteParaVotoEntity(votarDto));
    }

    private boolean verificaSeFuncionarioJaVotouRestauranteNoMesmoDia(FuncionarioDTO funcionarioDTO, LocalDate data,
                                                                    RestauranteDTO restauranteDTO) {
        return Optional.ofNullable(votoRepository.
                findByFuncionarioAndDataAndRestaurante(funcionarioConverter.
                        converteParaFuncionarioEntity(funcionarioDTO), data,
                        restauranteConverter.converteParaRestauranteEntity(restauranteDTO))).isPresent();
    }

    public VotacaoDto retornaResultadoVotacao(LocalDate data) {
        List<VotoEntity> votosEntity = votoRepository.findByData(data);
        List<VotacaoDto> dtos = new ArrayList<>();

        if (votosEntity != null) {
            votosEntity.forEach(voto -> {
                RestauranteEntity restauranteEntity = voto.getRestaurante();
                VotacaoDto votacao = new VotacaoDto();
                votacao.setRestauranteDTO(restauranteConverter.converteParaRestauranteDTO(restauranteEntity));
                votacao.setQuantidadeVotos(1);
                votacao.setData(data);
                if (dtos.contains(votacao)) {
                    int i = dtos.indexOf(votacao);
                    dtos.get(i).setQuantidadeVotos(dtos.get(i).getQuantidadeVotos() + 1);
                } else {
                    dtos.add(votacao);
                }
            });
        }
        VotacaoDto votacaoDto = dtos.stream()
                .max(Comparator.comparing(VotacaoDto::getQuantidadeVotos))
                .get();

        if (verificaSeRestauranteJaFoiEscolhidoNaSemana(votacaoDto).size() > 0) {
            throw new VotoBusinessException(MSG_RESTAURANTE_REPETIDO + "Id: "
                    + votacaoDto.getRestauranteDTO().getId() + " " + votacaoDto.getRestauranteDTO().getNome());
        }
        return votacaoDto;
    }

    public void excluir(Integer id) {
        votoRepository.findById(id).orElseThrow(VotoNotFoundException::new);
        votoRepository.deleteById(id);
    }

    private List<VotoEntity> verificaSeRestauranteJaFoiEscolhidoNaSemana(VotacaoDto dto) {
        return votoRepository.
                findByRestauranteAndDataBetweenAndDataNot(restauranteConverter.converteParaRestauranteEntity(
                        dto.getRestauranteDTO()), dto.getData().with(DayOfWeek.MONDAY),
                dto.getData().with(DayOfWeek.FRIDAY), LocalDate.now());
    }

}
