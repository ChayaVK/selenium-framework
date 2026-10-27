package com.chaya.pages;

import com.chaya.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

// Parent of all page classes: shared driver, waits and common actions
public abstract class BasePage {
    protected WebDriver driver;
    protected WaitUtils wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtils(driver);
    }

    // ---- Basic actions (waits built in)
    protected void click(By locator) {
        wait.waitForClickable(locator).click();
    }

    protected void type(By locator, String text) {
        WebElement element = wait.waitForVisible(locator);
        element.clear();
        element.sendKeys(text);
    }

    protected String getText(By locator) {
        return wait.waitForVisible(locator).getText();
    }

    // ---- Safe check: no exception if the element is absent
    protected boolean isDisplayed(By locator) {
        return !driver.findElements(locator).isEmpty()
                && driver.findElement(locator).isDisplayed();
    }

    // ---- Dropdown (HTML <select>)
    protected void selectByVisibleText(By locator, String text) {
        new Select(wait.waitForVisible(locator)).selectByVisibleText(text);
    }

    // ---- Mouse and JavaScript
    protected void hover(By locator) {
        new Actions(driver).moveToElement(wait.waitForVisible(locator)).perform();
    }

    protected void scrollIntoView(By locator) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});", wait.waitForVisible(locator));
    }

    protected void jsClick(By locator) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].click();", wait.waitForVisible(locator));
    }

    // ---- Frames, windows, alerts
    protected void switchToFrame(By frameLocator) {
        driver.switchTo().frame(wait.waitForVisible(frameLocator));
    }

    protected void switchToDefault() {
        driver.switchTo().defaultContent();
    }

    protected void switchToNewWindow(String parentHandle) {
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(parentHandle)) {
                driver.switchTo().window(handle);
                return;
            }
        }
    }

    protected void acceptAlert() {
        driver.switchTo().alert().accept();
    }

    // ---- Page info
    public String getPageTitle() {
        return driver.getTitle();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
