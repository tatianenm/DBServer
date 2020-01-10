package com.tatiane.voto;

import com.tatiane.funcionario.dto.FuncionarioDTO;
import com.tatiane.funcionario.model.FuncionarioEntity;
import com.tatiane.restaurante.dto.RestauranteDTO;
import com.tatiane.restaurante.model.RestauranteEntity;
import com.tatiane.voto.converter.VotoConverter;
import com.tatiane.voto.dto.VotarDto;
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

    @Test
    public void deveListarTudoDoBanco() {
        Mockito.when(votoRepository.findAll()).thenReturn(Arrays.asList(mockVotoEntity()));
        List<VotoEntity> votos = votoService.findAll();

        Assert.assertEquals(ID, votos.get(0).getId());
        Assert.assertEquals(ID_RESTAURANTE, votos.get(0).getRestaurante().getId());
        Assert.assertEquals(ID_FUNCIONARIO, votos.get(0).getFuncionario().getId());
    }

    @Test
    public void deveSalvarVotoRestauranteTeste() {
        VotoEntity votoEntity = new VotoEntity();
        votoEntity.setId(ID);
        votoEntity.setRestaurante(mockRestauranteEntity());
        votoEntity.setFuncionario(mockFuncionarioEntity());
        votoEntity.setData(DATA);

        Mockito.when(votoRepository.save(Mockito.any())).thenReturn(mockVotoEntity());
        VotoEntity voto = votoService.votar(mockVotarDTO());

        Assert.assertEquals(ID, voto.getId());
        Assert.assertEquals(ID_FUNCIONARIO, voto.getFuncionario().getId());
        Assert.assertEquals(ID_RESTAURANTE, voto.getRestaurante().getId());
        Assert.assertEquals(DATA, voto.getData());
        Mockito.verify(votoRepository).save(Mockito.any());
    }

    private VotarDto mockVotarDTO() {
        VotarDto votar = new VotarDto();
        votar.setId(ID);
        votar.setData(LocalDate.now());
        votar.setRestauranteDTO(RestauranteDTO.builder().id(1).build());
        votar.setFuncionarioDTO(FuncionarioDTO.builder().id(1).build());
        return votar;
    }

    private VotoEntity mockVotoEntity() {
        VotoEntity votoEntity = new VotoEntity();
        votoEntity.setId(ID);
        votoEntity.setData(DATA);
        votoEntity.setFuncionario(mockFuncionarioEntity());
        votoEntity.setRestaurante(mockRestauranteEntity());
        return votoEntity;
    }

    public RestauranteEntity mockRestauranteEntity() {
        RestauranteEntity restauranteEntity = new RestauranteEntity();
        restauranteEntity.setId(ID_RESTAURANTE);
        return restauranteEntity;
    }

    public FuncionarioEntity mockFuncionarioEntity() {
        FuncionarioEntity funcionarioEntity = new FuncionarioEntity();
        funcionarioEntity.setId(ID_FUNCIONARIO);
        return funcionarioEntity;
    }

    @Test
    public void deveExcluirVoto() {
        Mockito.when(votoRepository.findById(ID)).thenReturn(Optional.of(mockVotoEntity()));
        votoService.excluir(ID);
        Mockito.verify(votoRepository, Mockito.times(1)).deleteById(ID);
    }
}
