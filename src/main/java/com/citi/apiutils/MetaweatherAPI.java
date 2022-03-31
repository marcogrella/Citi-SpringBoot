package com.citi.apiutils;

import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.citi.domain.City;
import com.citi.exceptions.CityNotFoundException;
import com.citi.exceptions.ConnectionFailureException;
import java.net.HttpURLConnection;
import java.util.Scanner;

@Service
public class MetaweatherAPI {
	
	private static Logger logger = LoggerFactory.getLogger(MetaweatherAPI.class);
	
	MetaweatherWoeidAPI weidQuery = new MetaweatherWoeidAPI();
	
	public City queryCity(String cityName) throws CityNotFoundException { 
		
		StringBuilder informationString = new StringBuilder();
		City city = new City();
		
		try {
							
			String baseUrl = "https://www.metaweather.com/api/location/search/?query=";
			URL url = new URL(baseUrl+cityName);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
        
			int responseCode = conn.getResponseCode();
			
			if (responseCode != 200) {
				throw new ConnectionFailureException("A error occurred... please try again. If the error persist contact the administrator", responseCode);
			} else {
        	
        	
			Scanner scanner = new Scanner(url.openStream());

            while (scanner.hasNext()) {
                informationString.append(scanner.nextLine());
            }
            
            scanner.close();
            conn.disconnect();
            
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
			if(informationString.length() <= 2) {
				throw new CityNotFoundException("City not found, please try again");
			} else {	
				try {
				

					JSONParser parse = new JSONParser();
					JSONArray dataObject = (JSONArray) parse.parse(String.valueOf(informationString));
					JSONObject cityObject = (JSONObject) dataObject.get(0);
					logger.info("running search information for city: " + cityObject.get("title"));
					logger.info("woeid: " + cityObject.get("woeid"));
					StringBuilder weoidQuery = weidQuery.queryWoeid(cityObject.get("woeid"));
					
					JSONObject weoidQbj = (JSONObject) parse.parse(String.valueOf(weoidQuery));
					JSONArray obj = (JSONArray) weoidQbj.get("consolidated_weather");
					JSONObject wObj =  (JSONObject) parse.parse(obj.get(0).toString());
					
					city.setCityName(cityObject.get("title").toString());
					city.setCelsiusTemp(wObj.get("the_temp").toString().substring(0, 5));
					city.setFarenheitsTemp(String.valueOf(celsiusToFahrenheit(wObj.get("the_temp").toString().substring(0, 4))));
					
								
				} catch (ParseException e) {
				//e.printStackTrace();
			}
		}
			return city;
				
	}
	
	private float celsiusToFahrenheit(String celsiusTemp) {
		float celsius = Float.parseFloat(celsiusTemp);
		float fahrenheit = (float) ((celsius * 1.8) + 32);
		return fahrenheit;
		
	}
	
}



	
        