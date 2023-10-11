import functions.Requests;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class TestsRequest extends Requests {
    String payload = "{\n" +
            "\"data\": {\n" +
            "\"id\": 2,\n" +
            "\"email\": \"janet.weaver@reqres.in\",\n" +
            "\"first_name\": \"Janet\",\n" +
            "\"last_name\": \"Weaver\",\n" +
            "\"avatar\": \"https://reqres.in/img/faces/2-image.jpg\"\n" +
            "},\n" +
            "\"support\": {\n" +
            "\"url\": \"https://reqres.in/#support-heading\",\n" +
            "\"text\": \"To keep ReqRes free, contributions towards server costs are appreciated!\"\n" +
            "}\n" +
            "}";

    @Test
    public void requisition_get() {
        given()
                .when()
                .get("https://reqres.in/api/users/2")
                .then().body("data.id", Matchers.equalTo(2));
    }

    @Test
    public void requisition_post() {
        given()
                .when()
                .headers("", "")
                .body("")
                .post("https://reqres.in/api/users/2")
                .then().log().body().body("id", Matchers.notNullValue());

        given()
                .when()
                .headers("", "")
                .body("")
                .post("https://reqres.in/api/users/2")
                .then().log().body().body("id", Matchers.notNullValue());
        given()
                .when()
                .headers("", "")
                .body("")
                .post("https://reqres.in/api/users/2")
                .then().log().body().body("id", Matchers.notNullValue());
    }

    @Test
    public void request_get() throws IOException {
        _GET(getProps("base"), 200)
                .path("id", 1, 3, 4, 5, 6, 9, 7, 2)
                .info("Result path: " + _RESULT);
    }

    @Test
    public void request_post_with_headers() throws IOException {
        Map headers = new HashMap();
        headers.put("Authorization", "Bearer token");
        _POST(getProps("base"), payload, headers, 201)
        ;
    }

    //post auth
    // get id
    // post usando id
    @Test
    public void request_post() throws IOException {
        _GET(getProps("endpoint"), 200)
                .path("path_final")
                .warning("Info path_final: " + _RESULT);

    }
}
