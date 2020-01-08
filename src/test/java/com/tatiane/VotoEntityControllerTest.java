package com.tatiane;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tatiane.funcionario.model.FuncionarioEntity;
import com.tatiane.restaurante.model.RestauranteEntity;
import com.tatiane.voto.model.VotoEntity;
import com.tatiane.voto.service.VotoService;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VotoEntityControllerTest {

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
		//Mockito.when(votoService.findAll()).thenReturn(Arrays.asList(mockVoto()));
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/voto").accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expected = "[{\"id\":1,\"data\":\"2015-11-23T02:00:00.000+0000\",\"restaurante\":{\"id\":1,\"nome\":null,\"endereco\":null},"
				+ "\"funcionario\":{\"id\":1,\"nome\":null,\"senha\":null,\"user\":null},\"escolhido\":false}]";

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);
	}

	private RestauranteEntity mockRestaurante() {
		RestauranteEntity restaurante = new RestauranteEntity();
		restaurante.setId(1);
		return restaurante;
	}

	private FuncionarioEntity mockFuncionario() {
		FuncionarioEntity funcionario = new FuncionarioEntity();
		funcionario.setId(1);
		return funcionario;
	}
	
//	private VotoEntity mockVoto() {
//		return new VotoEntity(1, formataData(), mockRestaurante(), mockFuncionario(), Boolean.FALSE);
//	}

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

//	@Test
//	public void salvarVotoRestauranteTest() throws Exception {
//		Mockito.when(votoService.votar(1, 2)).thenReturn(mockSalvarVoto());
//		 mockMvc.perform(post("/voto/votar")
//	     .contentType("application/json")
//	     .content(om.writeValueAsString(mockSalvarVoto())))
//		 .andExpect(status().is2xxSuccessful());
//
//	}

//	private VotoEntity mockSalvarVoto() {
//		VotoEntity votoEntity = new VotoEntity();
//		votoEntity.setData(formataData());
//		votoEntity.setRestaurante(mockRestaurante());
//		votoEntity.setEscolhido(false);
//		votoEntity.setFuncionario(mockFuncionario());
//		return votoEntity;
//	}

}
