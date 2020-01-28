package com.tatiane.voto;

import com.tatiane.funcionario.converter.FuncionarioConverter;
import com.tatiane.funcionario.dto.FuncionarioDTO;
import com.tatiane.funcionario.model.FuncionarioEntity;
import com.tatiane.restaurante.converter.RestauranteConverter;
import com.tatiane.restaurante.dto.RestauranteDTO;
import com.tatiane.restaurante.model.RestauranteEntity;
import com.tatiane.voto.dto.VotacaoDto;
import com.tatiane.voto.dto.VotarDto;
import com.tatiane.voto.model.VotoEntity;
import com.tatiane.voto.repository.VotoRepository;
import com.tatiane.voto.validator.VotoValidator;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Arrays;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class VotoValidatorTest {

    @InjectMocks
    private VotoValidator votoValidator;
    @Mock
    private FuncionarioConverter funcionarioConverter;
    @Mock
    private RestauranteConverter restauranteConverter;
    @Mock
    private VotoRepository votoRepository;
    @Rule
    public ExpectedException exception = ExpectedException.none();


    @Test
    public void deveRetornarErroVotoRepetido() {
        exception.expectMessage(VotoValidator.MSG_VOTO_REPETIDO);
        when(votoRepository.findByFuncionarioAndDataAndRestaurante(any(), any(), any()))
                .thenReturn(new VotoEntity());
        votoValidator.validaSeFuncionarioJaVotouRestauranteNoMesmoDia(mockVotarDTO());
        verify(votoRepository).findByFuncionarioAndDataAndRestaurante(any(), any(), any());
    }

    private VotarDto mockVotarDTO() {
        return VotarDto.builder()
                .id(1)
                .data(LocalDate.now())
                .restauranteDTO(RestauranteDTO.builder().id(1).build())
                .funcionarioDTO(FuncionarioDTO.builder().id(1).build())
                .build();
    }

    @Test
    public void deveRetornarErroRestauranteRepetidoNaSemana() {
        exception.expectMessage(VotoValidator.MSG_RESTAURANTE_REPETIDO);
        when(votoRepository.
                findByRestauranteAndDataBetweenAndDataNot(any(), any(), any(), any()))
                .thenReturn(Arrays.asList(mockVotoEntity()));
        votoValidator.validaSeRestauranteJaFoiEscolhidoNaSemana(mockVotacaoDTO());
        verify(votoRepository)
                .findByRestauranteAndDataBetweenAndDataNot(any(), any(), any(), any());
    }

    private VotoEntity mockVotoEntity() {
        return VotoEntity.builder()
                .id(1)
                .funcionario(FuncionarioEntity.builder().id(1).build())
                .restaurante(RestauranteEntity.builder().id(1).build())
                .data(LocalDate.now())
                .build();
    }

    private VotacaoDto mockVotacaoDTO() {
        return VotacaoDto.builder()
                .data(LocalDate.of(2020, 01, 8))
                .restauranteDTO(RestauranteDTO.builder().id(1).build())
                .quantidadeVotos(3)
                .build();
    }

}
