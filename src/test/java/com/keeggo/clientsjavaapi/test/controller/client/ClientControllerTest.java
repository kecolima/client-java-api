package com.keeggo.clientsjavaapi.test.controller.client;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.ParseException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.keeggo.clientsjavaapi.dto.model.client.ClientDTO;
import com.keeggo.clientsjavaapi.model.client.Client;
import com.keeggo.clientsjavaapi.service.client.ClientService;

/**
 * Class that implements tests of the TransactionController features
 * 
 * @author Mariana Azevedo
 * @since 05/04/2020
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, MockitoTestExecutionListener.class })
public class ClientControllerTest {	
	
	static final Long ID = 1L;
	static final String NAME = "Client";
	static final String CPF = "123456789";
	static final String ADDRESS = "Rua";
	static final String NUMBER = "34";
	static final String ZIP_CODE = "19901230";	
	static final String DISTRICT = "Jardim";	
	static final String STATE = "São Paulo";
	static final String CITY = "Ourinhos";	
	static final String URL = "/api-clients/v1/clients";
	
	HttpHeaders headers;

	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	ClientService clientService;
	
	@BeforeAll
	private void setUp() {
		headers = new HttpHeaders();
        headers.set("X-api-key", "FX001-ZBSY6YSLP");
	}
	
	/**
	 * Method that tests to save an object Client in the API
	 * 
	 * @author Mariana Azevedo
	 * @since 05/04/2020
	 * 
	 * @throws Exception
	 */
	@Test
	@Order(1)
	public void testSave() throws Exception {
		
		BDDMockito.given(clientService.save(Mockito.any(Client.class))).willReturn(getMockClient());
		
		mockMvc.perform(MockMvcRequestBuilders
			.post(URL)
			.content(getJsonPayload(ID, NAME, CPF, ADDRESS, NUMBER, ZIP_CODE, DISTRICT, STATE, CITY))
			.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
			.headers(headers))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.data.id").value(ID))
		.andExpect(jsonPath("$.data.name").value(NAME))
		.andExpect(jsonPath("$.data.cpf").value(CPF))
		.andExpect(jsonPath("$.data.address").value(ADDRESS))
		.andExpect(jsonPath("$.data.numberHouse").value(NUMBER))
		.andExpect(jsonPath("$.data.zipCode").value(ZIP_CODE))
		.andExpect(jsonPath("$.data.district").value(DISTRICT))	
		.andExpect(jsonPath("$.data.state").value(STATE))
		.andExpect(jsonPath("$.data.city").value(CITY));
	}

	/**
	 * Method that fill a mock User object to use as return in the tests.
	 * 
	 * @author Mariana Azevedo
	 * @since 13/12/2020
	 * 
	 * @return <code>User</code> object
	 * @throws ParseException 
	 */
	private Client getMockClient() throws ParseException {
		return new Client(1L, "Client", "123456789", "Rua", "34", "19901230", "Jardim", "São Paulo", "Ourinhos");
	}
	
	/**
	 * Method that fill a mock UserDTO object to use as return in the tests.
	 * 
	 * @author Mariana Azevedo
	 * @since 13/12/2020
	 * 
	 * @param id
	 * @param accountNumber
	 * @param accountType
	 * @return <code>String</code> with the UserDTO payload
	 * 
	 * @throws JsonProcessingException
	 */
	private String getJsonPayload(Long id, String name, String cpf, String address, String number, String zipCode, String district, String state, String city) throws JsonProcessingException {
		
		ClientDTO dto = new ClientDTO(id, name, cpf, address, number, zipCode, district, state, city);
		return new ObjectMapper().writeValueAsString(dto);
	}
	/*
	@AfterAll
	private void tearDown() {
		clientService.deleteById(1L);
	}
	*/

}
