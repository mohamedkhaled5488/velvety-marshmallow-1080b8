package com.netsymptom.stepdefs;

import com.netsymptom.pages.HomePage;
import com.netsymptom.utils.DriverManager;
import io.cucumber.java.en.*;
import org.testng.Assert;

public class ResultsSteps {

    private final HomePage page;

    public ResultsSteps() {
        this.page = new HomePage(DriverManager.getDriver());
    }

    @Then("the results panel should be visible")
    public void theResultsPanelShouldBeVisible() {
        Assert.assertTrue(page.results.isVisible(), "Results panel is not visible");
    }

    @Then("the health score should be between {int} and {int}")
    public void theHealthScoreShouldBeBetween(int min, int max) {
        int score = page.results.getScoreValue();
        Assert.assertTrue(score >= min && score <= max,
            "Score " + score + " is not in range [" + min + ", " + max + "]");
    }

    @Then("the health score should be a valid number")
    public void theHealthScoreShouldBeAValidNumber() {
        Assert.assertTrue(page.results.isScoreInValidRange(),
            "Health score is not in valid range 0-100");
    }

    @Then("the probability bars should be visible")
    public void theProbabilityBarsShouldBeVisible() {
        Assert.assertTrue(page.results.getProbabilityBarCount() > 0,
            "No probability bars found in results");
    }

    @Then("there should be {int} probability bars")
    public void thereShouldBeProbabilityBars(int expected) {
        Assert.assertEquals(page.results.getProbabilityBarCount(), expected,
            "Expected " + expected + " probability bars");
    }

    @Then("the network path diagram should be visible")
    public void theNetworkPathDiagramShouldBeVisible() {
        Assert.assertTrue(page.results.isNetworkPathVisible(),
            "Network path diagram is not visible");
    }

    @Then("the network path should have at least {int} nodes")
    public void theNetworkPathShouldHaveAtLeastNodes(int minNodes) {
        int actual = page.results.getNetworkNodeCount();
        Assert.assertTrue(actual >= minNodes,
            "Network path has " + actual + " nodes, expected at least " + minNodes);
    }

    @Then("a bottleneck node should be highlighted")
    public void aBottleneckNodeShouldBeHighlighted() {
        Assert.assertTrue(page.results.isBottleneckHighlighted(),
            "No bottleneck node is highlighted in the network path");
    }

    @Then("the paradox translator text should not be empty")
    public void theParadoxTranslatorTextShouldNotBeEmpty() {
        Assert.assertTrue(page.results.isParadoxTextNonEmpty(),
            "Paradox translator text is empty");
    }

    @Then("the fix checklist should contain at least {int} items")
    public void theFixChecklistShouldContainAtLeastItems(int minItems) {
        int actual = page.results.getFixItemCount();
        Assert.assertTrue(actual >= minItems,
            "Fix checklist has " + actual + " items, expected at least " + minItems);
    }

    @Then("the walkthrough section should contain at least {int} items")
    public void theWalkthroughSectionShouldContainAtLeastItems(int minItems) {
        int actual = page.results.getWalkthroughCount();
        Assert.assertTrue(actual >= minItems,
            "Walkthrough section has " + actual + " items, expected at least " + minItems);
    }

    @When("I open walkthrough item {int}")
    public void iOpenWalkthroughItem(int index) {
        page.results.openWalkthrough(index - 1);
    }

    @Then("walkthrough item {int} should be expanded")
    public void walkthroughItemShouldBeExpanded(int index) {
        Assert.assertTrue(page.results.isWalkthroughOpen(index - 1),
            "Walkthrough item " + index + " is not expanded");
    }

    @Then("the affiliate card should be visible")
    public void theAffiliateCardShouldBeVisible() {
        Assert.assertTrue(page.results.isAffiliateCardVisible(),
            "Affiliate recommendation card is not visible");
    }

    @Then("the results badge should not be empty")
    public void theResultsBadgeShouldNotBeEmpty() {
        String badge = page.results.getResultsBadgeText();
        Assert.assertFalse(badge == null || badge.trim().isEmpty(),
            "Results badge text is empty");
    }

    @Then("the score label should not be empty")
    public void theScoreLabelShouldNotBeEmpty() {
        String label = page.results.getScoreLabel();
        Assert.assertFalse(label == null || label.trim().isEmpty(),
            "Score label is empty");
    }

    @When("I click Run New Diagnosis")
    public void iClickRunNewDiagnosis() {
        page.results.clickRunNewDiagnosis();
    }

    @Then("the results panel should be hidden")
    public void theResultsPanelShouldBeHidden() {
        Assert.assertFalse(page.results.isVisible(),
            "Results panel should be hidden after reset");
    }

    @Then("the diagnostic form should be shown again")
    public void theDiagnosticFormShouldBeShownAgain() {
        Assert.assertTrue(page.symptomForm.isFormVisible(),
            "Diagnostic form should be visible after reset");
    }
}
