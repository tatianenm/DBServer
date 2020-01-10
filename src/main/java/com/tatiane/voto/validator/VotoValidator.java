package com.tatiane.voto.validator;

import com.tatiane.funcionario.converter.FuncionarioConverter;
import com.tatiane.funcionario.dto.FuncionarioDTO;
import com.tatiane.restaurante.converter.RestauranteConverter;
import com.tatiane.restaurante.dto.RestauranteDTO;
import com.tatiane.voto.dto.VotacaoDto;
import com.tatiane.voto.dto.VotarDto;
import com.tatiane.voto.exception.VotoBusinessException;
import com.tatiane.voto.model.VotoEntity;
import com.tatiane.voto.repository.VotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Component
public class VotoValidator {

    public static final String MSG_VOTO_REPETIDO = "O funcionário já votou na data de hoje no mesmo restaurante.";

    public static final String MSG_RESTAURANTE_REPETIDO = "O restaurante escolhido já foi selecionado esta semana.";

    private FuncionarioConverter funcionarioConverter;

    private VotoRepository votoRepository;

    private RestauranteConverter restauranteConverter;

    @Autowired
    public VotoValidator(FuncionarioConverter funcionarioConverter, VotoRepository votoRepository,
                         RestauranteConverter restauranteConverter) {
        this.funcionarioConverter = funcionarioConverter;
        this.votoRepository = votoRepository;
        this.restauranteConverter = restauranteConverter;

    }

    public void validaSeFuncionarioJaVotouRestauranteNoMesmoDia(VotarDto votarDto) {
        if (Objects.nonNull(votarDto.getFuncionarioDTO()) && Objects.nonNull(votarDto.getRestauranteDTO())) {
            if (Objects.nonNull(votarDto.getFuncionarioDTO().getId()) && Objects.nonNull(
                    votarDto.getRestauranteDTO().getId())) {
                if (verificaSeFuncionarioJaVotouRestauranteNoMesmoDia(votarDto.getFuncionarioDTO(), LocalDate.now(),
                        votarDto.getRestauranteDTO())) {
                    throw new VotoBusinessException(MSG_VOTO_REPETIDO);
                }
            }
        }
    }

    private boolean verificaSeFuncionarioJaVotouRestauranteNoMesmoDia(FuncionarioDTO funcionarioDTO, LocalDate data,
                                                                      RestauranteDTO restauranteDTO) {
        return Optional.ofNullable(votoRepository.findByFuncionarioAndDataAndRestaurante(
                funcionarioConverter.converteParaFuncionarioEntity(funcionarioDTO), data,
                restauranteConverter.converteParaRestauranteEntity(restauranteDTO))).isPresent();
    }

    public void validaSeRestauranteJaFoiEscolhidoNaSemana(VotacaoDto votacaoDto) {
        if (verificaSeRestauranteJaFoiEscolhidoNaSemana(votacaoDto).size() > 0) {
            throw new VotoBusinessException(MSG_RESTAURANTE_REPETIDO + "Id: "
                    + votacaoDto.getRestauranteDTO().getId() + " " + votacaoDto.getRestauranteDTO().getNome());
        }
    }

    private List<VotoEntity> verificaSeRestauranteJaFoiEscolhidoNaSemana(VotacaoDto dto) {
        return votoRepository.
                findByRestauranteAndDataBetweenAndDataNot(restauranteConverter.converteParaRestauranteEntity(
                        dto.getRestauranteDTO()), dto.getData().with(DayOfWeek.MONDAY),
                        dto.getData().with(DayOfWeek.FRIDAY), LocalDate.now());
    }

}
