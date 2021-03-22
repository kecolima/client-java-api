package com.keeggo.clientsjavaapi.controller.v1.client;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.keeggo.clientsjavaapi.dto.model.client.ClientDTO;
import com.keeggo.clientsjavaapi.dto.response.Response;
import com.keeggo.clientsjavaapi.exception.ClientNotFoundException;
import com.keeggo.clientsjavaapi.exception.NotParsableContentException;
import com.keeggo.clientsjavaapi.exception.ClientInvalidUpdateException;
import com.keeggo.clientsjavaapi.exception.ClientNotFoundException;
import com.keeggo.clientsjavaapi.model.client.Client;
import com.keeggo.clientsjavaapi.service.client.ClientService;
import com.keeggo.clientsjavaapi.util.ClientsApiUtil;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api-clients/v1/clients")
public class ClientController {
	
	ClientService clientService;
	
	@Autowired
	public ClientController(ClientService clientService) {
		this.clientService = clientService;
	}
	
	/**
	 * Method that creates  in the database.
	 * 
	 * @author Keco Lima
	 * @since 02/04/2020
	 * 
	 * @param apiVersion - API version at the moment
	 * @param apiKey - API Key to access the routes
	 * @param dto, where: 
	 * - id - trip id; 
	 * - orderNumber - identification number of a trip in the system; 
	 * - amount – travel amount; a string of arbitrary length that is parsable as a BigDecimal; 
	 * - startDate – initial date time in the ISO 8601 format YYYY-MM-DDThh:mm:ss.sssZ in the Local time zone;
	 * - endDate – final date time in the ISO 8601 format YYYY-MM-DDThh:mm:ss.sssZ in the Local time zone; 
	 * - type - trip type: RETURN (with a date to begin and end), ONE_WAY (only with initial date), 
	 * MULTI_CITY (with multiple destinations);
	 * - account_id - account id of the user in the API.
	 * 
	 * @param result - Bind result
	 * 
	 * @return ResponseEntity with a <code>Response<ClientDTO></code> object and the HTTP status
	 * 
	 * HTTP Status:
	 * 
	 * 201 - Created: Everything worked as expected.
	 * 400 - Bad Request: The request was unacceptable, often due to missing a required parameter.
	 * 404 - Not Found: The requested resource doesn't exist.
	 * 409 - Conflict: The request conflicts with another request (perhaps due to using the same idempotent key).
	 * 422 – Unprocessable Entity: if any of the fields are not parsable or the start date is greater than end date.
	 * 429 - Too Many Requests: Too many requests hit the API too quickly. We recommend an exponential back-off of your requests.
	 * 500, 502, 503, 504 - Server Errors: something went wrong on API end (These are rare).
	 * 
	 * @throws NotParsableContentException
	 * @throws BadRequestException 
	 */
	@PostMapping
	@ApiOperation(value = "Route to create ")
	public ResponseEntity<Response<ClientDTO>> create(@RequestHeader(value=ClientsApiUtil.HEADER_CLIENTS_API_VERSION, defaultValue="${api.version}") String apiVersion, 
			@RequestHeader(value="Authorization", defaultValue="Authorization") String apiVersionn, @RequestHeader(value=ClientsApiUtil.HEADER_API_KEY, defaultValue="${api.key}") String apiKey, @Valid @RequestBody ClientDTO dto, BindingResult result) 
					throws NotParsableContentException {
		
		Response<ClientDTO> response = new Response<>();

		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.addErrorMsgToResponse(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		
		Client client = dto.convertDTOToEntity(); 
		Client clientToCreate = clientService.save(client);

		ClientDTO dtoSaved = clientToCreate.convertEntityToDTO();
		createSelfLink(clientToCreate, dtoSaved);

		response.setData(dtoSaved);
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add(ClientsApiUtil.HEADER_CLIENTS_API_VERSION, apiVersion);
		headers.add(ClientsApiUtil.HEADER_API_KEY, apiKey);
		
		return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
	}
	
	/**
	 * Method that updates  in the database.
	 * 
	 * @author Keco Lima
	 * @since 02/04/2020
	 * 
	 * @param apiVersion - API version at the moment
	 * @param apiKey - API Key to access the routes
	 * @param dto, where: 
	 * - id - trip id; 
	 * - orderNumber - identification number of a trip in the system; 
	 * - amount – travel amount; a string of arbitrary length that is parsable as a BigDecimal; 
	 * - initialDate – initial date time in the ISO 8601 format YYYY-MM-DDThh:mm:ss.sssZ in the Local time zone;
	 * - finalDate – final date time in the ISO 8601 format YYYY-MM-DDThh:mm:ss.sssZ in the Local time zone; 
	 * - type - trip type: RETURN (with a date to begin and end), ONE_WAY (only with initial date), 
	 * MULTI_CITY (with multiple destinations);
	 * - account_id - account id of the user in the API.
	 * 
	 * @param result - Bind result
	 * 
	 * @return ResponseEntity with a <code>Response<ClientDTO></code> object and the HTTP status
	 * 
	 * HTTP Status:
	 * 
	 * 200 - OK: Everything worked as expected.
	 * 400 - Bad Request: The request was unacceptable, often due to missing a required parameter.
	 * 404 - Not Found: The requested resource doesn't exist.
	 * 409 - Conflict: The request conflicts with another request (perhaps due to using the same idempotent key).
	 * 422 – Unprocessable Entity: if any of the fields are not parsable or the start date is greater than end date.
	 * 429 - Too Many Requests: Too many requests hit the API too quickly. We recommend an exponential back-off of your requests.
	 * 500, 502, 503, 504 - Server Errors: something went wrong on API end (These are rare).
	 * 
	 * @throws ClientNotFoundException
	 * @throws ClientInvalidUpdateException
	 * @throws NotParsableContentException 
	 */
	@PutMapping(path = "/{id}")
	@ApiOperation(value = "Route to update a client")
	public ResponseEntity<Response<ClientDTO>> update(@RequestHeader(value=ClientsApiUtil.HEADER_CLIENTS_API_VERSION, defaultValue="${api.version}") String apiVersion, 
			@RequestHeader(value="Authorization", defaultValue="Authorization") String apiVersionn, @RequestHeader(value=ClientsApiUtil.HEADER_API_KEY, defaultValue="${api.key}") String apiKey, @Valid @RequestBody ClientDTO dto, BindingResult result) 
		throws ClientNotFoundException, ClientInvalidUpdateException, NotParsableContentException {
		
		Response<ClientDTO> response = new Response<>();

		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.addErrorMsgToResponse(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		Client client = dto.convertDTOToEntity();
		Client clientToUpdate = clientService.save(client);
		
		ClientDTO itemDTO = clientToUpdate.convertEntityToDTO();
		createSelfLink(clientToUpdate, itemDTO);
		response.setData(itemDTO);
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add(ClientsApiUtil.HEADER_CLIENTS_API_VERSION, apiVersion);
		headers.add(ClientsApiUtil.HEADER_API_KEY, apiKey);
		
		return new ResponseEntity<>(response, headers, HttpStatus.OK);
	}
	
	/**
	 * Method that search a travel by the id.
	 * 
	 * @param apiVersion - API version at the moment
	 * @param apiKey - API Key to access the routes
	 * @param travelId - the id of the travel
	 * @param fields - Client fields that should be returned in JSON as Partial Response
	 * 
	 * @return ResponseEntity with a <code>Response<ClientDTO></code> object and the HTTP status
	 * 
	 * HTTP Status:
	 * 
	 * 200 - OK: Everything worked as expected.
	 * 400 - Bad Request: The request was unacceptable, often due to missing a required parameter.
	 * 404 - Not Found: The requested resource doesn't exist.
	 * 409 - Conflict: The request conflicts with another request (perhaps due to using the same idempotent key).
	 * 429 - Too Many Requests: Too many requests hit the API too quickly. We recommend an exponential back-off of your requests.
	 * 500, 502, 503, 504 - Server Errors: something went wrong on API end (These are rare).
	 * 
	 * @throws ClientNotFoundException
	 */
	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Route to find a client by your id in the API")
	public ResponseEntity<Response<ClientDTO>> findById(@RequestHeader(value=ClientsApiUtil.HEADER_CLIENTS_API_VERSION, defaultValue="${api.version}") 
		String apiVersion, @RequestHeader(value="Authorization", defaultValue="Authorization") String apiVersionn, @RequestHeader(value=ClientsApiUtil.HEADER_API_KEY, defaultValue="${api.key}") String apiKey, @PathVariable("id") Long clientId,
		@RequestParam(required = false) String fields) throws ClientNotFoundException {
		
		Response<ClientDTO> response = new Response<>();
		Client client = clientService.findById(clientId);
		
		ClientDTO dto = client.convertEntityToDTO();
		
		if(fields != null) {
			dto = clientService.getPartialJsonResponse(fields, dto);
		}
		
		createSelfLink(client, dto);
		response.setData(dto);
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add(ClientsApiUtil.HEADER_CLIENTS_API_VERSION, apiVersion);
		headers.add(ClientsApiUtil.HEADER_API_KEY, apiKey);
		
		return new ResponseEntity<>(response, headers, HttpStatus.OK);
	}	
	
	/**
	 * Method that search a travel by the id.
	 * 
	 * @param apiVersion - API version at the moment
	 * @param apiKey - API Key to access the routes
	 * @param travelId - the id of the travel
	 * @param fields - Client fields that should be returned in JSON as Partial Response
	 * 
	 * @return ResponseEntity with a <code>Response<ClientDTO></code> object and the HTTP status
	 * 
	 * HTTP Status:
	 * 
	 * 200 - OK: Everything worked as expected.
	 * 400 - Bad Request: The request was unacceptable, often due to missing a required parameter.
	 * 404 - Not Found: The requested resource doesn't exist.
	 * 409 - Conflict: The request conflicts with another request (perhaps due to using the same idempotent key).
	 * 429 - Too Many Requests: Too many requests hit the API too quickly. We recommend an exponential back-off of your requests.
	 * 500, 502, 503, 504 - Server Errors: something went wrong on API end (These are rare).
	 * 
	 * @throws ClientNotFoundException
	 */
	
	@GetMapping
	@ApiOperation(value = "Route to find clients in the API")
	public ResponseEntity<Response<List<ClientDTO>>> findAll(@RequestHeader(value=ClientsApiUtil.HEADER_CLIENTS_API_VERSION, defaultValue="${api.version}") 
		String apiVersion, @RequestHeader(value="Authorization", defaultValue="Authorization") String apiVersionn, @RequestHeader(value=ClientsApiUtil.HEADER_API_KEY, defaultValue="${api.key}") String apiKey,
		@RequestParam(required = false) String fields) throws ClientNotFoundException {
		
		Response<List<ClientDTO>> response = new Response<>();		
		List<Client> clients = clientService.findAll();
		
		if (clients.isEmpty()) {
			throw new ClientNotFoundException("There are no clients registered");
		}	
		
		List<ClientDTO> clientsDTO = new ArrayList<>();
		clients.stream().forEach(t -> clientsDTO.add(t.convertEntityToDTO()));
		
		clientsDTO.stream().forEach(dto -> {		
			try {
				createSelfLinkInCollections(apiVersion, apiKey, dto);
			} catch (ClientNotFoundException e) {
				e.printStackTrace();
			}			
		});
				
		response.setData(clientsDTO);
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add(ClientsApiUtil.HEADER_CLIENTS_API_VERSION, apiVersion);
		headers.add(ClientsApiUtil.HEADER_API_KEY, apiKey);
		
		return new ResponseEntity<>(response, headers, HttpStatus.OK);
	}	

	/**
	 * Method that delete a unique trip.
	 * 
	 * @author Keco Lima
	 * @since 02/04/2020
	 * 
	 * @param apiVersion - API version at the moment
	 * @param apiKey - API Key to access the routes
	 * @param travelId - the id of the travel
	 * 
	 * @return ResponseEntity with a <code>Response<String></code> object and the HTTP status
	 * 
	 * HTTP Status:
	 * 
	 * 204 - OK: Everything worked as expected.
	 * 400 - Bad Request: The request was unacceptable, often due to missing a required parameter.
	 * 404 - Not Found: The requested resource doesn't exist.
	 * 409 - Conflict: The request conflicts with another request (perhaps due to using the same idempotent key).
	 * 429 - Too Many Requests: Too many requests hit the API too quickly. We recommend an exponential back-off of your requests.
	 * 500, 502, 503, 504 - Server Errors: something went wrong on API end (These are rare).
	 * 
	 * @throws ClientNotFoundException 
	 */
	@DeleteMapping(value = "/{id}")
	@ApiOperation(value = "Route to delete a client in the API")
	public ResponseEntity<Response<String>> delete(@RequestHeader(value=ClientsApiUtil.HEADER_CLIENTS_API_VERSION, defaultValue="${api.version}") 
		String apiVersion, @RequestHeader(value="Authorization", defaultValue="Authorization") String apiVersionn, @RequestHeader(value=ClientsApiUtil.HEADER_API_KEY, defaultValue="${api.key}") String apiKey, 
		@PathVariable("id") Long clientId) throws ClientNotFoundException {
		
		Response<String> response = new Response<>();
		Client client = clientService.findById(clientId);
		
		clientService.deleteById(client.getId());
		response.setData("Client id=" + client.getId() + " successfully deleted");
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add(ClientsApiUtil.HEADER_CLIENTS_API_VERSION, apiVersion);
		headers.add(ClientsApiUtil.HEADER_API_KEY, apiKey);
		
		return new ResponseEntity<>(response, headers, HttpStatus.NO_CONTENT);
	}
	
	/**
	 * Method that creates a self link to travel object
	 * 
	 * @author Keco Lima
	 * @since 22/03/2021
	 * 
	 * @param travel
	 * @param travelDTO
	 */
	private void createSelfLink(Client client, ClientDTO clientDTO) {
		Link selfLink = WebMvcLinkBuilder.linkTo(ClientController.class).slash(client.getId()).withSelfRel();
		clientDTO.add(selfLink);
	}
	
	private void createSelfLinkInCollections(String apiVersion, String apiKey, final ClientDTO clientDTO) 
			throws ClientNotFoundException {
		Link selfLink = linkTo(methodOn(ClientController.class).findById(apiVersion, apiKey, null, clientDTO.getId(), null))
				.withSelfRel().expand();
		clientDTO.add(selfLink);		
	}
	
}
