package US_001;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class US_001 {
    RequestSpecification reqSpec;
    @Test
    public void login() {
        baseURI = "https://test.mersys.io";
        Map<String, String> userCredential = new HashMap<>();
        userCredential.put("username", "turkeyts");
        userCredential.put("password", "TechnoStudy123");
        userCredential.put("rememberMe", "true");

        Cookies cookies =
                given()
                        .body(userCredential)
                        .contentType(ContentType.JSON)

                        .when()

                        .post("/auth/login")
                        .then()
                        .statusCode(200)
                        .extract().response().getDetailedCookies();

        reqSpec = new RequestSpecBuilder()
                .addCookies(cookies)
                .setContentType(ContentType.JSON)
                .build();
    }

    @Test
    public void invalidlogin() {
        baseURI = "https://test.mersys.io";
        Map<String, String> userCredential = new HashMap<>();
        userCredential.put("username", "turkeyts1");
        userCredential.put("password", "TechnoStudy1232");
        userCredential.put("rememberMe", "true");

        Cookies cookies =
                given()
                        .body(userCredential)
                        .contentType(ContentType.JSON)

                        .when()

                        .post("/auth/login")
                        .then()
                        .statusCode(401)
                        .extract().response().getDetailedCookies();

        reqSpec = new RequestSpecBuilder()
                .addCookies(cookies)
                .setContentType(ContentType.JSON)
                .build();
    }
}
