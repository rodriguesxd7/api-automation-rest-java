package functions;

import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import hooks.Hooks;
import io.restassured.RestAssured;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class Requests extends Hooks {

    private Response  response;
    private JSONObject json;
    public Object _RESULT;

    public String readPost () throws IOException {
        String file;
        return file = new String(Files.readAllBytes(Paths.get("src/main/resources/postToAddNewProduct.json")));
    }

    protected String getProps(String prop) throws IOException {
        FileInputStream in = new FileInputStream("src/main/resources/config.properties");
        Properties props = new Properties();
        props.load(in);
        return props.getProperty(prop);
    }

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

    public Requests getMethod(String endpoint, int statusCode) {
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

    public Requests getMethod(String endpoint, int statusCode, Object token) {
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

    public Requests postMethod(String endpoint, String payload, int statusCode) {
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

    public Requests postMethod(String endpoint, String payload, Map headers, int statusCode) {
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

    private String formatLog(String captureLog) {
        String[] lines = captureLog.split("\n");
        StringBuilder formattedLog = new StringBuilder();
        System.out.println(captureLog);

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            if (line.startsWith("Request method:")) {
                test.get().info(line);
            } else if (line.startsWith("Request URI:")) {
                test.get().info(line);
            } else if (line.startsWith("Headers:")) {
                test.get().info(line);
                i++;
                while (i < lines.length && !lines[i].startsWith("Cookies:")) {
                    test.get().info(lines[i]);
                    i++;
                }
                i--;
            }
        }
        return formattedLog.toString();
    }


    private Object findField(JSONObject jsonObject, String targetField) {

        if (jsonObject == null) return null;

        if (jsonObject.has(targetField)) {
            return jsonObject.get(targetField);
        }
        Iterator<String> keys = jsonObject.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            Object value = jsonObject.get(key);

            if (value instanceof JSONObject) {
                Object result = findField((JSONObject) value, targetField);
                if (result != null) {
                    return result;
                }
            } else if (value instanceof JSONArray) {
                Object result = searchArray((JSONArray) value, targetField);
                if (result != null) {
                    return result;
                }

            }
        }

        return null;
    }


    private Object searchArray(JSONArray jsonArray, String targetFiled) {
        for (int i = 0; i < jsonArray.length(); i++) {
            Object item = jsonArray.get(i);

            if (item instanceof JSONObject) {
                Object result = findField((JSONObject) item, targetFiled);
                if (result != null) {
                    return result;
                }
            } else if (item instanceof JSONArray) {
                Object result = searchArray((JSONArray) item, targetFiled);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }

    public Requests path(String path, Object... value) {
        json = new JSONObject(response.asString());
        _RESULT = findField(json, path);
        test.get().info("Path validation: ** " + path + " ** find: " + _RESULT);
        if (value.length >= 1 && !Arrays.asList(value).contains(_RESULT)) {
            throw new IllegalArgumentException("The value " + _RESULT + " does not match any of the expected value for path: " + path);
        }
        return this;
    }

    public Requests warning(String info) {
        test.get().warning(info);

        return this;
    }

    public Requests info(String info) {
        test.get().info(info);

        return this;
    }

}
