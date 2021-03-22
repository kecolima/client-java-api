package com.keeggo.clientsjavaapi.exception;

/**
 * Class that implements TransactionInvalidUpdateException in the API.
 * 
 * @author Mariana Azevedo
 * @since 01/04/2020
 */
public class ClientInvalidUpdateException extends Exception{

	private static final long serialVersionUID = -6443362632495638948L;
	
	public ClientInvalidUpdateException(){
		super();
	}
	
	public ClientInvalidUpdateException(String msg){
		super(msg);
	}
	
	public ClientInvalidUpdateException(String msg, Throwable cause){
		super(msg, cause);
	}

}
