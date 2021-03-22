package com.keeggo.clientsjavaapi.dto.model.client;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

import com.keeggo.clientsjavaapi.model.client.Client;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ClientDTO extends RepresentationModel<ClientDTO> {

	@Getter
	private Long id;
	
	@Getter
	@NotNull(message = "Name cannot be null.")
	@Length(min=3, max=255, message="Name must contain between 3 and 255 characters.")
	private String name;
	
	@Getter
	@NotNull(message = "CPF cannot be null.")
	@Length(min=9, message="CPF must contain at least 9 characters.")
	private String cpf;
	
	@Getter
	@Length(max=100, message="Address must be a maximum of 100 characters.")
	@NotNull(message = "Address cannot be null.")
	private String address;	
	
	@Getter
	@Length(max=100000, message="Number must be a maximum of 100000 characters.")
	@NotNull(message = "numberHouse cannot be null.")
	private String numberHouse;
	
	@Getter
	@Length(max=100, message="ZipCode must be a maximum of 7 characters.")
	@NotNull(message = "ZipCode cannot be null.")
	private String zipCode;	
	
	@Getter
	@Length(max=100, message="District must be a maximum of 100 characters.")
	@NotNull(message = "District cannot be null.")
	private String district;	
	
	@Getter
	@Length(max=100, message="State must be a maximum of 100 characters.")
	@NotNull(message = "State cannot be null.")
	private String state;	
	
	@Getter
	@Length(max=100, message="City must be a maximum of 100 characters.")
	@NotNull(message = "City cannot be null.")
	private String city;			
	
	public Client convertDTOToEntity() {
		return new ModelMapper().map(this, Client.class);
	}
}
