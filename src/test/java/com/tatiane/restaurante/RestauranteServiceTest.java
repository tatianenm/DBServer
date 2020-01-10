package com.tatiane.restaurante;

import com.tatiane.restaurante.converter.RestauranteConverter;
import com.tatiane.restaurante.dto.RestauranteDTO;
import com.tatiane.restaurante.model.RestauranteEntity;
import com.tatiane.restaurante.repository.RestauranteRepository;
import com.tatiane.restaurante.service.RestauranteService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
public class RestauranteServiceTest {

    private static final Integer ID = 2;

    private static final String NOME = "Restaurante A";

    private static final String ENDERECO = "Assis Brasil";


    @InjectMocks
    private RestauranteService restauranteService;

    @Mock
    private RestauranteRepository restauranteRepository;

    @Mock
    private RestauranteConverter restauranteConverter;

    @Test
    public void deveListarTudoDoBanco(){
        Mockito.when(restauranteRepository.findAll()).thenReturn(Arrays.asList(mockRestauranteEntity()));

       List<RestauranteEntity> restaurantes = restauranteService.findAll();

        Assert.assertEquals(ID, restaurantes.get(0).getId());
        Assert.assertEquals(NOME, restaurantes.get(0).getNome());
        Assert.assertEquals(ENDERECO, restaurantes.get(0).getEndereco());
    }

    @Test
    public void devePesquisarPeloIdRestaurante(){
        Mockito.when(restauranteRepository.findById(ID)).thenReturn(Optional.of(mockRestauranteEntity()));

        RestauranteEntity restauranteEntity = restauranteService.findById(ID);

        Assert.assertEquals(ID, restauranteEntity.getId());
    }

    @Test
    public void deveExcluirRestaurante(){
        Mockito.when(restauranteRepository.findById(ID)).thenReturn(Optional.of(mockRestauranteEntity()));
        restauranteService.excluirRestaurante(ID);
        Mockito.verify(restauranteRepository, Mockito.times(1)).deleteById(ID);
        }

    private RestauranteEntity mockRestauranteEntity(){
        RestauranteEntity restauranteEntity = new RestauranteEntity();
        restauranteEntity.setId(ID);
        restauranteEntity.setEndereco(ENDERECO);
        restauranteEntity.setNome(NOME);
        return restauranteEntity;
    }

    private RestauranteDTO mockRestauranteDTO(){
        RestauranteDTO restauranteDTO = new RestauranteDTO();
        restauranteDTO.setId(ID);
        restauranteDTO.setEndereco(ENDERECO);
        restauranteDTO.setNome(NOME);
        return restauranteDTO;
    }


}
