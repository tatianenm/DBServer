package com.tatiane.funcionario;

import com.tatiane.funcionario.converter.FuncionarioConverter;
import com.tatiane.funcionario.dto.FuncionarioDTO;
import com.tatiane.funcionario.model.FuncionarioEntity;
import com.tatiane.funcionario.repository.FuncionarioRepository;
import com.tatiane.funcionario.service.FuncionarioService;
import com.tatiane.restaurante.model.RestauranteEntity;
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
public class FuncionarioServiceTest {

    private static final Integer ID = 2;

    private static final String NOME = "Frederico";

    private static final String USER = "frederico";

    @InjectMocks
    private FuncionarioService funcionarioService;

    @Mock
    private FuncionarioRepository funcionarioRepository;

    @Mock
    private FuncionarioConverter funcionarioConverter;

    @Test
    public void deveListarTudoDoBanco() {
        Mockito.when(funcionarioRepository.findAll()).thenReturn(Arrays.asList(mockFuncionarioEntity()));

        List<FuncionarioEntity> funcionarios = funcionarioService.findAll();

        Assert.assertEquals(ID, funcionarios.get(0).getId());
        Assert.assertEquals(NOME, funcionarios.get(0).getNome());
        Assert.assertEquals(USER, funcionarios.get(0).getUser());
    }

    @Test
    public void deveExcluirFuncionario() {
        Mockito.when(funcionarioRepository.findById(ID)).thenReturn(Optional.of(mockFuncionarioEntity()));
        funcionarioService.excluirFuncionario(ID);
        Mockito.verify(funcionarioRepository, Mockito.times(1)).deleteById(ID);
    }

    @Test
    public void devePesquisarFuncionarioPeloNome() {
        Mockito.when(funcionarioRepository.findByNomeContainingIgnoreCase(NOME)).
                thenReturn(Optional.of(Arrays.asList(mockFuncionarioEntity())));
        List<FuncionarioEntity> funcionarios = funcionarioService.pesquisarFuncionarioPeloNome(NOME);

        Assert.assertEquals(NOME, funcionarios.get(0).getNome());
    }

    private FuncionarioEntity mockFuncionarioEntity() {
        FuncionarioEntity funcionarioEntity = new FuncionarioEntity();
        funcionarioEntity.setId(ID);
        funcionarioEntity.setNome(NOME);
        funcionarioEntity.setUser(USER);
        return funcionarioEntity;
    }

    @Test
    public void deveCadastrarFuncionario(){
        Mockito.when(funcionarioRepository.save(mockFuncionarioEntity())).thenReturn(mockFuncionarioEntity());
        Mockito.when(funcionarioConverter.converteParaFuncionarioEntity(mockFuncionarioDTO()))
                .thenReturn(mockFuncionarioEntity());

       FuncionarioEntity funcionarioEntity = funcionarioService.cadastroFuncionario(mockFuncionarioDTO());

       Assert.assertNotNull(funcionarioEntity.getId());
       Assert.assertEquals(NOME, funcionarioEntity.getNome());
       Assert.assertEquals(USER, funcionarioEntity.getUser());
       Mockito.verify(funcionarioRepository, Mockito.times(1)).save(mockFuncionarioEntity());
    }

    private FuncionarioDTO mockFuncionarioDTO(){
        return FuncionarioDTO.builder()
                .id(ID)
                .nome(NOME)
                .user(USER)
                .build();
    }


}
