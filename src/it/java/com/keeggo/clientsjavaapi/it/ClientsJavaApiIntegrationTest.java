package com.keeggo.clientsjavaapi.it;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.keeggo.clientsjavaapi.dto.model.account.AccountDTO;
import com.keeggo.clientsjavaapi.dto.model.client.ClientDTO;
import com.keeggo.clientsjavaapi.dto.model.security.JwtUserDTO;
import com.keeggo.clientsjavaapi.dto.model.user.UserDTO;
import com.keeggo.clientsjavaapi.enumeration.APIUsagePlansEnum;
import com.keeggo.clientsjavaapi.enumeration.AccountTypeEnum;
import com.keeggo.clientsjavaapi.enumeration.ClientTypeEnum;
import com.keeggo.clientsjavaapi.enumeration.RoleEnum;
import com.keeggo.clientsjavaapi.util.ClientsApiUtil;

/**
 * Class that implements API integration tests.
 * 
 * @author Mariana Azevedo
 * @since 10/09/2019
 */
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ClientsJavaApiIntegrationTest {
	
	@LocalServerPort
	private int port;
	
	private String token;
	 
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @Order(1)
    public void testCreateUser() {
    	
    	UserDTO userDto = new UserDTO(99L, "Admin", "123456", "admin@financial.com", 
    			RoleEnum.ROLE_ADMIN.getValue());
    	
    	final HttpHeaders headers = new HttpHeaders();
        headers.set("X-api-key", "FX001-ZBSY6YSLP");
        
        //Create a new HttpEntity
        final HttpEntity<UserDTO> entity = new HttpEntity<>(userDto, headers);
        
        ResponseEntity<String> responseEntity = this.restTemplate.exchange("http://localhost:" 
        		+ port + "/api-clients/v1/users", HttpMethod.POST, entity, String.class);
        
        assertEquals(201, responseEntity.getStatusCodeValue());
    }
    
    @Test
    @Order(2)
    public void testAuthentication() throws JsonMappingException, JsonProcessingException {
    	
    	JwtUserDTO userDto = new JwtUserDTO("admin@financial.com", "123456");
    	
    	final HttpHeaders headers = new HttpHeaders();
        headers.set("X-api-key", "FX001-ZBSY6YSLP");
        
        //Create a new HttpEntity
        final HttpEntity<JwtUserDTO> entity = new HttpEntity<>(userDto, headers);
        
        ResponseEntity<String> responseEntity = this.restTemplate.exchange("http://localhost:" 
        		+ port + "/api-clients/v1/auth", HttpMethod.POST, entity, String.class);
        
        String body = responseEntity.getBody();
        JsonNode json = new ObjectMapper().readTree(body);
        token = json.get("data").get("token").textValue();
        
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertNotNull(token);
    }   
    
    @Test
    @Order(2)
    public void testCreateAccount() throws ParseException {
    	
    	//id=1
        AccountDTO accountDto = new AccountDTO(1L, "000012", AccountTypeEnum.BASIC.name()); 
        
        final HttpHeaders headers = new HttpHeaders();
        headers.set("X-api-key", "FX001-ZBSY6YSLP");
        
        //Create a new HttpEntity
        final HttpEntity<AccountDTO> entity = new HttpEntity<>(accountDto, headers);
        
		ResponseEntity<AccountDTO> responseEntity = this.restTemplate.exchange("http://localhost:" 
        		+ port + "/api-clients/v1/accounts", HttpMethod.POST, entity, new ParameterizedTypeReference<AccountDTO>(){});
        
        assertEquals(201, responseEntity.getStatusCodeValue());
    } 
    
    @Test
    @Order(3)
    public void testFindClientByIdThatNotExists() {
    	
    	final HttpHeaders headers = new HttpHeaders();
        headers.set("X-api-key", "FX001-ZBSY6YSLP");
        
        //Create a new HttpEntity
        final HttpEntity<String> entity = new HttpEntity<>(headers);
        
        ResponseEntity<String> responseEntity = this.restTemplate
        		.exchange("http://localhost:" + port + "/api-clients/v1/clients/3", 
        				HttpMethod.GET, entity, String.class);
    	
    	//Transaction not found
        assertEquals(404, responseEntity.getStatusCodeValue()); 
    }    
   /*
    @Test
    @Order(4)
    public void testCreateClient() {  
    	
    	ClientDTO clientDto = new ClientDTO(1l, "Client", "123456789", "Rua", "Jardim", "34", "São paulo", "Ourinhos", "19901230");
    	
    	final HttpHeaders headers = new HttpHeaders();
        headers.set("X-api-key", "FX001-ZBSY6YSLP");
        
        //Create a new HttpEntity
        final HttpEntity<ClientDTO> entity = new HttpEntity<>(clientDto, headers);
        
        ResponseEntity<String> responseEntity = this.restTemplate.exchange("http://localhost:" 
        		+ port + "/api-clients/v1/clients", HttpMethod.POST, entity, String.class);
        
           assertEquals(201, responseEntity.getStatusCodeValue());
    }
    
    @Test
    @Order(5)
    public void testFindById() {
    	
    	final HttpHeaders headers = new HttpHeaders();
        headers.set("X-api-key", "FX001-ZBSY6YSLP");
        
        //Create a new HttpEntity
        final HttpEntity<String> entity = new HttpEntity<>(headers);
        
        ResponseEntity<String> responseEntity = this.restTemplate
        		.exchange("http://localhost:" + port + "/api-clients/v1/clients/1", 
        				HttpMethod.GET, entity, String.class);
    	
        assertEquals(200, responseEntity.getStatusCodeValue());
    }
    /*
    @Test
    @Order(5)
	public void testRequestExceedingRateLimitCapacity() throws Exception {
	    
	    final HttpHeaders headers = new HttpHeaders();
        headers.set("X-api-key", "FX001-ZBSY6YSLP");

        //Create a new HttpEntity
        final HttpEntity<String> entity = new HttpEntity<>(headers);

        for (int i = 1; i <= APIUsagePlansEnum.FREE.getBucketCapacity(); i++) {
        	this.restTemplate.exchange("http://localhost:" + port + "/api-clients/v1/clients/1", 
        			HttpMethod.GET, entity, String.class);
        }
        
        ResponseEntity<String> responseEntity = this.restTemplate.exchange("http://localhost:" + port 
        		+ "/api-clients/v1/clients/1", HttpMethod.GET, entity, String.class);
        
    	//Too many requests
        assertEquals(429, responseEntity.getStatusCodeValue());
	}
    */

}
