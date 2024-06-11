package US_109;

import Utility.BaseParameter;
import org.testng.annotations.Test;

import java.util.*;

import static io.restassured.RestAssured.given;

public class US_109 extends BaseParameter {

    String gradingSchemeID;

    @Test
    public void List() {
        given()
                .spec(reqSpec)
                .when()
                .get("/school-service/api/grading-schemes/school/" + schoolID + "/search")
                .then()
                .log().body()
                .statusCode(200)
        ;
    }

    @Test
    public void Add() {
        Map<String, String> gradingSchemes = new HashMap<>();
        gradingSchemes.put("name", randomGenerator.educator().course());
        gradingSchemes.put("active", "true");
        gradingSchemes.put("type", "POINT");
        gradingSchemes.put("enablePoint", "false");
        gradingSchemes.put("schoolId", schoolID);

        gradingSchemeID =
                given()
                        .spec(reqSpec)
                        .body(gradingSchemes)
                        .when()
                        .post("/school-service/api/grading-schemes")
                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().path("id")
        ;
    }

    @Test(dependsOnMethods = "Add")
    public void Edit() {
        Map<String, String> gradingSchemes = new HashMap<>();
        gradingSchemes.put("id", gradingSchemeID);
        gradingSchemes.put("name", randomGenerator.educator().course() + randomGenerator.idNumber().hashCode());
        gradingSchemes.put("active", "true");
        gradingSchemes.put("type", "POINT");
        gradingSchemes.put("enablePoint", "false");
        gradingSchemes.put("schoolId", schoolID);

        given()
                .spec(reqSpec)
                .body(gradingSchemes)
                .when()
                .put("/school-service/api/grading-schemes")
                .then()
                .log().body()
                .statusCode(200)
        ;
    }

    @Test(dependsOnMethods = "Edit")  // Delete Contains BUG
    public void Delete() {

        given()
                .spec(reqSpec)
                .when()
                .delete("/school-service/api/grading-schemes/" + gradingSchemeID)
                .then()
                .statusCode(200)
        ;
    }
}