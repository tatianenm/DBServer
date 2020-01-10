package com.tatiane.voto;

import com.tatiane.funcionario.converter.FuncionarioConverter;
import com.tatiane.funcionario.dto.FuncionarioDTO;
import com.tatiane.restaurante.converter.RestauranteConverter;
import com.tatiane.restaurante.dto.RestauranteDTO;
import com.tatiane.voto.dto.VotarDto;
import com.tatiane.voto.model.VotoEntity;
import com.tatiane.voto.repository.VotoRepository;
import com.tatiane.voto.service.VotoService;

import java.time.LocalDate;

import com.tatiane.voto.validator.VotoValidator;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class VotoValidatorTest {

    @InjectMocks
    private VotoValidator restauranteValidator;
    @Mock
    private FuncionarioConverter funcionarioConverter;
    @Mock
    private RestauranteConverter restauranteConverter;
    @Mock
    private VotoRepository votoRepository;
    @Rule
    public ExpectedException exception = ExpectedException.none();


    @Test
    public void testValidateDeveRetornarErro() {
        exception.expectMessage(VotoService.MSG_VOTO_REPETIDO);
        Mockito.when(votoRepository.findByFuncionarioAndDataAndRestaurante(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(new VotoEntity());
        this.restauranteValidator.validate(mockVotarDTO());
        Mockito.verify(votoRepository).findByFuncionarioAndDataAndRestaurante(Mockito.any(), Mockito.any(), Mockito.any());
    }

    private VotarDto mockVotarDTO() {
        VotarDto votar = new VotarDto();
        votar.setId(1);
        votar.setData(LocalDate.now());
        votar.setRestauranteDTO(RestauranteDTO.builder().id(1).build());
        votar.setFuncionarioDTO(FuncionarioDTO.builder().id(1).build());
        return votar;
    }

}
