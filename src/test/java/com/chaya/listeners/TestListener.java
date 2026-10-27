package com.chaya.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.chaya.base.BaseTest;
import com.chaya.utils.ConfigReader;
import com.chaya.utils.ScreenshotUtil;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {
    private static ExtentReports extent;
    private static final ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    // Lets tests write steps into the report (see ExtentLogger)
    public static ExtentTest getTest() {
        return test.get();
    }

    @Override
    public synchronized void onStart(ITestContext context) {
        if (extent == null) {
            ExtentSparkReporter spark = new ExtentSparkReporter(
                    System.getProperty("user.dir") + "/reports/ExtentReport.html");
            spark.config().setDocumentTitle("SauceDemo Automation");
            spark.config().setReportName("SauceDemo Test Report");
            spark.config().setTheme(Theme.STANDARD);

            extent = new ExtentReports();
            extent.attachReporter(spark);
            extent.setSystemInfo("Application URL", ConfigReader.get("url"));
            extent.setSystemInfo("Browser", ConfigReader.get("browser"));
            extent.setSystemInfo("Headless", ConfigReader.get("headless"));
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("Java", System.getProperty("java.version"));
            extent.setSystemInfo("Tester", "Chaya V K");
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest t = extent.createTest(result.getMethod().getMethodName());
        t.assignCategory(result.getMethod().getGroups());   // smoke / regression
        t.assignDevice(ConfigReader.get("browser"));
        test.set(t);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        getTest().pass("Test passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        getTest().fail(result.getThrowable());
        WebDriver driver = BaseTest.getDriver();
        if (driver != null) {
            String name = result.getMethod().getMethodName();
            // Embedded inside the HTML, so the report works in Jenkins / email
            String base64 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
            getTest().fail("Screenshot at failure",
                    MediaEntityBuilder.createScreenCaptureFromBase64String(base64, name).build());
            // Also keep a .png file (Jenkins archives the screenshots/ folder)
            ScreenshotUtil.capture(driver, name);
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        if (getTest() == null) {             // skipped before onTestStart (e.g. config failure)
            test.set(extent.createTest(result.getMethod().getMethodName()));
        }
        getTest().skip(result.getThrowable() != null
                ? result.getThrowable().getMessage() : "Test skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }
}
