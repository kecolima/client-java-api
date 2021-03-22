package com.keeggo.clientsjavaapi.service.client.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ContainerNode;
import com.keeggo.clientsjavaapi.dto.model.client.ClientDTO;
import com.keeggo.clientsjavaapi.exception.ClientNotFoundException;
import com.keeggo.clientsjavaapi.model.client.Client;
import com.keeggo.clientsjavaapi.repository.client.ClientRepository;
import com.keeggo.clientsjavaapi.service.client.ClientService;
import com.keeggo.clientsjavaapi.service.user.UserService;
import com.zero_x_baadf00d.partialize.Partialize;

@Service
public class ClientServiceImpl implements ClientService{	
	
	ClientRepository clientRepository;
	
	@Autowired
	public ClientServiceImpl(ClientRepository clientRepository) {
		this.clientRepository = clientRepository;
	}

	/**
	 * @see UserService#save(Client)
	 */
	@Override
	public Client save(Client client) {
		return clientRepository.save(client);
	}

	@Override
	public void deleteById(Long clientId) {
		clientRepository.deleteById(clientId);
	}

	@Override
	@Cacheable(value="clientIdCache", key="#id")
	public Client findById(Long id) throws ClientNotFoundException {
		return clientRepository.findById(id).orElseThrow(() -> 
			new ClientNotFoundException("Client id=" + id + " not found"));
	}

	@Override
	public List<Client> findAll() {
		return clientRepository.findAll();
	}

	@Override
	public ClientDTO getPartialJsonResponse(String fields, ClientDTO dto) {
		
		final Partialize partialize = new Partialize();
		final ContainerNode<?> node = partialize.buildPartialObject(fields, ClientDTO.class, dto);
		return new ObjectMapper().convertValue(node, ClientDTO.class);
	}
}
