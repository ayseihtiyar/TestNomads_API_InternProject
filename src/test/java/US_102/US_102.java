package US_102;

import Utility.BaseParameter;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import java.util.*;

import static io.restassured.RestAssured.given;

public class US_102 extends BaseParameter {
    String citiesID;

    @Test
    public void List() {
        Map<String, String> Name = new HashMap<>();
        Name.put("name", "");

        given()
                .spec(reqSpec)
                .body(Name)
                .when()
                .post("/school-service/api/cities/search")
                .then()
                .log().body()
                .statusCode(200)
        ;
    }
    @Test
    public void Add() {
        Map<String, Object> newCities = new HashMap<>();
        newCities.put("name", randomGenerator.address().cityName());
        Map<String, String> country = new HashMap<>();
        country.put("id", countryId);
        newCities.put("country", country);
        Map<String, String> states = new HashMap<>();
        states.put("id", "666aecf1fd4acd175ea7ea85");
        newCities.put("state", states);

        citiesID =
                given()
                        .spec(reqSpec)
                        .body(newCities)
                        .contentType(ContentType.JSON)
                        .when()
                        .post("/school-service/api/cities")
                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().path("id")
        ;
    }

    @Test(dependsOnMethods = "Add")
    public void Edit() {
        Map<String, Object> newCities = new HashMap<>();
        newCities.put("id", citiesID);
        newCities.put("name", randomGenerator.address().cityName());
        Map<String, String> country = new HashMap<>();
        country.put("id", countryId);
        newCities.put("country", country);
        Map<String, String> states = new HashMap<>();
        states.put("id", "666aecf1fd4acd175ea7ea85");
        newCities.put("state", states);

        given()
                .spec(reqSpec)
                .body(newCities)
                .when()
                .put("/school-service/api/cities")
                .then()
                .log().body()
                .statusCode(200)
        ;
    }

    @Test(dependsOnMethods = "Edit")
    public void Delete() {

        given()
                .spec(reqSpec)
                .when()
                .delete("/school-service/api/cities/"+citiesID)
                .then()
                .statusCode(200)
        ;
    }
}