package com.netsymptom.pages.components;

import com.netsymptom.utils.WaitUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Handles both #privacy-modal and #terms-modal overlays.
 * Both share the same open/close pattern (.modal-overlay.open).
 */
public class ModalComponent {

    private static final Logger log = LogManager.getLogger(ModalComponent.class);

    // Privacy modal
    private final By privacyModal       = By.id("privacy-modal");
    private final By privacyCloseBtn    = By.cssSelector("#privacy-modal button[onclick*='closeModal']");
    private final By privacyTitle       = By.cssSelector("#privacy-modal h2");
    private final By privacyContent     = By.cssSelector("#privacy-modal .p-6");

    // Terms modal
    private final By termsModal         = By.id("terms-modal");
    private final By termsCloseBtn      = By.cssSelector("#terms-modal button[onclick*='closeModal']");
    private final By termsTitle         = By.cssSelector("#terms-modal h2");
    private final By termsContent       = By.cssSelector("#terms-modal .p-6");

    private final WebDriver driver;

    public ModalComponent(WebDriver driver) {
        this.driver = driver;
    }

    // ── Privacy ──────────────────────────────────────────────────────────────

    public boolean isPrivacyModalOpen() {
        WebElement modal = driver.findElement(privacyModal);
        String cls = modal.getAttribute("class");
        return cls != null && cls.contains("open");
    }

    public String getPrivacyTitle() {
        return WaitUtils.waitForVisible(privacyTitle).getText();
    }

    public String getPrivacyContent() {
        return WaitUtils.waitForVisible(privacyContent).getText();
    }

    public void closePrivacyModal() {
        WaitUtils.waitForClickable(privacyCloseBtn).click();
        log.info("Closed Privacy Policy modal");
    }

    // ── Terms ─────────────────────────────────────────────────────────────────

    public boolean isTermsModalOpen() {
        WebElement modal = driver.findElement(termsModal);
        String cls = modal.getAttribute("class");
        return cls != null && cls.contains("open");
    }

    public String getTermsTitle() {
        return WaitUtils.waitForVisible(termsTitle).getText();
    }

    public String getTermsContent() {
        return WaitUtils.waitForVisible(termsContent).getText();
    }

    public void closeTermsModal() {
        WaitUtils.waitForClickable(termsCloseBtn).click();
        log.info("Closed Terms of Service modal");
    }

    // ── Generic ───────────────────────────────────────────────────────────────

    /** Close any open modal by pressing Escape. */
    public void closeWithEscape() {
        driver.findElement(By.tagName("body"))
              .sendKeys(org.openqa.selenium.Keys.ESCAPE);
        WaitUtils.sleep(300);
        log.info("Sent Escape key to close modal");
    }

    // ── Aliases used by step definitions ─────────────────────────────────────

    public boolean isPrivacyModalVisible()   { return isPrivacyModalOpen(); }
    public boolean isTermsModalVisible()     { return isTermsModalOpen(); }
    public String  getPrivacyModalContent()  { return getPrivacyContent(); }
    public String  getTermsModalContent()    { return getTermsContent(); }
    public void    pressEscapeKey()          { closeWithEscape(); }

    /** Verify Privacy Policy explicitly states client-side processing. */
    public boolean privacyMentionsClientSide() {
        String content = getPrivacyContent();
        return content.contains("client-side") || content.contains("browser");
    }

    /** Verify Terms explicitly state results are probabilistic. */
    public boolean termsMentionProbabilistic() {
        String content = getTermsContent();
        return content.contains("probabilistic") || content.contains("probabilistic estimates");
    }
}
