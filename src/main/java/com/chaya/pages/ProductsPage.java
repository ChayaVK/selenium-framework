package com.chaya.pages;

import com.chaya.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductsPage {
    private WaitUtils wait;

    private By pageTitle = By.className("title");

    public ProductsPage(WebDriver driver) {
        this.wait = new WaitUtils(driver);
    }

    public String getTitle() {
        return wait.waitForVisible(pageTitle).getText();
    }
}