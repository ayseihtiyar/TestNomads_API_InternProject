package US_101;

import Utility.BaseParameter;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class US_101 extends BaseParameter {
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


}


