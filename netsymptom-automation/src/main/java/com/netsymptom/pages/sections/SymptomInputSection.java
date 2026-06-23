package com.netsymptom.pages.sections;

import com.netsymptom.utils.JavaScriptUtils;
import com.netsymptom.utils.WaitUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

/**
 * Step 2 — Calibration Matrix form: #step2-card / #diagnostic-form
 * Real DOM IDs:
 *   #symptom-select, #download-slider, #download-speed,
 *   #upload-slider, #upload-speed, #ping-slider, #ping-ms,
 *   #analyze-btn, #btn-text, #form-error
 */
public class SymptomInputSection {

    private static final Logger log = LogManager.getLogger(SymptomInputSection.class);

    // Symptom values as they appear in the HTML option values
    public static final String SYMPTOM_VIDEO_BUFFERING = "video-buffering";
    public static final String SYMPTOM_GAMING_LAG      = "gaming-lag";
    public static final String SYMPTOM_VOIP_FREEZE     = "voip-freeze";
    public static final String SYMPTOM_SLOW_LOAD       = "slow-load";
    public static final String SYMPTOM_STREAM_STUTTER  = "stream-stutter";

    private final By step2Card        = By.id("step2-card");
    private final By symptomSelect    = By.id("symptom-select");
    private final By downloadSlider   = By.id("download-slider");
    private final By downloadInput    = By.id("download-speed");
    private final By uploadSlider     = By.id("upload-slider");
    private final By uploadInput      = By.id("upload-speed");
    private final By pingSlider       = By.id("ping-slider");
    private final By pingInput        = By.id("ping-ms");
    private final By analyzeBtn       = By.id("analyze-btn");
    private final By btnText          = By.id("btn-text");
    private final By btnSpinner       = By.id("btn-spinner");
    private final By formError        = By.id("form-error");
    private final By dlVal            = By.id("dl-val");
    private final By ulVal            = By.id("ul-val");
    private final By pingVal          = By.id("ping-val");
    private final By pingColorLabel   = By.id("ping-color-label");
    private final By step1FastComLink = By.cssSelector("#step1-card a[href='https://fast.com']");

    private final WebDriver driver;

    public SymptomInputSection(WebDriver driver) {
        this.driver = driver;
    }

    // ── Symptom Dropdown ─────────────────────────────────────────────────────

    public void selectSymptom(String value) {
        WebElement sel = WaitUtils.waitForClickable(symptomSelect);
        new Select(sel).selectByValue(value);
        log.info("Selected symptom: {}", value);
    }

    public void selectSymptomByJs(String value) {
        WebElement sel = WaitUtils.waitForVisible(symptomSelect);
        JavaScriptUtils.selectByValue(sel, value);
        log.info("JS-selected symptom: {}", value);
    }

    public String getSelectedSymptomValue() {
        return new Select(WaitUtils.waitForVisible(symptomSelect)).getFirstSelectedOption().getAttribute("value");
    }

    public boolean isSymptomDropdownDisplayed() {
        return WaitUtils.waitForVisible(symptomSelect).isDisplayed();
    }

    public int getSymptomOptionCount() {
        return new Select(WaitUtils.waitForVisible(symptomSelect)).getOptions().size();
    }

    // ── Download Speed ───────────────────────────────────────────────────────

    public void setDownloadSpeed(int mbps) {
        WebElement input = WaitUtils.waitForVisible(downloadInput);
        JavaScriptUtils.setValue(input, String.valueOf(mbps));
        WebElement slider = WaitUtils.waitForVisible(downloadSlider);
        JavaScriptUtils.setSliderValue(slider, Math.min(mbps, 1000));
        log.info("Set download speed: {} Mbps", mbps);
    }

    public String getDownloadDisplayValue() {
        return WaitUtils.waitForVisible(dlVal).getText();
    }

    public String getDownloadSliderMin() {
        return WaitUtils.waitForVisible(downloadSlider).getAttribute("min");
    }

    public String getDownloadSliderMax() {
        return WaitUtils.waitForVisible(downloadSlider).getAttribute("max");
    }

    // ── Upload Speed ─────────────────────────────────────────────────────────

