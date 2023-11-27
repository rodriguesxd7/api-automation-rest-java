import functions.Requests;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class TestsRequest extends Requests {
    String url;
    {
        try {
            url = getProps("base");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getAllUsers () throws IOException {
        getMethod(url + "users", 200);
    }

    @Test
    public void loginWithInvalidCredentials() {
        Response response = given()
                .headers("Content-Type", "application/json")
                .body("{\n" +
                        "\"username\": \"bruno\",\n" +
                        "\"password\": \"1234\"\n" +
                        "}")
                .when()
                .post(url + "auth/login")
                .then().statusCode(400).extract().response();

        Assert.assertEquals(response.then().extract().statusCode(), 400);
    }

    @Test
    public void getAllProducts() throws IOException {
        getMethod(url + "auth/products" , 200, getToken());
    }

    @Test
    public void getProductsWithoutToken() throws IOException {
        getMethod(url + "auth/products" , 403, "");
    }

    @Test
    public void getProductsWithInvalidToken() throws IOException {
        getMethod(url + "auth/products" , 401, "1234");
    }

    @Test
    public void addProduct() throws IOException {
        postMethod(url + "products/add", readPost(), 200 );
    }

    @Test
    public void getProductsWithoutAuth() throws IOException {
        getMethod(url + "products" , 200, getToken());
    }

    @Test
    public void getProductsById() throws IOException {
        getMethod(url + "products/9" , 200, getToken());
    }

    @Test
    public void getProductsByNonexistentId() throws IOException {
        getMethod(url + "products/200" , 404, getToken());
    }



    @Test
    public void request_get_test() throws IOException {
        getMethod(url, 200)
                .path("method", "GET")
                .info("Result path: " + _RESULT);
    }

    @Test
    public void request_post_with_headers() throws IOException {
        Map headers = new HashMap();
        headers.put("Authorization", "Bearer token");
        postMethod(getProps("base"), "payload", headers, 201);
    }

    @Test
    public void request_post() throws IOException {
        getMethod(url, 200)
                .path("path_final")
                .warning("Info path_final: " + _RESULT);

    }

}
