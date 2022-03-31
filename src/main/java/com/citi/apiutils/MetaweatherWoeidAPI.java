package com.citi.apiutils;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import com.citi.exceptions.ConnectionFailureException;


@Service
public class MetaweatherWoeidAPI {
	
	public StringBuilder queryWoeid(Object woeid) throws ParseException  { 
		StringBuilder woeidInformation = new StringBuilder();
		try {
			
			String baseURL = "https://www.metaweather.com/api/location/";
			URL url = new URL(baseURL+woeid);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			
			int responseCode = conn.getResponseCode();
			if (responseCode != 200) {
				throw new ConnectionFailureException("A error occurred... please try again. If the error persist contact the administrator", responseCode);
			} else {
        	
			Scanner scanner = new Scanner(url.openStream());
            while (scanner.hasNext()) {
            	woeidInformation.append(scanner.nextLine());
            	
            }
            scanner.close();
			}	
						
			conn.disconnect();	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return woeidInformation;
	
	}
}

