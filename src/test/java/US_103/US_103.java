package US_103;

import Utility.BaseParameter;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class US_103 extends BaseParameter {
    String userId;
    String randomName2;

    @Test
    public void CreateExam() {
        String name = randomGenerator.name().name();
        Map<String, Object> infos = new HashMap<>();
        Map<String, String> gradeLevel = new HashMap<>();

        infos.put("name", name);
        infos.put("school", "6576fd8f8af7ce488ac69b89");
        infos.put("registrationStartDate", "2024-04-09T00:00:00.000Z");
        infos.put("registrationEndDate", "2024-06-19T00:00:00.000Z");
        infos.put("academicPeriod", "6577022e8af7ce488ac69b96");

        gradeLevel.put("id", "654898fae70d9e34a8331e51");
        infos.put("gradeLevel", gradeLevel);


        userId = given()
                .spec(reqSpec)
                .body(infos)
                .when()
                .post("/school-service/api/exams")
                .then()
                .statusCode(201)
                .extract().path("id");

        System.out.println("id:" + userId);
    }

    //666c8c595941ea262d32cad9
    @Test
    public void ListExams() {
        Map<String, String> infos = new HashMap<>();
        infos.put("academicPeriodId", "6577022e8af7ce488ac69b96");
        infos.put("schoolId", "6576fd8f8af7ce488ac69b89");

        given()
                .spec(reqSpec)
                .body(infos)
                .when()
                .post("/school-service/api/exams/search")
                .then().log().body()
                .statusCode(200)
        ;
    }

    @Test(dependsOnMethods = "CreateExam")
    public void Edit() {
        randomName2 = randomGenerator.name().fullName();
        Map<String, Object> infos = new HashMap<>();
        Map<String, String> gradeLevel = new HashMap<>();

        infos.put("id", userId);
        infos.put("name", randomName2);
        infos.put("school", "6576fd8f8af7ce488ac69b89");
        infos.put("registrationStartDate", "2024-04-09T00:00:00.000Z");
        infos.put("registrationEndDate", "2024-06-19T00:00:00.000Z");
        infos.put("academicPeriod", "6577022e8af7ce488ac69b96");
        gradeLevel.put("id", "654898fae70d9e34a8331e51");
        infos.put("gradeLevel", gradeLevel);

        given()
                .spec(reqSpec)
                .body(infos)
                .when()
                .put("/school-service/api/exams")
                .then()
                .log().body()
                .statusCode(200)
        ;
    }

    @Test(dependsOnMethods = "Edit")
    public void EditNeg() {
        Map<String, Object> infos = new HashMap<>();
        Map<String, String> gradeLevel = new HashMap<>();

        infos.put("id", "48846846816s8fsa");
        infos.put("name", randomName2);
        infos.put("school", "6576fd8f8af7ce488ac69b89");
        infos.put("registrationStartDate", "2024-04-09T00:00:00.000Z");
        infos.put("registrationEndDate", "2024-06-19T00:00:00.000Z");
        infos.put("academicPeriod", "6577022e8af7ce488ac69b96");
        gradeLevel.put("id", "654898fae70d9e34a8331e51");
        infos.put("gradeLevel", gradeLevel);

        given()
                .spec(reqSpec)
                .body(infos)
                .when()
                .put("/school-service/api/exams")
                .then()
                .log().body()
                .statusCode(400)
        ;

    }

    @Test(dependsOnMethods = "EditNeg")
    public void Delete() {
        given()
                .spec(reqSpec)
                .when()
                .delete("/school-service/api/exams/" + userId)
                .then()
                .statusCode(204)
        ;
    }

    @Test(dependsOnMethods = "Delete")
    public void DeleteNeg() {
        given()
                .spec(reqSpec)
                .when()
                .delete("/school-service/api/exams/" + userId)
                .then()
                .statusCode(204)
        ;
    }
}