package functions;

import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import hooks.Hooks;
import hooks.UtilsRest;
import io.restassured.RestAssured;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import org.json.JSONObject;
import java.io.*;
import java.util.Arrays;
import static io.restassured.RestAssured.given;

public class GetRequests extends Hooks {

    private Response  response;
    public Object getToken() {
        response = given()
                .headers("Content-Type", "application/json")
                .body("{\n" +
                        "\"username\": \"kminchelle\",\n" +
                        "\"password\": \"0lelplR\"\n" +
                        "}")
                .when()
                .post("https://dummyjson.com/auth/login")
                .then().statusCode(200).extract().response();

        Object token = null;
        JSONObject obj = new JSONObject(response.asString());
        if(response.statusCode() == 200) {
            for (int i = 0; i <= obj.length(); i ++) {
                if(obj.has("token")) {
                    token = obj.getString("token");
                }
            }
        }
        return token;
    }

    public GetRequests getMethod(String endpoint, int statusCode) {
        RestAssured.reset();
        ByteArrayOutputStream ouContent = new ByteArrayOutputStream();
        PrintStream captureStream = new PrintStream(ouContent);

        RestAssured.filters(
                new RequestLoggingFilter(captureStream),
                new ResponseLoggingFilter(captureStream),
                new ErrorLoggingFilter(captureStream)
        );
        response = given()
                .when()
                .get(endpoint);
        formatLog(ouContent.toString());

        test.get().info("Request response: ");
        test.get().info(MarkupHelper.createCodeBlock(response.asString(), CodeLanguage.JSON));

        response.then().statusCode(statusCode);

        response = response.then().extract().response();
        return this;
    }

    public GetRequests getMethod(String endpoint, int statusCode, Object token) {
        RestAssured.reset();
        ByteArrayOutputStream ouContent = new ByteArrayOutputStream();
        PrintStream captureStream = new PrintStream(ouContent);

        RestAssured.filters(
                new RequestLoggingFilter(captureStream),
                new ResponseLoggingFilter(captureStream),
                new ErrorLoggingFilter(captureStream)
        );
        response = given()
                .headers("Authorization", token)
                .when()
                .get(endpoint);
        formatLog(ouContent.toString());

        test.get().info("Request response: ");
        test.get().info(MarkupHelper.createCodeBlock(response.asString(), CodeLanguage.JSON));

        response.then().statusCode(statusCode);



        response = response.then().extract().response();
        return this;
    }

    public GetRequests postMethod(String endpoint, String payload, int statusCode) {
        RestAssured.reset();
        ByteArrayOutputStream ouContent = new ByteArrayOutputStream();
        PrintStream captureStream = new PrintStream(ouContent);
        RestAssured.filters(
                new RequestLoggingFilter(captureStream),
                new ResponseLoggingFilter(captureStream),
                new ErrorLoggingFilter(captureStream)
        );
        response = given()
                .when()
                .body(payload)
                .post(endpoint);
        formatLog(ouContent.toString());

        test.get().info("Payload: ");
        test.get().info(MarkupHelper.createCodeBlock(payload, CodeLanguage.JSON));

        test.get().info("Request response: ");
        test.get().info(MarkupHelper.createCodeBlock(response.asString(), CodeLanguage.JSON));
        response.then().statusCode(statusCode);
        response = response.then().extract().response();
        return this;
    }
}
