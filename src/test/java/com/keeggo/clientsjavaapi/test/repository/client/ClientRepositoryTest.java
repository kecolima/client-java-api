package com.keeggo.clientsjavaapi.test.repository.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.keeggo.clientsjavaapi.model.client.Client;
import com.keeggo.clientsjavaapi.repository.client.ClientRepository;

@SpringBootTest
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
public class ClientRepositoryTest {	

	@Autowired
	ClientRepository repository;
	
	Client client;
	
	/**
	 * Method that test the repository that save an User in the API.
	 * 
	 * @author Mariana Azevedo
	 * @since 24/03/2020
	 */
	@Test
	public void testSave() {
		
		Client client = new Client(2L, "Client", "123456789", "Rua", "34", "19901230", "Jardim", "São Paulo", "Ourinhos");
		Client response = repository.save(client);
		
		assertNotNull(response);
	}
	
	/**
	 * Method that test the repository that search for an User by the email.
	 * 
	 * @author Mariana Azevedo
	 * @since 24/03/2020
	 */
	@Test
	public void testFindById(){
		Optional<Client> response = repository.findById(1L);
		
		assertTrue(response.isPresent());
		assertEquals(1L, response.get().getId());
	}
	
	/**
	 * Method to remove all User test data.
	 * 
	 * @author Mariana Azevedo
	 * @since 24/03/2020
	 */
	@AfterAll
	private void tearDown() {
		repository.deleteAll();
	}
}
