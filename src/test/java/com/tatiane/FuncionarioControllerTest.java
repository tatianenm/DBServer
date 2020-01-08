package com.tatiane;

import org.assertj.core.util.Lists;
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

import com.tatiane.funcionario.model.Funcionario;
import com.tatiane.funcionario.service.FuncionarioService;


@RunWith(SpringRunner.class)
@SpringBootTest
public class FuncionarioControllerTest {
 
	
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext context;
	
	@MockBean
	private FuncionarioService funcionarioService;   

	
    @Before
    public void setUp() {
    	mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    

    @Test
    public void getFuncionariosTest() throws Exception {
    	Funcionario mockFunc = new Funcionario(2, "jose", "123", "jose");
    	Mockito.when(funcionarioService.findAll()).thenReturn(Lists.newArrayList(mockFunc));
    	
    	RequestBuilder requestBuilder = MockMvcRequestBuilders
    			                        .get("/funcionario").accept(MediaType.APPLICATION_JSON);
    	MvcResult result = mockMvc.perform(requestBuilder).andReturn();
    	
    	String expected = "[{id:2,nome:'jose',senha:'123',user:'jose'}]";
    	JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);    	

    }
    
    
}
