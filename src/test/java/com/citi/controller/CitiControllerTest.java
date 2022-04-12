package com.citi.controller;

import com.citi.apiutils.MetaweatherAPI;
import com.citi.domain.City;
import com.citi.exceptions.CityNameException;
import com.citi.exceptions.CityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class CitiControllerTest {

    @InjectMocks
    CitiController citiControllerTest;

    @Autowired
    MetaweatherAPI queryAPITest;


    @DisplayName("Throws CityNameException when an api consumer sends an empty name of a city")
    @Test
    void shouldThrows_CityNameException_whenCityNameIsEmptyOrBlank (){

        Assertions.assertThrows(CityNameException.class, () -> citiControllerTest.getCityInfo(""));
        CityNameException exception = assertThrows(CityNameException.class,
                () -> citiControllerTest.getCityInfo(""));
        assertTrue(exception.getMessage() == "The name of the city cannot be null or empty");

    }


    @DisplayName("Throws CityNotFoundException when an city is not found")
    @Test
    void shouldThrows_CityNotFoundException_whenCityIsNotFound (){

        Assertions.assertThrows(CityNotFoundException.class, () -> citiControllerTest.getCityInfo("cityThatDoesNotExists"));
        CityNotFoundException exception = assertThrows(CityNotFoundException.class,
                () -> citiControllerTest.getCityInfo("cityThatDoesNotExists"));
        assertTrue(exception.getMessage() == "City not found, please try again");

    }


    @DisplayName("Returns a valid City Object when a valid name is send to query")
    @Test
    void returnsAValidCity_whenSucessfull() throws CityNotFoundException {

        City city = queryAPITest.queryCity("paris");
        Assertions.assertEquals("Paris", city.getCityName());
        Assertions.assertTrue(city.getCelsiusTemp() != null);
        Assertions.assertTrue(city.getFarenheitsTemp() != null);
    }


    @DisplayName("Returns a valid response entity when valid city is send to query")
    @Test
    void returnsAValidResponseEntity_whenSucessfull() throws CityNameException, CityNotFoundException {

        ResponseEntity<City> entity = citiControllerTest.getCityInfo("rio");
        Assertions.assertTrue(entity.hasBody());
        Assertions.assertTrue(entity.getStatusCode() == HttpStatus.OK);
        Assertions.assertTrue(entity.hasBody());
        Assertions.assertTrue(entity.getBody().getCityName() != null);
    }

}