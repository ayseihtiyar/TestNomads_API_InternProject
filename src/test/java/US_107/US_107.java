package US_107;

import Utility.BaseParameter;
import org.testng.annotations.Test;

import java.util.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class US_107 extends BaseParameter {

    @Test
    public void remove() {
        List<String> gradingSchemes = new ArrayList<>();
        gradingSchemes.add("658ef7edcacea97f2a0cb065");

        given().spec(reqSpec)
                .body(gradingSchemes)
                .when()
                .post("/school-service/api/student-group/6660c1d60886240ea1a62bf3/remove-students")
                .then()
                .log().body()
                .statusCode(200)
        ;
    }

    @Test
    public void check() {
        given().spec(reqSpec)
                .when()
                .get("/school-service/api/students/group/6660c1d60886240ea1a62bf3")
                .then()
                .log().body()
                .statusCode(200)
                .body("content.'id'", not(hasItem("658ef7edcacea97f2a0cb065")))
        ;
    }
}