    public void setUploadSpeed(int mbps) {
        WebElement input = WaitUtils.waitForVisible(uploadInput);
        JavaScriptUtils.setValue(input, String.valueOf(mbps));
        WebElement slider = WaitUtils.waitForVisible(uploadSlider);
        JavaScriptUtils.setSliderValue(slider, Math.min(mbps, 500));
        log.info("Set upload speed: {} Mbps", mbps);
    }

    public String getUploadDisplayValue() {
        return WaitUtils.waitForVisible(ulVal).getText();
    }

    // ── Ping / Latency ───────────────────────────────────────────────────────

    public void setPing(int ms) {
        WebElement input = WaitUtils.waitForVisible(pingInput);
        JavaScriptUtils.setValue(input, String.valueOf(ms));
        WebElement slider = WaitUtils.waitForVisible(pingSlider);
        JavaScriptUtils.setSliderValue(slider, Math.min(ms, 500));
        log.info("Set ping: {} ms", ms);
    }

    public String getPingDisplayValue() {
        return WaitUtils.waitForVisible(pingVal).getText();
    }

    public String getPingLabelColorClass() {
        return WaitUtils.waitForVisible(pingColorLabel).getAttribute("class");
    }

    // ── Analyze Button ───────────────────────────────────────────────────────

    public void clickAnalyze() {
        WebElement btn = WaitUtils.scrollAndWait(analyzeBtn);
        try {
            btn.click();
        } catch (org.openqa.selenium.ElementClickInterceptedException e) {
            JavaScriptUtils.click(btn);
        }
        log.info("Clicked 'Execute Diagnostic Analysis'");
    }

    public String getButtonText() {
        return WaitUtils.waitForVisible(btnText).getText();
    }

    public boolean isButtonDisabled() {
        return WaitUtils.waitForVisible(analyzeBtn).getAttribute("disabled") != null;
    }

    public boolean isSpinnerVisible() {
        WebElement spinner = driver.findElement(btnSpinner);
        return !spinner.getAttribute("class").contains("hidden");
    }

    // ── Error Message ────────────────────────────────────────────────────────

    public boolean isFormErrorVisible() {
        WebElement err = driver.findElement(formError);
        return !err.getAttribute("class").contains("hidden");
    }

    public String getFormErrorText() {
        return driver.findElement(formError).getText();
    }

    // ── Step 1 Fast.com link ─────────────────────────────────────────────────

    public String getFastComLinkHref() {
        return WaitUtils.waitForVisible(step1FastComLink).getAttribute("href");
    }

    public String getFastComLinkTarget() {
        return WaitUtils.waitForVisible(step1FastComLink).getAttribute("target");
    }

    public String getFastComLinkRel() {
        return WaitUtils.waitForVisible(step1FastComLink).getAttribute("rel");
    }

    // ── Aliases used by step definitions ─────────────────────────────────────

    public boolean isFormVisible()           { return isSymptomDropdownDisplayed(); }
    public boolean isAnalyzeButtonEnabled()  { return !isButtonDisabled(); }
    public boolean isErrorVisible()          { return isFormErrorVisible(); }

    public int getDownloadDisplayInt() {
        try { return Integer.parseInt(getDownloadDisplayValue().trim()); }
        catch (NumberFormatException e) { return -1; }
    }

    public int getUploadDisplayInt() {
        try { return Integer.parseInt(getUploadDisplayValue().trim()); }
        catch (NumberFormatException e) { return -1; }
    }

    public int getPingDisplayInt() {
        try { return Integer.parseInt(getPingDisplayValue().trim()); }
        catch (NumberFormatException e) { return -1; }
    }

    // ── Convenience: fill full form ──────────────────────────────────────────

    public void fillForm(String symptom, int downloadMbps, int uploadMbps, int pingMs) {
        selectSymptom(symptom);
        setDownloadSpeed(downloadMbps);
        setUploadSpeed(uploadMbps);
        setPing(pingMs);
        log.info("Form filled — symptom:{} dl:{} ul:{} ping:{}", symptom, downloadMbps, uploadMbps, pingMs);
    }
}
