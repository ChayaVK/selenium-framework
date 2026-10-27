package com.chaya.tests;

import com.chaya.base.BaseTest;
import com.chaya.listeners.ExtentLogger;
import com.chaya.pages.LoginPage;
import com.chaya.pages.ProductsPage;
import com.chaya.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test(groups = {"smoke", "regression"})
    public void validLogin() {
        LoginPage loginPage = new LoginPage(getDriver());
        ExtentLogger.info("Opened login page");

        ProductsPage productsPage = loginPage.login(
                ConfigReader.get("username"), ConfigReader.get("password"));
        ExtentLogger.info("Logged in as " + ConfigReader.get("username"));

        Assert.assertEquals(productsPage.getTitle(), "Products");
        ExtentLogger.pass("Products page is displayed");
    }

    @Test(groups = {"regression"})
    public void invalidLogin() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.login("wrong_user", "wrong_pass");
        Assert.assertTrue(loginPage.getErrorMessage().contains("do not match"));
    }

    @Test(groups = {"regression"})
    public void secondInvalidLogin() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.login("wrong_user", "wrong_pass");
        Assert.assertTrue(loginPage.getErrorMessage().contains("do not match"));
    }
}