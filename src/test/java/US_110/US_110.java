package US_110;

import Utility.BaseParameter;
import org.testng.annotations.Test;

import java.util.*;

import static io.restassured.RestAssured.given;

public class US_110 extends BaseParameter {

    String incidentTypeID;

    @Test
    public void List() {
        Map<String, String> incidentType = new HashMap<>();
        incidentType.put("schoolId", schoolID);
        given()
                .spec(reqSpec)
                .body(incidentType)
                .when()
                .post("/school-service/api/incident-type/search")
                .then()
                .log().body()
                .statusCode(200)
        ;
    }

    @Test
    public void Add() {
        Map<String, String> incidentType = new HashMap<>();
        incidentType.put("name", randomGenerator.educator().course());
        incidentType.put("active", "true");
        incidentType.put("schoolId", schoolID);
        incidentType.put("minPoint", "0");
        incidentType.put("maxPoint", "100");
        incidentType.put("academicBased", "false");

        incidentTypeID =
                given()
                        .spec(reqSpec)
                        .body(incidentType)
                        .when()
                        .post("/school-service/api/incident-type")
                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().path("id")
        ;
    }

    @Test(dependsOnMethods = "Add")
    public void Edit() {
        Map<String, String> incidentType = new HashMap<>();
        incidentType.put("id", incidentTypeID);
        incidentType.put("name", randomGenerator.educator().course());
        incidentType.put("active", "true");
        incidentType.put("schoolId", schoolID);
        incidentType.put("minPoint", "0");
        incidentType.put("maxPoint", "100");
        incidentType.put("academicBased", "false");

        given()
                .spec(reqSpec)
                .body(incidentType)
                .when()
                .put("/school-service/api/incident-type")
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
                .delete("/school-service/api/incident-type/" + incidentTypeID)
                .then()
                .statusCode(200)
        ;
    }

    @Test(dependsOnMethods = "Delete")
    public void NegativeDelete() {
        given()
                .spec(reqSpec)
                .when()
                .delete("/school-service/api/incident-type/" + incidentTypeID)
                .then()
                .statusCode(400)
        ;
    }
}
