package com.keeggo.clientsjavaapi.model.client;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.modelmapper.ModelMapper;

import com.keeggo.clientsjavaapi.dto.model.client.ClientDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "clients")
public class Client implements Serializable {
	
	private static final long serialVersionUID = 7774962185654520597L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String cpf;
	
	@Column(nullable = false)
	private String address;		
	
	@Column(nullable = false)
	private String numberHouse;
	
	@Column(nullable = false)
	private String zipCode;
	
	@Column(nullable = false)
	private String district;
	
	@Column(nullable = false)
	private String state;
	
	@Column(nullable = false)
	private String city;	

	public Client(Long id) {
		this.id = id;	
	}
		
	public ClientDTO convertEntityToDTO() {
		return new ModelMapper().map(this, ClientDTO.class);
	}
}
