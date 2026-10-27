package com.chaya.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

// Factory pattern: creates the right browser; BaseTest just asks for one
public class DriverFactory {

    public static WebDriver createDriver(String browser, boolean headless) {
        switch (browser.toLowerCase()) {
            case "edge": {
                EdgeOptions options = new EdgeOptions();
                if (headless) options.addArguments("--headless=new", "--window-size=1920,1080");
                return new EdgeDriver(options);
            }
            case "firefox": {
                FirefoxOptions options = new FirefoxOptions();
                if (headless) options.addArguments("-headless", "--width=1920", "--height=1080");
                return new FirefoxDriver(options);
            }
            default: {
                ChromeOptions options = new ChromeOptions();
                if (headless) options.addArguments("--headless=new", "--window-size=1920,1080");
                return new ChromeDriver(options);
            }
        }
    }
}
