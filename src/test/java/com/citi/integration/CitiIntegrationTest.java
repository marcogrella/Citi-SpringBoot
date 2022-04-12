package com.citi.integration;


import com.citi.controller.CitiController;
import com.citi.domain.City;
import io.restassured.RestAssured;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.http.HttpStatus;

import static org.hamcrest.Matchers.equalTo;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CitiIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private CitiController controllerTest;

    @BeforeEach
    public void setUp(){
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/api/v1/query";

    }


    @Test
    @DisplayName("get status 200 and returns city when successful")
    public void get_City_when_Sucessful(){

        City city =  RestAssured.given()
                .get("?cityName=tokyo")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract().as(City.class);

        Assertions.assertThat(city).isNotNull();
        Assertions.assertThat(city.getCityName()).isEqualTo("Tokyo");
        Assertions.assertThat(city.getCelsiusTemp()).isNotEmpty();
        Assertions.assertThat(city.getFarenheitsTemp()).isNotEmpty();

    }


    @Test
    @DisplayName("get status 401 when city not found")
    public void get_returnsStatus401_whenCityIsNotFound(){

        RestAssured.given()
                .get("?cityName=cityThatDoesNotExists")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("message", equalTo("City not found, please try again"));

    }

    @Test
    @DisplayName("get status 400 when api consumer sends in uri an empty name or empty string ")
    public void get_returnsStatus400_whenCityNameIsNotFound(){

        RestAssured.given()
                .get("?cityName=")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("message", equalTo("The name of the city cannot be null or empty"));

    }




}
