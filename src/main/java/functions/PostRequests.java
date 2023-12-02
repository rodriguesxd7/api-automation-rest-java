package functions;

import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import hooks.Hooks;
import io.restassured.RestAssured;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;
import static io.restassured.RestAssured.given;

public class PostRequests extends Hooks {
    private Response response;
    public PostRequests postMethod(String endpoint, String payload, int statusCode) {
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

    public PostRequests postMethod(String endpoint, String payload, Map headers, int statusCode) {
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
                .headers(headers)
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
