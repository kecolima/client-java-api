package com.keeggo.clientsjavaapi.exception;

/**
 * Class that implements ClientNotFoundException in the API
 * 
 * @author Mariana Azevedo
 * @since 31/10/2020
 */
public class ClientNotFoundException extends Exception {

	private static final long serialVersionUID = -7139304880555402679L;
	
	public ClientNotFoundException(){
		super();
	}
	
	public ClientNotFoundException(String msg){
		super(msg);
	}
	
	public ClientNotFoundException(String msg, Throwable cause){
		super(msg, cause);
	}

}
