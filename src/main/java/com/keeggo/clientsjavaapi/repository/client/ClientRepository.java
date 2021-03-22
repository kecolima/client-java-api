package com.keeggo.clientsjavaapi.repository.client;

import org.springframework.data.jpa.repository.JpaRepository;

import com.keeggo.clientsjavaapi.model.client.Client;


public interface ClientRepository extends JpaRepository<Client, Long>{
	
}
