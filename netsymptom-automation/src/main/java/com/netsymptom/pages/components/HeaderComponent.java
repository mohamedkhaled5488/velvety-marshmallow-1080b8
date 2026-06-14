package com.netsymptom.pages.components;

import com.netsymptom.utils.WaitUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Represents the sticky header: #site-header
 * Contains logo, nav links (Diagnose, Learn, FAQ, About) and "Run Diagnosis" CTA.
 */
public class HeaderComponent {

    private static final Logger log = LogManager.getLogger(HeaderComponent.class);

    private final By header          = By.id("site-header");
    private final By logoLink        = By.cssSelector("#site-header a[href='#']");
    private final By logoText        = By.cssSelector("#site-header span.font-semibold");
    private final By navDiagnose     = By.cssSelector("nav a[href='#tool-section']");
    private final By navLearn        = By.cssSelector("nav a[href='#learn']");
    private final By navFaq          = By.cssSelector("nav a[href='#faq']");
    private final By navAbout        = By.cssSelector("nav a[href='#about']");
    private final By ctaRunDiagnosis = By.cssSelector("#site-header a[href='#tool-section'].bg-indigo-600");

    private final WebDriver driver;

    public HeaderComponent(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getHeader()           { return WaitUtils.waitForVisible(header); }
    public WebElement getLogoLink()         { return WaitUtils.waitForVisible(logoLink); }
    public String     getLogoText()         { return WaitUtils.waitForVisible(logoText).getText(); }
    public WebElement getNavDiagnose()      { return WaitUtils.waitForClickable(navDiagnose); }
    public WebElement getNavLearn()         { return WaitUtils.waitForClickable(navLearn); }
    public WebElement getNavFaq()           { return WaitUtils.waitForClickable(navFaq); }
    public WebElement getNavAbout()         { return WaitUtils.waitForClickable(navAbout); }
    public WebElement getCtaRunDiagnosis()  { return WaitUtils.waitForClickable(ctaRunDiagnosis); }

    public void clickLogo()           { getLogoLink().click();        log.info("Clicked logo"); }
    public void clickNavDiagnose()    { getNavDiagnose().click();     log.info("Clicked nav: Diagnose"); }
    public void clickNavLearn()       { getNavLearn().click();        log.info("Clicked nav: Learn"); }
    public void clickNavFaq()         { getNavFaq().click();          log.info("Clicked nav: FAQ"); }
    public void clickNavAbout()       { getNavAbout().click();        log.info("Clicked nav: About"); }
    public void clickRunDiagnosis()   { getCtaRunDiagnosis().click(); log.info("Clicked CTA: Run Diagnosis"); }

    public boolean isHeaderSticky() {
        String pos = getHeader().getCssValue("position");
        return "sticky".equals(pos);
    }

    public boolean isLogoVisible()          { return getLogoLink().isDisplayed(); }
    public boolean isNavDiagnoseVisible()   { return getNavDiagnose().isDisplayed(); }
    public boolean isCtaVisible()           { return getCtaRunDiagnosis().isDisplayed(); }
    public boolean isNavVisible()           { return isNavDiagnoseVisible(); }

    public void clickNavLink(String linkText) {
        switch (linkText.toLowerCase()) {
            case "learn":   clickNavLearn();    break;
            case "faq":     clickNavFaq();      break;
            case "about":   clickNavAbout();    break;
            default:        clickNavDiagnose(); break;
        }
        log.info("Clicked nav link: {}", linkText);
    }
}
