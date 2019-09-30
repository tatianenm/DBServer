package com.tatiane;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tatiane.model.Funcionario;
import com.tatiane.model.Restaurante;
import com.tatiane.model.Votacao;
import com.tatiane.model.dto.VotacaoDto;
import com.tatiane.service.VotacaoService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VotacaoControllerTest {
	
	private MockMvc mockMvc;
	
	@MockBean
	private VotacaoService votacaoService;
	
	@Autowired
	private WebApplicationContext context;
	
	ObjectMapper om = new ObjectMapper();
	
	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	@Test
	public void getVotacoesTest() throws Exception {		
		Mockito.when(votacaoService.findAll()).thenReturn(Arrays.asList(mockVotacao()));		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/votacao").accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();			
		String expected = 
				"[{\"id\":1,\"data\":\"2015-11-23T02:00:00.000+0000\",\"restaurante\":{\"id\":1,\"nome\":null,\"endereco\":null},"
				+ "\"funcionario\":{\"id\":1,\"nome\":null,\"senha\":null,\"user\":null},\"escolhido\":true}]";	
		
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);		
	}
	
	private Restaurante mockRestaurante() {		
		Restaurante restaurante = new Restaurante();
		restaurante.setId(1);
		return restaurante;		
	}
	
	private Funcionario mockFuncionario() {
		Funcionario funcionario = new Funcionario();
		funcionario.setId(1);
		return funcionario;		
	}
	
	private Votacao mockVotacao() {
	   return new Votacao(1, formataData(), mockRestaurante(), mockFuncionario(), Boolean.TRUE);
	}
	
	private Date formataData() {
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy"); 
		Date data = null;
		try {
			data = formato.parse("23/11/2015");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return data;
	}
	
	@Test
	public void salvarVotoRestaurante() throws Exception {
	  Mockito.when(votacaoService.votar(1,2)).thenReturn(Boolean.TRUE);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/votacao").accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();	
		System.out.println(result.getResponse().getContentAsString() + "moacir");
		String expected = 
				"[{\"data\":\"2015-11-23T02:00:00.000+0000\",\"restaurante\":{\"id\":1,\"nome\":null,\"endereco\":null},\"quantidadeVotos\":1}]";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
		
	}
	
	private VotacaoDto mockVotacaoDto() {
		VotacaoDto dto = new VotacaoDto();
		dto.setData(formataData());
		dto.setRestaurante(mockRestaurante());
		dto.setQuantidadeVotos(1);
		return dto;
	}

	
}
