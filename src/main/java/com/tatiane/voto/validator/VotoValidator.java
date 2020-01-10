package com.tatiane.voto.validator;

import com.tatiane.funcionario.converter.FuncionarioConverter;
import com.tatiane.funcionario.dto.FuncionarioDTO;
import com.tatiane.restaurante.converter.RestauranteConverter;
import com.tatiane.restaurante.dto.RestauranteDTO;
import com.tatiane.voto.dto.VotarDto;
import com.tatiane.voto.exception.VotoBusinessException;
import com.tatiane.voto.repository.VotoRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

import static com.tatiane.voto.service.VotoService.MSG_VOTO_REPETIDO;

@Component
public class VotoValidator {

    private FuncionarioConverter funcionarioConverter;
    private VotoRepository votoRepository;
    private RestauranteConverter restauranteConverter;

    public VotoValidator(FuncionarioConverter funcionarioConverter, VotoRepository votoRepository,
                         RestauranteConverter restauranteConverter) {
        this.funcionarioConverter = funcionarioConverter;
        this.votoRepository = votoRepository;
        this.restauranteConverter = restauranteConverter;

    }

    public void validate(VotarDto votarDto) {
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

    public boolean verificaSeFuncionarioJaVotouRestauranteNoMesmoDia(FuncionarioDTO funcionarioDTO, LocalDate data,
                                                                     RestauranteDTO restauranteDTO) {
        return Optional.ofNullable(votoRepository.findByFuncionarioAndDataAndRestaurante(
                funcionarioConverter.converteParaFuncionarioEntity(funcionarioDTO), data,
                restauranteConverter.converteParaRestauranteEntity(restauranteDTO))).isPresent();
    }
}
