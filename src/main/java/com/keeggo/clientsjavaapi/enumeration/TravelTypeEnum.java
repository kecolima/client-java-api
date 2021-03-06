package com.keeggo.clientsjavaapi.enumeration;

/**
 * Enum that classifies the client's type.
 * 
 * @author Mariana Azevedo
 * @since 14/09/2019
 */
public enum TravelTypeEnum {
	
	ONE_WAY("ONE-WAY"), RETURN("RETURN"), MULTI_CITY("MULTI-CITY");
	
	private String value;
	
	private TravelTypeEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
