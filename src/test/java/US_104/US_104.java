package US_104;

import Utility.BaseParameter;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class US_104 extends BaseParameter {

    String customFieldGroupID;

    @Test
    public void Search() {
        Map<String, String> school = new HashMap<>();
        school.put("schoolId", schoolID);
        given()
                .spec(reqSpec)
                .body(school)
                .when()
                .post("/school-service/api/custom-field-groups/search")
                .then()
                .log().body()
                .statusCode(200);
    }

    @Test
    public void Create() {
        String fullName = randomGenerator.name().fullName();
        String orderName = randomGenerator.phoneNumber().subscriberNumber();
        int columnSize = randomGenerator.number().numberBetween(1, 3);

        Map<String, Object> customFieldGroup = new HashMap<>();
        customFieldGroup.put("name", fullName);
        customFieldGroup.put("orderNo", orderName);
        customFieldGroup.put("columnSize", columnSize);
        customFieldGroup.put("schoolId", schoolID);

        customFieldGroupID =
                given()
                        .spec(reqSpec)
                        .body(customFieldGroup)
                        .when()
                        .post("/school-service/api/custom-field-groups")
                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().path("id");

    }

    @Test(dependsOnMethods = "Create")
    public void CreateNegative() {

        String orderName = randomGenerator.phoneNumber().subscriberNumber();
        int columnSize = randomGenerator.number().numberBetween(1, 4);

        Map<String, Object> customFieldGroup = new HashMap<>();
        customFieldGroup.put("id", customFieldGroupID);
        customFieldGroup.put("orderNo", orderName);
        customFieldGroup.put("columnSize", columnSize);
        customFieldGroup.put("schoolId", schoolID);

        customFieldGroupID =
                given()
                        .spec(reqSpec)
                        .body(customFieldGroup)
                        .when()
                        .post("/school-service/api/custom-field-groups")
                        .then()
                        .log().body()
                        .statusCode(400)
                        .extract().path("id");

    }

    @Test(dependsOnMethods = "CreateNegative")
    public void Update() {

        String fullName = randomGenerator.name().fullName();
        String orderName = randomGenerator.phoneNumber().subscriberNumber();
        int columnSize = randomGenerator.number().numberBetween(1, 3);

        Map<String, String> customFieldGroup = new HashMap<>();
        customFieldGroup.put("id", customFieldGroupID);
        customFieldGroup.put("name", fullName);
        customFieldGroup.put("orderNo", orderName);
        customFieldGroup.put("columnSize", String.valueOf(columnSize));
        customFieldGroup.put("schoolId", schoolID);

        given()
                .spec(reqSpec)
                .body(customFieldGroup)
                .when()
                .put("/school-service/api/custom-field-groups")
                .then()
                .log().body()
                .statusCode(400);

    }

    @Test(dependsOnMethods = "Update")
    public void NegativeUpdate() {

        String fullName = randomGenerator.name().fullName();
        //String orderName= randomGenerator.phoneNumber().subscriberNumber();
        int columnSize = randomGenerator.number().numberBetween(1, 3);

        Map<String, String> customFieldGroup = new HashMap<>();
        customFieldGroup.put("id", customFieldGroupID);
        customFieldGroup.put("name", fullName);
        //customFieldGroup.put("orderNo", orderName);
        customFieldGroup.put("columnSize", String.valueOf(columnSize));
        customFieldGroup.put("schoolId", schoolID);

        given()
                .spec(reqSpec)
                .body(customFieldGroup)
                .when()
                .put("/school-service/api/custom-field-groups")
                .then()
                .log().body()
                .statusCode(400);

    }

    @Test(dependsOnMethods = "NegativeUpdate")
    public void Delete() {
        given()
                .spec(reqSpec)
                .when()
                .delete("/school-service/api/custom-field-groups/" + customFieldGroupID)
                .then()
                .statusCode(500);


    }
}
