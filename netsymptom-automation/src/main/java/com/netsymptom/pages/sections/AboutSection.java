package com.netsymptom.pages.sections;

import com.netsymptom.utils.WaitUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * About section: #about
 * Contains mission text and a feedback/bug-report form.
 */
public class AboutSection {

    private static final Logger log = LogManager.getLogger(AboutSection.class);

    private final By aboutSection    = By.id("about");
    private final By aboutHeading    = By.cssSelector("#about h2");
    private final By feedbackForm    = By.cssSelector("#about form");
    private final By nameInput       = By.cssSelector("#about input[type='text']");
    private final By emailInput      = By.cssSelector("#about input[type='email']");
    private final By messageTextarea = By.cssSelector("#about textarea");
    private final By submitBtn       = By.cssSelector("#about button[type='submit']");
    private final By thanksMessage   = By.id("feedback-thanks");

    private final WebDriver driver;

    public AboutSection(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isVisible() {
        return WaitUtils.scrollAndWait(aboutSection).isDisplayed();
    }

    public String getHeadingText() {
        return WaitUtils.scrollAndWait(aboutHeading).getText();
    }

    public void enterName(String name) {
        WebElement el = WaitUtils.scrollAndWait(nameInput);
        el.clear();
        el.sendKeys(name);
    }

    public void enterEmail(String email) {
        WebElement el = WaitUtils.waitForVisible(emailInput);
        el.clear();
        el.sendKeys(email);
    }

    public void enterMessage(String message) {
        WebElement el = WaitUtils.waitForVisible(messageTextarea);
        el.clear();
        el.sendKeys(message);
    }

    public void submitFeedback() {
        WaitUtils.waitForClickable(submitBtn).click();
        log.info("Submitted feedback form");
    }

    public boolean isThankYouMessageVisible() {
        WaitUtils.sleep(500);
        WebElement el = driver.findElement(thanksMessage);
        return !el.getAttribute("class").contains("hidden");
    }

    public void fillAndSubmitFeedback(String name, String email, String message) {
        enterName(name);
        enterEmail(email);
        enterMessage(message);
        submitFeedback();
    }
}
