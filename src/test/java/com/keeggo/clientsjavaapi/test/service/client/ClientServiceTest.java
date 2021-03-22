package com.keeggo.clientsjavaapi.test.service.client;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.keeggo.clientsjavaapi.model.client.Client;
import com.keeggo.clientsjavaapi.repository.client.ClientRepository;
import com.keeggo.clientsjavaapi.service.client.ClientService;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, MockitoTestExecutionListener.class })
public class ClientServiceTest {
	@Autowired
	private ClientService service;

	@MockBean
	private ClientRepository repository;
	
	
	/**
	 * Method to test the creation of an Client.
	 * 
	 * @author Mariana Azevedo
	 * @since 08/12/2020
	 */
	@Test
	@Order(1)
	public void testSave() {
		
		BDDMockito.given(repository.save(Mockito.any(Client.class)))
			.willReturn(getMockClient());
		Client response = service.save(new Client());
		
		assertNotNull(response);
	}
	
	
	/**
	 * Method that fill a mock of a Client to use as return in the tests.
	 * 
	 * @author Mariana Azevedo
	 * @since 08/12/2020
	 * 
	 * @return <code>Client</code> object
	 */
	private Client getMockClient() {
		return new Client(2L, "Client", "123456789", "Rua", "Jardim", "34", "São Paulo", "Ourinhos", "19901230");
	}
	
	/**
	 * Method to remove all Client test data.
	 * 
	 * @author Mariana Azevedo
	 * @since 25/10/2020
	 */
	@AfterAll
	private void tearDown() {
		repository.deleteAll();
	}
}
