package US_101;

import Utility.BaseParameter;
import org.testng.annotations.Test;

import java.util.*;

import static io.restassured.RestAssured.given;

public class US_101 extends BaseParameter {

    String statesID;

    @Test
    public void List() {
        given()
                .spec(reqSpec)
                .when()
                .get("/school-service/api/states")
                .then()
                .log().body()
                .statusCode(200);
    }

    @Test
    public void Add() {
        Map<String, Object> newStates = new HashMap<>();
        newStates.put("name", randomGenerator.address().state());
        Map<String, String> country = new HashMap<>();
        country.put("id", countryId);
        newStates.put("country", country);
        statesID =
                given()
                        .spec(reqSpec)
                        .body(newStates)
                        .when()
                        .post("/school-service/api/states")
                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().path("id");
    }

    @Test(dependsOnMethods = "Add")
    public void Edit() {
        Map<String, Object> newStates = new HashMap<>();
        newStates.put("id", statesID);
        newStates.put("name", randomGenerator.address().state());
        Map<String, String> country = new HashMap<>();
        country.put("id", countryId);
        newStates.put("country", country);

        given()
                .spec(reqSpec)
                .body(newStates)
                .when()
                .put("/school-service/api/states")
                .then()
                .log().body()
                .statusCode(200);
    }

    @Test(dependsOnMethods = "Edit")
    public void Delete() {

        given()
                .spec(reqSpec)
                .when()
                .delete("/school-service/api/states/" + statesID)
                .then()
                .statusCode(200);
    }
}