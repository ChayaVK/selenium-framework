package com.chaya.pages;

import com.chaya.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {
    private WebDriver driver;
    private WaitUtils wait;

    // Locators
    private By usernameField = By.id("user-name");
    private By passwordField = By.id("password");
    private By loginButton  = By.id("login-button");
    private By errorMessage = By.cssSelector("h3[data-test='error']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtils(driver);
    }

    // Actions
    public void enterUsername(String username) {
        WebElement field = wait.waitForVisible(usernameField);
        field.clear();
        field.sendKeys(username);
    }

    public void enterPassword(String password) {
        WebElement field = wait.waitForVisible(passwordField);
        field.clear();
        field.sendKeys(password);
    }

    public void clickLogin() {
        wait.waitForClickable(loginButton).click();
    }

    public ProductsPage login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
        return new ProductsPage(driver);
    }

    public String getErrorMessage() {
        return wait.waitForVisible(errorMessage).getText();
    }
}