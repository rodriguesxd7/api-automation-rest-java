package hooks;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.lang.reflect.Method;

import java.util.Iterator;
import java.util.Locale;

public class Hooks {
    public ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    private static ExtentReports extent;

    @BeforeSuite
    public void setup() {
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter("report/report.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    @BeforeMethod
    public void beforeMethod(Method method) {
        ExtentTest extentTest = extent.createTest(method.getName().toUpperCase(Locale.ROOT));
        test.set(extentTest);
    }

    @AfterMethod
    public void afterMethod(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            test.get().fail(result.getThrowable().getMessage());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.get().pass("Test passed successfully");
        }
    }

    @AfterSuite
    public void afterSuite() {
        extent.flush();
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

    protected Object findField(JSONObject jsonObject, String targetField) {

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

    protected String formatLog(String captureLog) {
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

}
