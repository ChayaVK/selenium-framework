package com.chaya.base;

import com.chaya.pages.LoginPage;
import com.chaya.pages.ProductsPage;
import com.chaya.utils.ConfigReader;
import com.chaya.utils.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

public abstract class BaseTest {
    // One driver per thread -> safe parallel runs
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return driver.get();
    }

    @BeforeMethod(alwaysRun = true)    // alwaysRun: needed when running by groups
    public void setUp() {
        String browser = ConfigReader.get("browser");                         // -Dbrowser overrides
        boolean headless = Boolean.parseBoolean(ConfigReader.get("headless")); // -Dheadless overrides

        driver.set(DriverFactory.createDriver(browser, headless));
        getDriver().manage().window().maximize();
        getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        getDriver().get(ConfigReader.get("url"));                              // -Durl overrides
    }

    // Common precondition used by many tests
    protected ProductsPage loginAsStandardUser() {
        return new LoginPage(getDriver())
                .login(ConfigReader.get("username"), ConfigReader.get("password"));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (getDriver() != null) {
            getDriver().quit();
            driver.remove();          // clean this thread's slot
        }
    }
}
