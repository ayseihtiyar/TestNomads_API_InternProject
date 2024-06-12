package US_102;

import Utility.BaseParameter;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class US_102 extends BaseParameter {
    String citiesID;

    @Test
    public void List() {
        String X = "{\n" +
                "    \"name\": \"\"\n" +
                "}";

        given()
                .spec(reqSpec)
                .body(X)
              //  .contentType(ContentType.JSON)
                .when()
                .post("/school-service/api/cities/search")
                .then()
                .log().body()
                .statusCode(200)
        ;
    }

    @Test
    public void Add() {
        String newCities ="{\n" +
                "    \"id\": null,\n" +
                "    \"name\": \"{{$randomCity}}\",\n" +
                "    \"country\": {\n" +
                "        \"id\": \"{{CountryID}}\"\n" +
                "    },\n" +
                "    \"state\": null,\n" +
                "    \"translateName\": []\n" +
                "}";

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
        String editCitie = "{\n" +
                "    \"id\": \"{{CitiesID}}\",\n" +
                "    \"name\": \"{{$randomCity}}\",\n" +
                "    \"country\": {\n" +
                "        \"id\": \"{{CountryID}}\"\n" +
                "    },\n" +
                "    \"state\": null,\n" +
                "    \"translateName\": []\n" +
                "}";
        given()
                .spec(reqSpec)
                .body(editCitie)
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
                .delete("/school-service/api/cities" + citiesID)
                .then()
                .statusCode(200)
        ;
    }


}
