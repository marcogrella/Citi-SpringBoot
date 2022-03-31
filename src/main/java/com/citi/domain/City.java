package com.citi.domain;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;


public class City implements Serializable {

    private static final long serialVersionUID = -6708582589196853294L;
    
    @ApiModelProperty(value = "City name")
    private String cityName;
    
    @ApiModelProperty(value = "Temperature of city in Celsius degrees")
    private String celsiusTemp;
    
    @ApiModelProperty(value = "Temperature of city in Fahrenheit degrees")
    private String FarenheitsTemp;

	public City(String cityName, String celsiusTemp, String farenheitsTemp) {
		super();
		this.cityName = cityName;
		this.celsiusTemp = celsiusTemp;
		FarenheitsTemp = farenheitsTemp;
	}
	
	public City() {
	}
	
	public String getCityName() {
		return cityName;
	}
	
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	public String getCelsiusTemp() {
		return celsiusTemp;
	}

	public void setCelsiusTemp(String celsiusTemp) {
		this.celsiusTemp = celsiusTemp;
	}

	public String getFarenheitsTemp() {
		return FarenheitsTemp;
	}

	public void setFarenheitsTemp(String farenheitsTemp) {
		FarenheitsTemp = farenheitsTemp;
	}
      
}