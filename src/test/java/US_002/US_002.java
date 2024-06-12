package US_002;

import Utility.BaseParameter;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class US_002 extends BaseParameter {
    String countryid;

    @Test
    public void Add() {
        Map<String, String> newCountry = new HashMap<>();
        newCountry.put("name", randomGenerator.address().country() + randomGenerator.address().countryCode());
        newCountry.put("code", randomGenerator.address().countryCode());

        countryid=
                given()
                        .spec(reqSpec)
                        .body(newCountry)
                        .when()
                        .post("/school-service/api/countries")
                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().path("id")
        ;
        System.out.println(countryid);

    }

    @Test
    public void List() {

        given()
                .spec(reqSpec)
                .when()
                .get("/school-service/api/countries")
                .then()
                .log().body()
                .statusCode(200)
        ;

    }
}






