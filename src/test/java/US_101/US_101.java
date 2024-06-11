package US_101;

import Utility.BaseParameter;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

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
        newStates.put("name", randomGenerator.educator().course());
        newStates .put("shortName", null);

        Map<String, String> country = new HashMap<>();
        country.put("id",countryId);
        newStates .put("country", country);

        newStates .put("translateName", new String[] {});


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
        System.out.println(statesID);

    }








}





