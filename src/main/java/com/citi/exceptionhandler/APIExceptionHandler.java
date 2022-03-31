package com.citi.exceptionhandler;

import com.citi.exceptions.CityNameException;
import com.citi.exceptions.CityNotFoundException;
import com.citi.exceptions.ConnectionFailureException;
import com.citi.utils.HttpResponse;

import java.io.Serializable;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class APIExceptionHandler implements Serializable{
	
	
	 private static final long serialVersionUID = 1L;


	@SuppressWarnings("rawtypes")
	@ExceptionHandler(CityNameException.class)
	 public ResponseEntity handleCityNameException (CityNameException e) {
	        return createHttpResponse(HttpStatus.BAD_REQUEST, e.getMessage());
	    }
	
	@SuppressWarnings("rawtypes")
	@ExceptionHandler(ConnectionFailureException.class)
	 public ResponseEntity handleConnectionFailureException (ConnectionFailureException e, int statusCode) {
			HttpStatus httpStatus =  HttpStatus.valueOf(statusCode);
	        return createHttpResponse(httpStatus, e.getMessage());
	    }
	
	
	@SuppressWarnings("rawtypes")
	@ExceptionHandler(CityNotFoundException.class)
	 public ResponseEntity handleCityNotFoundException (CityNotFoundException e) {	
	        return createHttpResponse(HttpStatus.NOT_FOUND, e.getMessage());
	    }
	
	
	 @ExceptionHandler(Exception.class)
	    public ResponseEntity<HttpResponse> internalServerErrorException(Exception exception) {
		 	return createHttpResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An Internal error ocoured");
	    }
	
	 
	 private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus httpStatus, String message){
	        @SuppressWarnings("serial")
			HttpResponse httpResponseBody = new HttpResponse(new Date(), httpStatus.value(), httpStatus,
	                httpStatus.getReasonPhrase(), message) {
	        };
	        return new ResponseEntity<>(httpResponseBody, httpStatus);
	    }
	
	
}
