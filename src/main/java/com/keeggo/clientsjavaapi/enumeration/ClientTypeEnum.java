package com.keeggo.clientsjavaapi.enumeration;

/**
 * Enum that classifies the client's type in the API.
 * 
 * @author Mariana Azevedo
 * @since 16/12/2020
 * 
 * @author Mariana Azevedo
 *
 */
public enum ClientTypeEnum {
	
	FREE("FREE"),
	BASIC("BASIC"), 
	PREMIUM("PREMIUM"),	
	ONE_WAY("ONE-WAY"),
	RETURN("RETURN"),
	MULTI_CITY("MULTI-CITY");
	
	private String value;
	
	private ClientTypeEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
