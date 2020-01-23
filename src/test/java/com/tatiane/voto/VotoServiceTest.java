package com.tatiane.voto;

import com.tatiane.funcionario.converter.FuncionarioConverter;
import com.tatiane.funcionario.dto.FuncionarioDTO;
import com.tatiane.funcionario.model.FuncionarioEntity;
import com.tatiane.restaurante.converter.RestauranteConverter;
import com.tatiane.restaurante.dto.RestauranteDTO;
import com.tatiane.restaurante.model.RestauranteEntity;
import com.tatiane.voto.converter.VotoConverter;
import com.tatiane.voto.dto.VotacaoDto;
import com.tatiane.voto.dto.VotarDto;
import com.tatiane.voto.dto.VotoPesquisaDTO;
import com.tatiane.voto.model.VotoEntity;
import com.tatiane.voto.repository.VotoRepository;
import com.tatiane.voto.service.VotoService;
import com.tatiane.voto.validator.VotoValidator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class VotoServiceTest {

    private static final Integer ID = 2;

    private static final Integer ID_RESTAURANTE = 2;

    private static final Integer ID_FUNCIONARIO = 2;

    private static final LocalDate DATA = LocalDate.now();

    @InjectMocks
    private VotoService votoService;

    @Mock
    private VotoRepository votoRepository;

    @Mock
    private VotoValidator votoValidator;

    @Mock
    private VotoConverter votoConverter;

    @Mock
    private RestauranteConverter restauranteConverter;

    @Mock
    private FuncionarioConverter funcionarioConverter;

    @Test
    public void deveListarTudoDoBanco() {
        when(votoRepository.findAll()).thenReturn(Arrays.asList(mockVotoEntity()));
        when(votoConverter.converteParaVotoPesquisaDTO(any())).thenReturn(mockVotoPesquisaDTO());
        List<VotoPesquisaDTO> votos = votoService.findAll();

        Assert.assertEquals(ID, votos.get(0).getId());
        Assert.assertEquals(ID_RESTAURANTE, votos.get(0).getRestauranteDTO().getId());
        Assert.assertEquals(ID_FUNCIONARIO, votos.get(0).getFuncionarioDTO().getId());
    }

    @Test
    public void deveSalvarVotoRestauranteTeste() {
        when(votoRepository.save(any())).thenReturn(mockVotoEntity());
        VotoEntity voto = votoService.votar(mockVotarDTO());

        Assert.assertEquals(ID, voto.getId());
        Assert.assertEquals(ID_FUNCIONARIO, voto.getFuncionario().getId());
        Assert.assertEquals(ID_RESTAURANTE, voto.getRestaurante().getId());
        Assert.assertEquals(DATA, voto.getData());
        verify(votoRepository).save(any());
    }

    private VotarDto mockVotarDTO() {
        return VotarDto.builder()
                .id(ID)
                .data(LocalDate.now())
                .restauranteDTO(RestauranteDTO.builder().id(1).build())
                .funcionarioDTO(FuncionarioDTO.builder().id(1).build())
                .build();
    }

    private VotoEntity mockVotoEntity() {
        return VotoEntity.builder()
                .id(ID)
                .data(DATA)
                .funcionario(mockFuncionarioEntity())
                .restaurante(mockRestauranteEntity())
                .build();
    }

    private RestauranteEntity mockRestauranteEntity() {
      return RestauranteEntity.builder()
              .id(ID_RESTAURANTE)
              .build();
    }

    private FuncionarioEntity mockFuncionarioEntity() {
        return FuncionarioEntity.builder()
                .id(ID_FUNCIONARIO)
                .build();

    }

    @Test
    public void deveExcluirVoto() {
        when(votoRepository.findById(ID)).thenReturn(Optional.of(mockVotoEntity()));
        votoService.excluir(ID);
        verify(votoRepository, times(1)).deleteById(ID);
    }

    @Test
    public void deveRetornarResultadoVotacao() {
        when(votoRepository.findByData(DATA)).thenReturn(Arrays.asList(mockVotoEntity()));
        when(votoRepository.
                findByRestauranteAndDataBetweenAndDataNot(any(), any(), any(), any()))
                .thenReturn(Arrays.asList(mockVotoEntity()));
        VotacaoDto votacaoDto = votoService.retornaResultadoVotacao(DATA);

        Assert.assertNotNull(votacaoDto.getQuantidadeVotos());
    }

    private VotoPesquisaDTO mockVotoPesquisaDTO(){
        return VotoPesquisaDTO.builder()
                .restauranteDTO(restauranteConverter.converteParaRestauranteDTO(mockRestauranteEntity()))
                .funcionarioDTO(funcionarioConverter.converteParaFuncionarioDTO(mockFuncionarioEntity()))
                .data(DATA)
                .build();
    }


}
