package US_002;

import Utility.BaseParameter;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class CountryTest extends BaseParameter {

    String countryID;

    @Test
    public void Add() {
        Map<String, String> newCountry = new HashMap<>();
        newCountry.put("name", randomGenerator.address().country() + randomGenerator.address().countryCode());
        newCountry.put("code", randomGenerator.address().countryCode());

        countryID=
        given()
                .spec(reqSpec)  // gelen cookies, yeni istek için login olduğunun kanıtı olarak gönderildi
                .body(newCountry) // spec değeri body den önce belirtilmeli ***
                .when()
                .post("/school-service/api/countries")
                .then()
                .log().body()
                .statusCode(201)
                .extract().path("id")
        ;
        System.out.println(countryID);

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
