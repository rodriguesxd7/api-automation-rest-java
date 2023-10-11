package hooks;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.lang.reflect.Method;
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

}
