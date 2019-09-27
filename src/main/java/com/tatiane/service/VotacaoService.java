package com.tatiane.service;

import com.tatiane.model.Funcionario;
import com.tatiane.model.Restaurante;
import com.tatiane.model.Votacao;
import com.tatiane.model.dto.VotacaoDto;
import com.tatiane.repository.VotacaoRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class VotacaoService {

    private Logger logger = LoggerFactory.getLogger(VotacaoService.class);

    private VotacaoRepository votacaoRepository;

    public VotacaoService(VotacaoRepository votacaoRepository) {
        this.votacaoRepository = votacaoRepository;
    }

    public List<Votacao> findAll() {
        List<Votacao> votacoes = votacaoRepository.findAll();
        return votacoes;
    }

    public Optional<Votacao> findOne(Integer id) {
        logger.debug("find:" + id);
        Optional<Votacao> ent = votacaoRepository.findById(id);
        return ent;
    }

//   private Boolean verificaSeExisteIdRestauranteRepetidoNoBanco(Integer idRestaurante) {
//	   Calendar primeiroDiaSemana  = new GregorianCalendar();
//	   int semanaMinima = primeiroDiaSemana.getActualMinimum(Calendar.DAY_OF_WEEK_IN_MONTH);
//	   primeiroDiaSemana.set(Calendar.DAY_OF_WEEK,semanaMinima);
//
//	   Calendar ultimoDiaSemana  = new GregorianCalendar();
//	   int semanaMaxima =  ultimoDiaSemana.getActualMaximum(Calendar.DAY_OF_WEEK);
//	   ultimoDiaSemana.set(Calendar.DAY_OF_WEEK,semanaMaxima);
//
//	   Date dtInicioSemana = primeiroDiaSemana.getTime();
//	   Date dtFimSemana =  ultimoDiaSemana.getTime();
//
//	   List<Votacao>  votacao = votacaoRepository.verificaSeExisteIdRestauranteNoBanco(idRestaurante,dtInicioSemana, dtFimSemana);
//	   return  Optional.ofNullable(votacao).isPresent();
//   }
    public boolean votar(Integer idRestaurante, Integer idFuncionario) {

        Funcionario funcionario = new Funcionario();
        funcionario.setId(idFuncionario);
        Restaurante restaurante = new Restaurante();
        restaurante.setId(idRestaurante);
        // boolean restauranteRepetido =
        // verificaSeExisteIdRestauranteRepetidoNoBanco(idRestaurante);
        boolean votoRepetido = verificaSeFuncionarioJaVotouRestauranteMesmoDia(funcionario, new Date());
        if (votoRepetido == true) {
            return false;
        } else {
            Votacao votacao = new Votacao();
            votacao.setData(new Date());
            votacao.setFuncionario(funcionario);
            votacao.setRestaurante(restaurante);
            votacaoRepository.save(votacao);
        }
        return true;
    }

    private boolean verificaSeFuncionarioJaVotouRestauranteMesmoDia(Funcionario funcionario, Date data) {
        Votacao votacao = votacaoRepository.findByFuncionarioAndData(funcionario, data);
        return Optional.ofNullable(votacao).isPresent();
    }

    public VotacaoDto retornaResultadoVotacao(Date data) {
        List<Votacao> votacoes = votacaoRepository.findByData(data);
        List<VotacaoDto> dtos = new ArrayList<>();
        VotacaoDto dtoRetorno = null;

        if (votacoes != null) {
            votacoes.forEach(voto -> {
                Restaurante r = voto.getRestaurante();
                VotacaoDto dto = new VotacaoDto();
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
        dtoRetorno = dtos.stream()
                .max((VotacaoDto o1, VotacaoDto o2) -> o1.getQuantidadeVotos().compareTo(o2.getQuantidadeVotos())).get();
        return dtoRetorno;
    }

}
