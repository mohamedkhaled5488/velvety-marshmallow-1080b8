package com.netsymptom.pages.sections;

import com.netsymptom.utils.WaitUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * The hero section above the fold.
 * Contains H1 headline, subheadline, "Analyze My Internet" CTA,
 * "How it works" link, and four trust badges.
 */
public class HeroSection {

    private static final Logger log = LogManager.getLogger(HeroSection.class);

    private final By heroSection      = By.cssSelector("section.grad-bg");
    private final By h1Headline       = By.cssSelector("section.grad-bg h1");
    private final By subHeadline      = By.cssSelector("section.grad-bg p.text-slate-400");
    private final By analyzeBtn       = By.cssSelector("a[href='#tool-section'].bg-indigo-600.font-bold");
    private final By howItWorksLink   = By.cssSelector("a[href='#learn'].text-slate-400");
    private final By trustBadges      = By.cssSelector("section.grad-bg .text-slate-500 span");
    private final By freeBadge        = By.cssSelector("section.grad-bg .rounded-full.border-indigo-500\\/30");

    private final WebDriver driver;

    public HeroSection(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isVisible()           { return WaitUtils.waitForVisible(heroSection).isDisplayed(); }
    public String  getHeadlineText()    { return WaitUtils.waitForVisible(h1Headline).getText(); }
    public String  getSubHeadlineText() { return WaitUtils.waitForVisible(subHeadline).getText(); }
    public boolean isAnalyzeBtnVisible(){ return WaitUtils.waitForVisible(analyzeBtn).isDisplayed(); }
    public boolean isHeadlineVisible()  { return WaitUtils.waitForVisible(h1Headline).isDisplayed(); }

    public void clickAnalyzeMyInternet() {
        WaitUtils.waitForClickable(analyzeBtn).click();
        log.info("Clicked 'Analyze My Internet' CTA in hero");
    }

    public void clickHowItWorks() {
        WaitUtils.waitForClickable(howItWorksLink).click();
        log.info("Clicked 'How it works' link in hero");
    }

    public int getTrustBadgeCount() {
        return driver.findElements(trustBadges).size();
    }

    public boolean headlineContains(String text) {
        return getHeadlineText().toLowerCase().contains(text.toLowerCase());
    }
}
