package com.netsymptom.pages.sections;

import com.netsymptom.utils.JavaScriptUtils;
import com.netsymptom.utils.WaitUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Step 3 — Diagnostic Report: #results-wrap
 * Real DOM IDs:
 *   #results-wrap, #score-number, #score-label, #score-ring,
 *   #results-badge, #bars-container, #network-path,
 *   #paradox-text, #fix-list, #walkthroughs, #affiliate-card,
 *   #affiliate-title, #affiliate-desc, #affiliate-link
 */
public class AnalysisResultsSection {

    private static final Logger log = LogManager.getLogger(AnalysisResultsSection.class);

    private final By resultsWrap       = By.id("results-wrap");
    private final By scoreNumber       = By.id("score-number");
    private final By scoreLabel        = By.id("score-label");
    private final By scoreRing         = By.id("score-ring");
    private final By resultsBadge      = By.id("results-badge");
    private final By barsContainer     = By.id("bars-container");
    private final By networkPath       = By.id("network-path");
    private final By paradoxText       = By.id("paradox-text");
    private final By fixList           = By.id("fix-list");
    private final By walkthroughs      = By.id("walkthroughs");
    private final By affiliateCard     = By.id("affiliate-card");
    private final By affiliateTitle    = By.id("affiliate-title");
    private final By affiliateDesc     = By.id("affiliate-desc");
    private final By affiliateLink     = By.id("affiliate-link");
    private final By resetBtn          = By.cssSelector("#results-wrap button[onclick='resetDiagnostic()']");
    private final By probBars          = By.cssSelector("#bars-container .bar-inner");
    private final By probLabels        = By.cssSelector("#bars-container .font-semibold.text-slate-200");
    private final By probPercentages   = By.cssSelector("#bars-container .font-bold");
    private final By fixItems          = By.cssSelector("#fix-list .fix-item");
    private final By walkthroughAccs   = By.cssSelector("#walkthroughs .acc-toggle");
    private final By networkNodes      = By.cssSelector("#network-path .flex.flex-col");
    private final By bottleneckBadge   = By.cssSelector("#network-path .bg-rose-500\\/20");

    private final WebDriver driver;

    public AnalysisResultsSection(WebDriver driver) {
        this.driver = driver;
    }

    // ── Visibility ───────────────────────────────────────────────────────────

    public boolean isVisible() {
        return JavaScriptUtils.hasClass("results-wrap", "show");
    }

    public void waitForResults() {
        WaitUtils.waitForDiagnosticResults();
        log.info("Results panel is now visible");
    }

    // ── Health Score ─────────────────────────────────────────────────────────

    public String getScoreText() {
        return WaitUtils.waitForVisible(scoreNumber).getText();
    }

    public int getScoreValue() {
        String text = getScoreText();
        try { return Integer.parseInt(text.trim()); }
        catch (NumberFormatException e) { return -1; }
    }

    public String getScoreLabel() {
        return WaitUtils.waitForVisible(scoreLabel).getText();
    }

    public String getScoreRingColor() {
        return WaitUtils.waitForVisible(scoreRing).getAttribute("stroke");
    }

    public boolean isScoreInValidRange() {
        int score = getScoreValue();
        return score >= 0 && score <= 100;
    }

    // ── Results Badge ────────────────────────────────────────────────────────

    public String getResultsBadgeText() {
        return WaitUtils.waitForVisible(resultsBadge).getText();
    }

    // ── Probability Bars ─────────────────────────────────────────────────────

    public int getProbabilityBarCount() {
        return driver.findElements(probBars).size();
    }

    public List<WebElement> getProbabilityLabels() {
        return driver.findElements(probLabels);
    }

    public List<WebElement> getProbabilityPercentages() {
        return driver.findElements(probPercentages);
    }

    public boolean allBarsHaveAnimateClass() {
        List<WebElement> bars = driver.findElements(probBars);
        return bars.stream().allMatch(b -> {
            String cls = b.getAttribute("class");
            return cls != null && cls.contains("animate");
        });
    }

    // ── Network Path ─────────────────────────────────────────────────────────

    public boolean isNetworkPathVisible() {
        return WaitUtils.waitForVisible(networkPath).isDisplayed();
    }

    public int getNetworkNodeCount() {
        return driver.findElements(networkNodes).size();
    }

    public boolean isBottleneckHighlighted() {
        return !driver.findElements(bottleneckBadge).isEmpty();
    }

    // ── Paradox Translator ───────────────────────────────────────────────────

    public String getParadoxText() {
        return WaitUtils.waitForVisible(paradoxText).getText();
    }

    public boolean isParadoxTextNonEmpty() {
        String text = getParadoxText();
        return text != null && !text.trim().isEmpty();
    }

    // ── Fix Checklist ────────────────────────────────────────────────────────

    public int getFixItemCount() {
        return driver.findElements(fixItems).size();
    }

    public boolean areFixItemsVisible() {
        List<WebElement> items = driver.findElements(fixItems);
        return items.stream().anyMatch(el -> {
            String cls = el.getAttribute("class");
            return cls != null && cls.contains("visible");
        });
    }

    // ── Walkthroughs ─────────────────────────────────────────────────────────

    public int getWalkthroughCount() {
        return driver.findElements(walkthroughAccs).size();
    }

    public void openWalkthrough(int index) {
        List<WebElement> toggles = driver.findElements(walkthroughAccs);
        if (index < toggles.size()) {
            toggles.get(index).click();
            WaitUtils.sleep(500);
            log.info("Opened walkthrough at index {}", index);
        }
    }

    public boolean isWalkthroughOpen(int index) {
        List<WebElement> bodies = driver.findElements(
                By.cssSelector("#walkthroughs .acc-body"));
        if (index >= bodies.size()) return false;
        String cls = bodies.get(index).getAttribute("class");
        return cls != null && cls.contains("open");
    }

    // ── Affiliate Card ───────────────────────────────────────────────────────

    public boolean isAffiliateCardVisible() {
        WebElement card = driver.findElement(affiliateCard);
        String display = card.getCssValue("display");
        return !"none".equals(display);
    }

    public String getAffiliateTitleText() {
        return WaitUtils.waitForVisible(affiliateTitle).getText();
    }

    public String getAffiliateDescText() {
        return WaitUtils.waitForVisible(affiliateDesc).getText();
    }

    public String getAffiliateLinkHref() {
        return WaitUtils.waitForVisible(affiliateLink).getAttribute("href");
    }

    public String getAffiliateLinkTarget() {
        return WaitUtils.waitForVisible(affiliateLink).getAttribute("target");
    }

    // ── Reset ────────────────────────────────────────────────────────────────

    public void clickRunNewDiagnosis() {
        WaitUtils.waitForClickable(resetBtn).click();
        log.info("Clicked 'Run New Diagnosis'");
    }
}
