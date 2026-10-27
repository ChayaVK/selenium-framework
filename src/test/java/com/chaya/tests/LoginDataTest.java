package com.chaya.tests;

import com.chaya.base.BaseTest;
import com.chaya.pages.LoginPage;
import com.chaya.pages.ProductsPage;
import com.chaya.utils.ConfigReader;
import com.chaya.utils.ExcelUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginDataTest extends BaseTest {

    @DataProvider(name = "loginData")
    public Object[][] loginData() {
        return ExcelUtils.getData(ConfigReader.get("testdata"), "Login");
    }

    @Test(dataProvider = "loginData", groups = {"regression"})
    public void loginWithExcelData(String username, String password, String expected) {
        LoginPage loginPage = new LoginPage(getDriver());

        if (expected.equalsIgnoreCase("success")) {
            ProductsPage productsPage = loginPage.login(username, password);
            Assert.assertEquals(productsPage.getTitle(), "Products");
        } else {
            loginPage.login(username, password);
            Assert.assertTrue(loginPage.getErrorMessage().contains(expected),
                    "Expected error to contain: " + expected);
        }
    }
}