package com.citi.controller;




import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.citi.apiutils.MetaweatherAPI;
import com.citi.domain.City;
import com.citi.exceptions.CityNameException;
import com.citi.exceptions.CityNotFoundException;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;



@RestController
@RequestMapping(value = "/api/v1")
public class CitiController {
	
	private final MetaweatherAPI queryAPI = new MetaweatherAPI();
	
	
	@ApiOperation(value = "Returns a city and respective temperature in Celsius and Fareinht")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "When successfull. Returns a city with respective temperature in Celsius and Fahrenheit degrees."),
            @ApiResponse(code = 500, message = "When an internal error occours in server."),
            @ApiResponse(code = 404, message = "When api consumer tries to query a city wich does not exists."),
            @ApiResponse(code = 400, message = "When api consumer tries sends a parameter with no value, query not will be executed."),
    })
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("/query")
    public ResponseEntity<City> getCityInfo(@RequestParam String cityName) throws CityNameException, CityNotFoundException{
        if(cityName.isEmpty() || cityName == null) {
        	throw new CityNameException("The name of the city cannot be null or empty");
        } 
		return new ResponseEntity(queryAPI.queryCity(cityName), HttpStatus.OK);
	}

}
