package com.tatiane.voto;

import com.tatiane.funcionario.dto.FuncionarioDTO;
import com.tatiane.funcionario.model.FuncionarioEntity;
import com.tatiane.restaurante.dto.RestauranteDTO;
import com.tatiane.restaurante.model.RestauranteEntity;
import com.tatiane.voto.dto.VotarDto;
import com.tatiane.voto.dto.VotoPesquisaDTO;
import com.tatiane.voto.model.VotoEntity;

import java.time.LocalDate;

public class MocksVoto {

//    private static final Integer ID = 2;
//
//    private static final Integer ID_RESTAURANTE = 2;
//
//    private static final Integer ID_FUNCIONARIO = 2;
//
//    private static final LocalDate DATA = LocalDate.now();
//
//
//    public VotoEntity mockVotoEntity(){
//        VotoEntity votoEntity = new VotoEntity();
//        votoEntity.setId(ID);
//        votoEntity.setData(DATA);
//        votoEntity.setFuncionario(mockFuncionarioEntity());
//        votoEntity.setRestaurante(mockRestauranteEntity());
//        return votoEntity;
//    }
//
//    public RestauranteEntity mockRestauranteEntity(){
//        RestauranteEntity restauranteEntity = new RestauranteEntity();
//        restauranteEntity.setId(ID_RESTAURANTE);
//        return restauranteEntity;
//    }
//
//    public FuncionarioEntity mockFuncionarioEntity(){
//        FuncionarioEntity funcionarioEntity = new FuncionarioEntity();
//        funcionarioEntity.setId(ID_FUNCIONARIO);
//        return funcionarioEntity;
//    }
//
////    public VotoPesquisaDTO mockVotoPesquisaDTO(){
////        VotoPesquisaDTO votoPesquisa = new VotoPesquisaDTO();
////        votoPesquisa.setId(mockVotoEntity().getId());
////        votoPesquisa.setData(mockVotoEntity().getData());
////        votoPesquisa.setFuncionarioDTO(mockConverteParaFuncionarioDTO(mockVotoEntity().getFuncionario()));
////        votoPesquisa.setRestauranteDTO(mockConverteParaRestauranteDTO(mockVotoEntity().getRestaurante()));
////
////        return votoPesquisa;
////    }
//
//    public RestauranteDTO mockConverteParaRestauranteDTO(RestauranteEntity restaurante){
//        return RestauranteDTO.builder()
//                .id(restaurante.getId())
//                .build();
//    }
//
//    public FuncionarioDTO mockConverteParaFuncionarioDTO(FuncionarioEntity funcionario){
//        return FuncionarioDTO.builder()
//                .id(funcionario.getId())
//                .build();
//    }
//    public VotarDto mockVotarDTO(){
//        VotarDto votar = new VotarDto();
//        votar.setId(ID);
//        votar.setData(DATA);
//        votar.setFuncionarioDTO(mockConverteParaFuncionarioDTO(mockFuncionarioEntity()));
//        votar.setRestauranteDTO(mockConverteParaRestauranteDTO(mockRestauranteEntity()));
//        return votar;
//    }
}
