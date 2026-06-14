package com.netsymptom.stepdefs;

import com.netsymptom.pages.HomePage;
import com.netsymptom.utils.DriverManager;
import io.cucumber.java.en.*;
import org.testng.Assert;

public class DiagnosticFormSteps {

    private final HomePage page;

    public DiagnosticFormSteps() {
        this.page = new HomePage(DriverManager.getDriver());
    }

    @Given("I am on the NetSymptom website")
    public void iAmOnTheNetSymptomWebsite() {
        page.open();
    }

    @When("I select {string} as the network symptom")
    public void iSelectAsTheNetworkSymptom(String symptom) {
        page.symptomForm.selectSymptom(symptom);
    }

    @When("I set the download speed to {int} Mbps")
    public void iSetTheDownloadSpeedToMbps(int mbps) {
        page.symptomForm.setDownloadSpeed(mbps);
    }

    @When("I set the upload speed to {int} Mbps")
    public void iSetTheUploadSpeedToMbps(int mbps) {
        page.symptomForm.setUploadSpeed(mbps);
    }

    @When("I set the ping to {int} ms")
    public void iSetThePingToMs(int ms) {
        page.symptomForm.setPing(ms);
    }

    @When("I click the Execute Diagnostic Analysis button")
    public void iClickTheExecuteDiagnosticAnalysisButton() {
        page.symptomForm.clickAnalyze();
    }

    @When("I fill the form with symptom {string}, download {int} Mbps, upload {int} Mbps, ping {int} ms")
    public void iFillTheFormWith(String symptom, int download, int upload, int ping) {
        page.symptomForm.fillForm(symptom, download, upload, ping);
    }

    @Then("the diagnostic form should be visible")
    public void theDiagnosticFormShouldBeVisible() {
        Assert.assertTrue(page.symptomForm.isFormVisible(), "Diagnostic form is not visible");
    }

    @Then("the symptom dropdown should have {int} options")
    public void theSymptomDropdownShouldHaveOptions(int expectedCount) {
        int actual = page.symptomForm.getSymptomOptionCount();
        Assert.assertEquals(actual, expectedCount,
            "Expected " + expectedCount + " symptom options but found " + actual);
    }

    @Then("the analyze button should be disabled when no symptom is selected")
    public void theAnalyzeButtonShouldBeDisabledWhenNoSymptomIsSelected() {
        Assert.assertFalse(page.symptomForm.isAnalyzeButtonEnabled(),
            "Analyze button should be disabled when no symptom is selected");
    }

    @Then("the analyze button should be enabled")
    public void theAnalyzeButtonShouldBeEnabled() {
        Assert.assertTrue(page.symptomForm.isAnalyzeButtonEnabled(),
            "Analyze button should be enabled");
    }

    @Then("the analyze button should show a spinner during analysis")
    public void theAnalyzeButtonShouldShowASpinnerDuringAnalysis() {
        Assert.assertTrue(page.symptomForm.isSpinnerVisible(),
            "Spinner not visible during analysis");
    }

    @Then("the download display should show {int} Mbps")
    public void theDownloadDisplayShouldShow(int expected) {
        int actual = page.symptomForm.getDownloadDisplayInt();
        Assert.assertEquals(actual, expected,
            "Download display shows " + actual + " but expected " + expected);
    }

    @Then("the upload display should show {int} Mbps")
    public void theUploadDisplayShouldShow(int expected) {
        int actual = page.symptomForm.getUploadDisplayInt();
        Assert.assertEquals(actual, expected,
            "Upload display shows " + actual + " but expected " + expected);
    }

    @Then("the ping display should show {int} ms")
    public void thePingDisplayShouldShow(int expected) {
        int actual = page.symptomForm.getPingDisplayInt();
        Assert.assertEquals(actual, expected,
            "Ping display shows " + actual + " but expected " + expected);
    }

    @Then("the fast.com link should open in a new tab")
    public void theFastComLinkShouldOpenInANewTab() {
        Assert.assertEquals(page.symptomForm.getFastComLinkTarget(), "_blank",
            "fast.com link does not open in a new tab");
    }

    @Then("the fast.com link should have rel noopener noreferrer")
    public void theFastComLinkShouldHaveRelNoopenerNoreferrer() {
        String rel = page.symptomForm.getFastComLinkRel();
        Assert.assertTrue(rel.contains("noopener") && rel.contains("noreferrer"),
            "fast.com link rel attribute is: " + rel);
    }

    @Then("the error message should be visible")
    public void theErrorMessageShouldBeVisible() {
        Assert.assertTrue(page.symptomForm.isErrorVisible(),
            "Form error message is not visible");
    }
}
