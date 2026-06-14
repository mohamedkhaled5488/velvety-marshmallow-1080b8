package com.netsymptom.pages.sections;

import com.netsymptom.utils.WaitUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * FAQ section: #faq
 * 6 accordion items — each has an .acc-toggle button and .acc-body div.
 */
public class FAQSection {

    private static final Logger log = LogManager.getLogger(FAQSection.class);

    private final By faqSection      = By.id("faq");
    private final By faqHeading      = By.cssSelector("#faq h2");
    private final By accToggles      = By.cssSelector("#faq-list .acc-toggle");
    private final By accBodies       = By.cssSelector("#faq-list .acc-body");
    private final By accIcons        = By.cssSelector("#faq-list .acc-icon");

    private final WebDriver driver;

    public FAQSection(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isVisible() {
        return WaitUtils.scrollAndWait(faqSection).isDisplayed();
    }

    public String getHeadingText() {
        return WaitUtils.scrollAndWait(faqHeading).getText();
    }

    public int getFaqCount() {
        return driver.findElements(accToggles).size();
    }

    public void clickFaqItem(int index) {
        List<WebElement> toggles = driver.findElements(accToggles);
        if (index >= toggles.size()) throw new IndexOutOfBoundsException("FAQ index " + index + " does not exist");
        WaitUtils.scrollAndWait(accToggles);
        toggles.get(index).click();
        WaitUtils.sleep(500);
        log.info("Clicked FAQ item at index {}", index);
    }

    public boolean isFaqBodyOpen(int index) {
        List<WebElement> bodies = driver.findElements(accBodies);
        if (index >= bodies.size()) return false;
        String cls = bodies.get(index).getAttribute("class");
        return cls != null && cls.contains("open");
    }

    public String getFaqAnswerText(int index) {
        List<WebElement> bodies = driver.findElements(accBodies);
        if (index >= bodies.size()) return "";
        return bodies.get(index).getText();
    }

    public String getFaqQuestionText(int index) {
        List<WebElement> toggles = driver.findElements(accToggles);
        if (index >= toggles.size()) return "";
        return toggles.get(index).findElement(By.tagName("span")).getText();
    }

    public boolean isIconRotatedWhenOpen(int index) {
        List<WebElement> icons = driver.findElements(accIcons);
        if (index >= icons.size()) return false;
        String cls = icons.get(index).getAttribute("class");
        return cls != null && cls.contains("open");
    }

    /** Opens one item and verifies the others close (accordion behaviour). */
    public boolean onlyOneFaqOpenAtATime(int indexToOpen) {
        clickFaqItem(indexToOpen);
        List<WebElement> bodies = driver.findElements(accBodies);
        long openCount = bodies.stream()
            .filter(b -> { String cls = b.getAttribute("class"); return cls != null && cls.contains("open"); })
            .count();
        return openCount <= 1;
    }
}
