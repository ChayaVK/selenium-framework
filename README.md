# Java Selenium Test Automation Framework

Java + Selenium 4 + TestNG + Maven, Page Object Model, Extent Reports, Jenkins.
Application under test: https://www.saucedemo.com/

## Run
| Command | What it does |
|---|---|
| `mvn clean test` | Regression suite (testng.xml), Chrome |
| `mvn clean test -DsuiteXmlFile=smoke.xml` | Smoke tests only |
| `mvn clean test -Dbrowser=firefox` | Run on Firefox (chrome / firefox / edge) |
| `mvn clean test -Dheadless=true` | No browser window |
| `mvn clean test -Durl=https://...` | Another environment |

Defaults live in `src/main/resources/config.properties`; any `-Dkey=value` overrides them.

## Structure
- `com.chaya.pages`      BasePage, LoginPage, ProductsPage (Page Object Model)
- `com.chaya.utils`      ConfigReader, DriverFactory, WaitUtils, ExcelUtils, ScreenshotUtil
- `com.chaya.base`       BaseTest (browser setup / teardown, ThreadLocal driver)
- `com.chaya.listeners`  TestListener (Extent report + Base64 screenshots), ExtentLogger
- `com.chaya.tests`      SmokeTest, LoginTest, LoginDataTest, SortTest, DropDowns

## Reports
`reports/ExtentReport.html` (self-contained, screenshots embedded) and `screenshots/`.

## Jenkins
`Jenkinsfile`: parameters BROWSER, HEADLESS, SUITE, URL; nightly cron + pollSCM.
