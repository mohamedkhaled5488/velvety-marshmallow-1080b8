package com.netsymptom.stepdefs;

import com.netsymptom.pages.HomePage;
import com.netsymptom.utils.DriverManager;
import io.cucumber.java.en.*;
import org.testng.Assert;

public class HeaderSteps {

    private final HomePage page;

    public HeaderSteps() {
        this.page = new HomePage(DriverManager.getDriver());
    }

    @Given("I open the NetSymptom website")
    public void iOpenTheNetSymptomWebsite() {
        page.open();
    }

    @Then("the page title should contain {string}")
    public void thePageTitleShouldContain(String expectedTitle) {
        Assert.assertTrue(page.getPageTitle().contains(expectedTitle),
            "Page title '" + page.getPageTitle() + "' does not contain '" + expectedTitle + "'");
    }

    @Then("the site logo should be visible")
    public void theSiteLogoShouldBeVisible() {
        Assert.assertTrue(page.header.isLogoVisible(), "Logo is not visible");
    }

    @Then("the header navigation links should be visible")
    public void theHeaderNavigationLinksShouldBeVisible() {
        Assert.assertTrue(page.header.isNavVisible(), "Navigation links are not visible");
    }

    @When("I click the {string} navigation link")
    public void iClickTheNavigationLink(String linkText) {
        page.header.clickNavLink(linkText);
    }

    @Then("the header should contain {string}")
    public void theHeaderShouldContain(String text) {
        Assert.assertTrue(page.header.getLogoText().contains(text),
            "Header does not contain: " + text);
    }

    @Then("the page should have Google Analytics tracking")
    public void thePageShouldHaveGoogleAnalyticsTracking() {
        Assert.assertTrue(page.hasGa4Script(), "GA4 script (G-Q4YPBPM31W) not found on page");
    }

    @Then("the page should have AdSense meta tag")
    public void thePageShouldHaveAdSenseMetaTag() {
        Assert.assertTrue(page.hasAdSenseMeta(), "AdSense publisher ID ca-pub-4634260205367551 not found");
    }

    @Then("the page should have JSON-LD structured data")
    public void thePageShouldHaveJsonLdStructuredData() {
        Assert.assertTrue(page.hasJsonLdSchema(), "JSON-LD schema markup not found on page");
    }
}
