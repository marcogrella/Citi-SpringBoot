package com.citi.exceptions;

public class CityNotFoundException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public CityNotFoundException(String message){
        super(message);
    }
	
}
