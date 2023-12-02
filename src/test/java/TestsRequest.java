import functions.GetRequests;
import hooks.Utils;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

import static io.restassured.RestAssured.*;

@Test
public class TestsRequest extends GetRequests {

    @Test
    public void getAllUsers () throws IOException {
        getMethod(Utils.getUrl() + "users", 200);
    }

    @Test
    public void getUserById () throws IOException {
        getMethod(Utils.getUrl() + "users/1", 200);
    }

    @Test
    public void getInvalidUser () throws IOException {
        getMethod(Utils.getUrl() + "users/0", 404);
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
                .post(Utils.getUrl() + "auth/login")
                .then().statusCode(400).extract().response();

        Assert.assertEquals(response.then().extract().statusCode(), 400);
    }

    @Test
    public void getAllProducts() throws IOException {
        getMethod(Utils.getUrl() + "auth/products" , 200, getToken());
    }

    @Test
    public void getProductsWithoutToken() throws IOException {
        getMethod(Utils.getUrl() + "auth/products" , 403, "");
    }

    @Test
    public void getProductsWithInvalidToken() throws IOException {
        getMethod(Utils.getUrl() + "auth/products" , 401, "1234");
    }

    @Test
    public void addProduct() throws IOException {
        postMethod(Utils.getUrl() + "products/add", Utils.readPost(), 200 );
    }

    @Test
    public void getProductsWithoutAuth() throws IOException {
        getMethod(Utils.getUrl() + "products" , 200, getToken());
    }

    @Test
    public void getProductsById() throws IOException {
        getMethod(Utils.getUrl() + "products/9" , 200, getToken());
    }

    @Test
    public void getProductsByNonexistentId() throws IOException {
        getMethod(Utils.getUrl() + "products/200" , 404, getToken());
    }

}
