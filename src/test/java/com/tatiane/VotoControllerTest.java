package com.tatiane;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import com.tatiane.model.Voto;
import com.tatiane.service.VotoService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VotoControllerTest {

	private MockMvc mockMvc;

	@MockBean
	private VotoService votoService;

	@Autowired
	private WebApplicationContext context;

	ObjectMapper om = new ObjectMapper();

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	public void getVotosTest() throws Exception {
		Mockito.when(votoService.findAll()).thenReturn(Arrays.asList(mockVoto()));
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/voto").accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expected = "[{\"id\":1,\"data\":\"2015-11-23T02:00:00.000+0000\",\"restaurante\":{\"id\":1,\"nome\":null,\"endereco\":null},"
				+ "\"funcionario\":{\"id\":1,\"nome\":null,\"senha\":null,\"user\":null},\"escolhido\":false}]";

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
	
	private Voto mockVoto() {
		return new Voto(1, formataData(), mockRestaurante(), mockFuncionario(), Boolean.FALSE);
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
	public void salvarVotoRestauranteTest() throws Exception {
		Mockito.when(votoService.votar(1, 2)).thenReturn(mockSalvarVoto());
		 mockMvc.perform(post("/voto/votar")
	     .contentType("application/json")
	     .content(om.writeValueAsString(mockSalvarVoto())))
		 .andExpect(status().is2xxSuccessful());

	}

	private Voto mockSalvarVoto() {
		Voto voto = new Voto();
		voto.setData(formataData());
		voto.setRestaurante(mockRestaurante());
		voto.setEscolhido(false);
		voto.setFuncionario(mockFuncionario());
		return voto;
	}

}