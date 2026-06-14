package com.netsymptom.pages.components;

import com.netsymptom.utils.WaitUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Represents the site footer.
 * Contains brand text, quick nav links, and Privacy/Terms modal triggers.
 */
public class FooterComponent {

    private static final Logger log = LogManager.getLogger(FooterComponent.class);

    private final By footer              = By.cssSelector("footer");
    private final By brandName           = By.cssSelector("footer span.font-semibold");
    private final By privacyButton       = By.cssSelector("footer button[onclick*='privacy-modal']");
    private final By termsButton         = By.cssSelector("footer button[onclick*='terms-modal']");
    private final By footerNavDiagnose   = By.cssSelector("footer a[href='#diagnose/youtube-buffering']");
    private final By footerNavGamingLag  = By.cssSelector("footer a[href='#diagnose/gaming-lag']");
    private final By footerNavZoom       = By.cssSelector("footer a[href='#diagnose/zoom-freezing']");
    private final By copyrightText       = By.cssSelector("footer p.text-xs.text-slate-600");

    private final WebDriver driver;

    public FooterComponent(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getFooter()       { return WaitUtils.scrollAndWait(footer); }
    public String     getBrandText()    { return WaitUtils.waitForVisible(brandName).getText(); }
    public String     getCopyrightText(){ return WaitUtils.scrollAndWait(copyrightText).getText(); }

    public void clickPrivacyPolicy() {
        WaitUtils.scrollAndWait(privacyButton).click();
        log.info("Clicked footer: Privacy Policy");
    }

    public void clickTermsOfService() {
        WaitUtils.scrollAndWait(termsButton).click();
        log.info("Clicked footer: Terms of Service");
    }

    public void clickYouTubeBufferingLink() {
        WaitUtils.scrollAndWait(footerNavDiagnose).click();
        log.info("Clicked footer deep-link: YouTube Buffering");
    }

    public void clickGamingLagLink() {
        WaitUtils.scrollAndWait(footerNavGamingLag).click();
        log.info("Clicked footer deep-link: Gaming Lag");
    }

    // ── Aliases used by step definitions ─────────────────────────────────────

    public void clickPrivacyPolicyLink()  { clickPrivacyPolicy(); }
    public void clickTermsOfServiceLink() { clickTermsOfService(); }

    public boolean isFooterVisible() {
        return WaitUtils.waitForVisible(footer).isDisplayed();
    }

    public boolean isCopyrightPresent() {
        String text = getCopyrightText();
        return text != null && text.contains("NetSymptom");
    }
}
