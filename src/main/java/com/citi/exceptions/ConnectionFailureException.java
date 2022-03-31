package com.citi.exceptions;

public class ConnectionFailureException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public ConnectionFailureException(String message, int statusCode){
        super(message);
    } 
}
