package com.tatiane.voto.service;

import com.tatiane.funcionario.converter.FuncionarioConverter;
import com.tatiane.funcionario.dto.FuncionarioDTO;
import com.tatiane.funcionario.model.FuncionarioEntity;
import com.tatiane.restaurante.converter.RestauranteConverter;
import com.tatiane.restaurante.dto.RestauranteDTO;
import com.tatiane.restaurante.model.RestauranteEntity;
import com.tatiane.util.DateUtil;
import com.tatiane.voto.converter.VotoConverter;
import com.tatiane.voto.dto.VotarDto;
import com.tatiane.voto.dto.VotoDto;
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

    public Optional<VotoEntity> findById(Integer id) {
        return votoRepository.findById(id);
    }

    public VotoEntity votar(VotarDto votarDto) {

//        FuncionarioEntity funcionario = new FuncionarioEntity();
//        funcionario.setId(idFuncionario);
//        RestauranteEntity restaurante = new RestauranteEntity();
//        restaurante.setId(idRestaurante);

        if (verificaSeFuncionarioJaVotouRestauranteNoMesmoDia(votarDto.getFuncionarioDTO(), LocalDate.now(),
                votarDto.getRestauranteDTO())) {
            throw new VotoBusinessException();
        }
//        VotoEntity votoEntity = new VotoEntity();
//        votoEntity.setData(LocalDate.now());
//        votoEntity.setFuncionario(funcionario);
//        votoEntity.setRestaurante(restaurante);
//        votoEntity.setEscolhido(false);
        return votoRepository.save(votoConverter.converteParaVotoEntity(votarDto));

    }

    private boolean verificaSeFuncionarioJaVotouRestauranteNoMesmoDia(FuncionarioDTO funcionarioDTO, LocalDate data,
                                                                    RestauranteDTO restauranteDTO) {
        return Optional.ofNullable(votoRepository.
                findByFuncionarioAndDataAndRestaurante(funcionarioConverter.
                        converteParaFuncionarioEntity(funcionarioDTO), data,
                        restauranteConverter.converteParaRestauranteEntity(restauranteDTO))).isPresent();
    }

//    public VotoDto retornaResultadoVotacao(Date data) {
//        List<VotoEntity> votacoes = votoRepository.findByData(data);
//        List<VotoDto> dtos = new ArrayList<>();
//
//        if (votacoes != null) {
//            votacoes.forEach(voto -> {
//                RestauranteEntity r = voto.getRestaurante();
//                VotoDto dto = new VotoDto();
//                dto.setRestaurante(r);
//                dto.setQuantidadeVotos(1);
//                dto.setData(data);
//                if (dtos.contains(dto)) {
//                    int i = dtos.indexOf(dto);
//                    dtos.get(i).setQuantidadeVotos(dtos.get(i).getQuantidadeVotos() + 1);
//                } else {
//                    dtos.add(dto);
//                }
//            });
//        }
//        VotoDto voto = dtos.stream()
//                .max(Comparator.comparing(VotoDto::getQuantidadeVotos))
//                .get();
//
//        if (verificaSeRestauranteJaFoiEscolhidoNaSemana(voto).size() > 0) {
//            throw new VotoNotFoundException(MSG_RESTAURANTE_REPETIDO + "Id: " + voto.getRestaurante().getId() + " " + voto.getRestaurante().getNome());
//        }
//
//        return voto;
//    }

    public void excluir(Integer id) {
        votoRepository.deleteById(id);
    }


//    private List<VotoEntity> verificaSeRestauranteJaFoiEscolhidoNaSemana(VotoDto dto) {
//        return votoRepository.verificaSeRestauranteJaFoiEscolhidoNaSemana(dto.getRestaurante().getId(),
//                converteParaDate(DateUtil.converteParaLocalDate(dto.getData()).with(DayOfWeek.MONDAY)),
//                converteParaDate(DateUtil.converteParaLocalDate(dto.getData()).with(DayOfWeek.FRIDAY)));
//
//    }

    private Date converteParaDate(LocalDate localDate) {
        return DateUtil.converteParaDate(localDate);
    }

}
