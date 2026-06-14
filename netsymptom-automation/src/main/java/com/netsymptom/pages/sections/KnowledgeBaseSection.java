package com.netsymptom.pages.sections;

import com.netsymptom.utils.WaitUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Educational Knowledge Base: #learn
 * 4 articles: Bufferbloat, ISP Peering, DNS Resolution, Packet Loss.
 */
public class KnowledgeBaseSection {

    private static final Logger log = LogManager.getLogger(KnowledgeBaseSection.class);

    private final By learnSection     = By.id("learn");
    private final By sectionHeading   = By.cssSelector("#learn h2");
    private final By articles         = By.cssSelector("#learn article");
    private final By articleHeadings  = By.cssSelector("#learn article h3");
    private final By articleContent   = By.cssSelector("#learn article .space-y-4");

    private final WebDriver driver;

    public KnowledgeBaseSection(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isVisible() {
        return WaitUtils.scrollAndWait(learnSection).isDisplayed();
    }

    public String getSectionHeading() {
        return WaitUtils.scrollAndWait(sectionHeading).getText();
    }

    public int getArticleCount() {
        return driver.findElements(articles).size();
    }

    public String getArticleTitle(int index) {
        List<WebElement> headings = driver.findElements(articleHeadings);
        if (index >= headings.size()) return "";
        return headings.get(index).getText();
    }

    public String getArticleBodyText(int index) {
        List<WebElement> bodies = driver.findElements(articleContent);
        if (index >= bodies.size()) return "";
        return bodies.get(index).getText();
    }

    public boolean articleContains(int index, String keyword) {
        return getArticleBodyText(index).toLowerCase().contains(keyword.toLowerCase());
    }

    public boolean allArticlesHaveSubstantialContent() {
        List<WebElement> bodies = driver.findElements(articleContent);
        return bodies.stream().allMatch(el -> el.getText().length() > 300);
    }

    /** Verify the Bufferbloat article (index 0) mentions key technical terms. */
    public boolean bufferbloatArticleIsComplete() {
        String text = getArticleBodyText(0);
        return text.contains("buffer") && text.contains("latency") && text.contains("FQ-CoDel");
    }

    /** Verify DNS article (index 2) mentions Cloudflare and 1.1.1.1. */
    public boolean dnsArticleMentionsCloudflare() {
        String text = getArticleBodyText(2);
        return text.contains("Cloudflare") && text.contains("1.1.1.1");
    }
}
