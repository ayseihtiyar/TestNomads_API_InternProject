package US_105;

import Utility.BaseParameter;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class US_105 extends BaseParameter {

    String studentGroupsID;

    @Test
    public void List() {
        given()
                .spec(reqSpec)
                .when()
                .get("/school-service/api/education-standard/school/" + schoolID)
                .then()
                .log().body()
                .statusCode(200);
    }

    @Test
    public void CreateStudent() {

        String fullName = randomGenerator.name().fullName();

        Map<String, String> studentGroups = new HashMap<>();
        studentGroups.put("name", fullName);
        studentGroups.put("description", "testnomads is the best group in batch 5 ");
        studentGroups.put("schoolId", schoolID);

        studentGroupsID =
                given()
                        .spec(reqSpec)
                        .body(studentGroups)
                        .when()
                        .post("/school-service/api/student-group")
                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().path("id");

    }

    @Test(dependsOnMethods = "CreateStudent")
    public void Edit() {
        Map<String, String> studentGroupsEdit = new HashMap<>();
        studentGroupsEdit.put("id", studentGroupsID);
        studentGroupsEdit.put("name", randomGenerator.name().fullName());
        studentGroupsEdit.put("active", "true");
        studentGroupsEdit.put("description", "always the best group in batch 5");
        studentGroupsEdit.put("publicGroup", "true");
        studentGroupsEdit.put("showToStudent", "false");
        studentGroupsEdit.put("schoolId", schoolID);

        given()
                .spec(reqSpec)
                .body(studentGroupsEdit)
                .when()
                .put("/school-service/api/student-group")
                .then()
                .log().body()
                .statusCode(200);

    }

    @Test(dependsOnMethods = "Edit")
    public void Delete() {
        given()
                .spec(reqSpec)
                .when()
                .delete("/school-service/api/student-group/" + studentGroupsID)
                .then()
                .statusCode(200);

    }

    @Test(dependsOnMethods = "Delete")
    public void DeleteNegative() {

        given()
                .spec(reqSpec)
                .when()
                .delete("/school-service/api/student-group/" + studentGroupsID)
                .then()
                .statusCode(400);
    }


}