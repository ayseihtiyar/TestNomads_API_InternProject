package US_108;

import Utility.BaseParameter;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class US_108 extends BaseParameter {
    String educationStandardID;

    @Test
    public void List() {
        given()
                .spec(reqSpec)
                .when()
                .get("/school-service/api/education-standard/school/" + schoolID)
                .then()
                .log().body()
                .statusCode(200)
        ;
    }

    @Test
    public void Add() {
        Map<String, String> educationStandard = new HashMap<>();
        educationStandard.put("name", randomGenerator.educator().university());
        educationStandard.put("description", randomGenerator.educator().course());
        educationStandard.put("schoolId", schoolID);

        educationStandardID =
                given()
                        .spec(reqSpec)
                        .body(educationStandard)
                        .when()
                        .post("/school-service/api/education-standard")
                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().path("id")
        ;
    }

    @Test(dependsOnMethods = "Add")
    public void Edit() {
        Map<String, String> educationStandard_e = new HashMap<>();
        educationStandard_e.put("id", educationStandardID);
        educationStandard_e.put("name", randomGenerator.educator().university() + randomGenerator.idNumber().hashCode());
        educationStandard_e.put("description", randomGenerator.educator().course());
        educationStandard_e.put("schoolId", schoolID);
        given()
                .spec(reqSpec)
                .body(educationStandard_e)
                .when()
                .put("/school-service/api/education-standard")
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
                .delete("/school-service/api/education-standard/" + educationStandardID)
                .then()
                .statusCode(204)
        ;
    }

    @Test(dependsOnMethods = "Delete")
    public void DeleteNegative() {

        given()
                .spec(reqSpec)
                .when()
                .delete("/school-service/api/education-standard/" + educationStandardID)
                .then()
                .statusCode(400)
        ;
    }
}
