package com.keeggo.clientsjavaapi.service.client;

import java.util.List;

import com.keeggo.clientsjavaapi.dto.model.client.ClientDTO;
import com.keeggo.clientsjavaapi.exception.ClientNotFoundException;
import com.keeggo.clientsjavaapi.model.client.Client;

public interface ClientService {
	Client save(Client client);
	
	void deleteById(Long clientId);
	
	Client findById(Long id) throws ClientNotFoundException;	
	
	List<Client> findAll();
	
	ClientDTO getPartialJsonResponse(String fields, ClientDTO dto);
}
