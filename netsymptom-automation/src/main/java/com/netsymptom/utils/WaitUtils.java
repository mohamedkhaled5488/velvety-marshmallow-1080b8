package com.netsymptom.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitUtils {

    private static final Logger log = LogManager.getLogger(WaitUtils.class);

    private WaitUtils() {}

    private static WebDriverWait wait(int seconds) {
        return new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(seconds));
    }

    /** Standard explicit wait. */
    public static WebElement waitForVisible(By locator) {
        log.debug("Waiting for element visible: {}", locator);
        return wait(ConfigReader.getExplicitWait())
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement waitForVisible(By locator, int seconds) {
        return new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(seconds))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement waitForClickable(By locator) {
        return wait(ConfigReader.getExplicitWait())
                .until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static boolean waitForInvisible(By locator) {
        return wait(ConfigReader.getExplicitWait())
                .until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    /** Wait for the results panel to appear — engine takes ~10 seconds. */
    public static WebElement waitForDiagnosticResults() {
        log.info("Waiting up to {}s for diagnostic results...", ConfigReader.getDiagnosticWait());
        return wait(ConfigReader.getDiagnosticWait())
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("#results-wrap.show")));
    }

    /** Wait for results-wrap to have class 'show' (JS adds it after 10s engine run). */
    public static boolean waitForResultsClass() {
        return wait(ConfigReader.getDiagnosticWait()).until((ExpectedCondition<Boolean>) d -> {
            WebElement el = d.findElement(By.id("results-wrap"));
            String cls = el.getAttribute("class");
            return cls != null && cls.contains("show");
        });
    }

    /** Wait until the button text changes back (analysis finished). */
    public static boolean waitForAnalysisComplete() {
        return wait(ConfigReader.getDiagnosticWait()).until(
                ExpectedConditions.textToBe(By.id("btn-text"), "Execute Diagnostic Analysis"));
    }

    /** Wait for page to fully load via JS readyState. */
    public static void waitForPageLoad() {
        wait(ConfigReader.getPageLoadTimeout()).until((ExpectedCondition<Boolean>) d ->
                ((JavascriptExecutor) d).executeScript("return document.readyState")
                        .equals("complete"));
    }

    /** Scroll element into view then wait for it. */
    public static WebElement scrollAndWait(By locator) {
        WebDriver driver = DriverManager.getDriver();
        WebElement el = waitForVisible(locator);
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior:'smooth',block:'center'});", el);
        try { Thread.sleep(400); } catch (InterruptedException ignored) {}
        return el;
    }

    /** Hard pause — use sparingly, only when animations require settling time. */
    public static void sleep(long millis) {
        try { Thread.sleep(millis); } catch (InterruptedException ignored) {}
    }
}
