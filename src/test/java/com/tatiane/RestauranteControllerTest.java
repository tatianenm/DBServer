package com.tatiane;

import java.util.Arrays;

import org.json.JSONArray;
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

import com.tatiane.model.Restaurante;
import com.tatiane.service.RestauranteService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RestauranteControllerTest {
	
	
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext webContext;
	
	@MockBean
	private RestauranteService restauranteService; 
	
	@Before
	public void SetUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
	}
	
	@Test
	public void getRestaurantesTest() throws Exception {
		Restaurante mockRestaurante = new Restaurante(1, "xxxx", "cccc");
		
		Mockito.when(restauranteService.findAll()).thenReturn(Arrays.asList(mockRestaurante));
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/restaurante")
				                        .accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expected = "[{id:1, nome:'xxxx', endereco:'cccc' }]";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

}
