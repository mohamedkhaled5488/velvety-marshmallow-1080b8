package com.netsymptom.pages;

import com.netsymptom.pages.components.FooterComponent;
import com.netsymptom.pages.components.HeaderComponent;
import com.netsymptom.pages.components.ModalComponent;
import com.netsymptom.pages.sections.*;
import com.netsymptom.utils.ConfigReader;
import com.netsymptom.utils.DriverManager;
import com.netsymptom.utils.WaitUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Root Page Object for NetSymptom.
 * This is a single-page application — all sections live on one page.
 * Composes all section/component objects and provides page-level actions.
 */
public class HomePage {

    private static final Logger log = LogManager.getLogger(HomePage.class);

    private final WebDriver driver;

    // ── Components ───────────────────────────────────────────────────────────
    public final HeaderComponent    header;
    public final FooterComponent    footer;
    public final ModalComponent     modal;

    // ── Sections ─────────────────────────────────────────────────────────────
    public final HeroSection          hero;
    public final SymptomInputSection  symptomForm;
    public final AnalysisResultsSection results;
    public final KnowledgeBaseSection knowledgeBase;
    public final FAQSection           faq;
    public final AboutSection         about;

    public HomePage(WebDriver driver) {
        this.driver       = driver;
        this.header       = new HeaderComponent(driver);
        this.footer       = new FooterComponent(driver);
        this.modal        = new ModalComponent(driver);
        this.hero         = new HeroSection(driver);
        this.symptomForm  = new SymptomInputSection(driver);
        this.results      = new AnalysisResultsSection(driver);
        this.knowledgeBase= new KnowledgeBaseSection(driver);
        this.faq          = new FAQSection(driver);
        this.about        = new AboutSection(driver);
    }

    // ── Navigation ───────────────────────────────────────────────────────────

    public HomePage open() {
        String url = ConfigReader.getBaseUrl();
        driver.get(url);
        WaitUtils.waitForPageLoad();
        log.info("Opened: {}", url);
        return this;
    }

    public HomePage openDeepLink(String slug) {
        String url = ConfigReader.getBaseUrl() + "#diagnose/" + slug;
        driver.get(url);
        WaitUtils.waitForPageLoad();
        log.info("Opened deep link: {}", url);
        return this;
    }

    // ── Page-level assertions ────────────────────────────────────────────────

    public String getPageTitle() {
        return driver.getTitle();
    }

    public String getMetaDescription() {
        return driver.findElement(By.cssSelector("meta[name='description']"))
                     .getAttribute("content");
    }

    public boolean hasGa4Script() {
        return driver.getPageSource().contains("G-Q4YPBPM31W");
    }

    public boolean hasAdSenseMeta() {
        return driver.getPageSource().contains("ca-pub-4634260205367551");
    }

    public boolean hasJsonLdSchema() {
        return driver.getPageSource().contains("application/ld+json");
    }

    // ── Full diagnostic workflow ──────────────────────────────────────────────

    /**
     * Convenience: fill form + click analyze + wait for results.
     * Accounts for the 10-second engine run.
     */
    public void runFullDiagnosis(String symptom, int download, int upload, int ping) {
        symptomForm.fillForm(symptom, download, upload, ping);
        symptomForm.clickAnalyze();
        results.waitForResults();
    }

    // ── Step indicator checks ─────────────────────────────────────────────────

    public boolean isStepDotActive(int stepNumber) {
        WebDriver d = DriverManager.getDriver();
        String cls = d.findElement(By.id("step-dot-" + stepNumber)).getAttribute("class");
        return cls != null && (cls.contains("indigo") || cls.contains("emerald"));
    }

    public boolean isConnectorAnimated(String connId) {
        String style = driver.findElement(By.id(connId)).getAttribute("style");
        return style != null && style.contains("gradient");
    }
}
