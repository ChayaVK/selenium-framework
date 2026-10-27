package com.chaya.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

public class ProductsPage extends BasePage {

    private By pageTitle     = By.className("title");
    private By productNames  = By.className("inventory_item_name");
    private By productPrices = By.className("inventory_item_price");
    private By sortDropdown  = By.className("product_sort_container");

    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    public String getTitle() {
        return getText(pageTitle);
    }

    public void sortBy(String visibleText) {
        selectByVisibleText(sortDropdown, visibleText);
    }

    // All options of the sort dropdown, as text
    public List<String> getSortOptions() {
        List<String> options = new ArrayList<>();
        for (WebElement option : new Select(wait.waitForVisible(sortDropdown)).getOptions()) {
            options.add(option.getText());
        }
        return options;
    }

    public String getSelectedSortOption() {
        return new Select(wait.waitForVisible(sortDropdown)).getFirstSelectedOption().getText();
    }

    // List<WebElement> -> List<String>
    public List<String> getProductNames() {
        wait.waitForVisible(productNames);
        List<String> names = new ArrayList<>();
        for (WebElement element : driver.findElements(productNames)) {
            names.add(element.getText());
        }
        return names;
    }

    // "$29.99" -> 29.99
    public List<Double> getProductPrices() {
        wait.waitForVisible(productPrices);
        List<Double> prices = new ArrayList<>();
        for (WebElement element : driver.findElements(productPrices)) {
            prices.add(Double.parseDouble(element.getText().replace("$", "")));
        }
        return prices;
    }
}
