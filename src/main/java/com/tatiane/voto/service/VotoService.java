package com.tatiane.voto.service;

import com.tatiane.funcionario.converter.FuncionarioConverter;
import com.tatiane.restaurante.converter.RestauranteConverter;
import com.tatiane.restaurante.dto.RestauranteDTO;
import com.tatiane.restaurante.model.RestauranteEntity;
import com.tatiane.voto.converter.VotoConverter;
import com.tatiane.voto.dto.VotacaoDto;
import com.tatiane.voto.dto.VotarDto;
import com.tatiane.voto.exception.VotoNotFoundException;
import com.tatiane.voto.model.VotoEntity;
import com.tatiane.voto.repository.VotoRepository;
import com.tatiane.voto.validator.VotoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VotoService {

    private VotoRepository votoRepository;

    private VotoConverter votoConverter;

    private FuncionarioConverter funcionarioConverter;

    private RestauranteConverter restauranteConverter;

    private VotoValidator votoValidator;

    @Autowired
    public VotoService(VotoRepository votoRepository, VotoConverter votoConverter,
                       FuncionarioConverter funcionarioConverter, RestauranteConverter restauranteConverter,
                       VotoValidator votoValidator) {
        this.votoRepository = votoRepository;
        this.votoConverter = votoConverter;
        this.funcionarioConverter = funcionarioConverter;
        this.restauranteConverter = restauranteConverter;
        this.votoValidator = votoValidator;
    }

    public List<VotoEntity> findAll() {
        return votoRepository.findAll()
                .stream()
                .sorted((v1, v2) -> v2.getData().compareTo(v1.getData()))
                .collect(Collectors.toList());
    }

    public VotoEntity votar(VotarDto votarDto) {
        votoValidator.validaSeFuncionarioJaVotouRestauranteNoMesmoDia(votarDto);
        return votoRepository.save(votoConverter.converteParaVotoEntity(votarDto));
    }

    public VotacaoDto retornaResultadoVotacao(LocalDate data) {
        List<VotoEntity> votosEntity = votoRepository.findByData(data);
        List<VotacaoDto> dtos = new ArrayList<>();

        if (votosEntity != null) {
            votosEntity.forEach(voto -> {
                VotacaoDto votacao = new VotacaoDto();
                votacao.setRestauranteDTO(restauranteConverter.converteParaRestauranteDTO(voto.getRestaurante()));
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
        votoValidator.validaSeRestauranteJaFoiEscolhidoNaSemana(votacaoDto);

        return votacaoDto;
    }

    public void excluir(Integer id) {
        votoRepository.findById(id).orElseThrow(VotoNotFoundException::new);
        votoRepository.deleteById(id);
    }
}
