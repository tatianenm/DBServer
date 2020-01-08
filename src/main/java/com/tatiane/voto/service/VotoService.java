package com.tatiane.voto.service;

import com.tatiane.funcionario.model.Funcionario;
import com.tatiane.restaurante.model.RestauranteEntity;
import com.tatiane.util.DateUtil;
import com.tatiane.voto.dto.VotoDto;
import com.tatiane.voto.exception.VotoNotFoundException;
import com.tatiane.voto.model.Voto;
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

    @Autowired
    public VotoService(VotoRepository votoRepository) {
        this.votoRepository = votoRepository;
    }

    public List<Voto> findAll() {
        return votoRepository.findAll()
                .stream()
                .sorted((v1, v2) -> v2.getData().compareTo(v1.getData()))
                .collect(Collectors.toList());
    }

    public Optional<Voto> findOne(Integer id) {
        return votoRepository.findById(id);
    }

    public Voto votar(Integer idRestaurante, Integer idFuncionario) {

        Funcionario funcionario = new Funcionario();
        funcionario.setId(idFuncionario);
        RestauranteEntity restaurante = new RestauranteEntity();
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

    private boolean verificaSeFuncionarioJaVotouRestauranteMesmoDia(Funcionario funcionario, Date data, RestauranteEntity restaurante) {
        return Optional.ofNullable(votoRepository.findByFuncionarioAndDataAndRestaurante(funcionario, data, restaurante)).isPresent();
    }

    public VotoDto retornaResultadoVotacao(Date data) {
        List<Voto> votacoes = votoRepository.findByData(data);
        List<VotoDto> dtos = new ArrayList<>();

        if (votacoes != null) {
            votacoes.forEach(voto -> {
                RestauranteEntity r = voto.getRestaurante();
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
        VotoDto voto = dtos.stream()
                .max(Comparator.comparing(VotoDto::getQuantidadeVotos))
                .get();

        if (verificaSeRestauranteJaFoiEscolhidoNaSemana(voto).size() > 0) {
            throw new VotoNotFoundException(MSG_RESTAURANTE_REPETIDO + "Id: " + voto.getRestaurante().getId() + " " + voto.getRestaurante().getNome());
        }

        return voto;
    }

    public void excluir(Integer id) {
        votoRepository.deleteById(id);
    }


    private List<Voto> verificaSeRestauranteJaFoiEscolhidoNaSemana(VotoDto dto) {
        return votoRepository.verificaSeRestauranteJaFoiEscolhidoNaSemana(dto.getRestaurante().getId(),
                converteParaDate(DateUtil.converteParaLocalDate(dto.getData()).with(DayOfWeek.MONDAY)),
                converteParaDate(DateUtil.converteParaLocalDate(dto.getData()).with(DayOfWeek.FRIDAY)));

    }

    private Date converteParaDate(LocalDate localDate) {
        return DateUtil.converteParaDate(localDate);
    }

}