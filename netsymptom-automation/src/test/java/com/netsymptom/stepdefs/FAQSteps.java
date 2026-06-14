package com.netsymptom.stepdefs;

import com.netsymptom.pages.HomePage;
import com.netsymptom.utils.DriverManager;
import io.cucumber.java.en.*;
import org.testng.Assert;

public class FAQSteps {

    private final HomePage page;

    public FAQSteps() {
        this.page = new HomePage(DriverManager.getDriver());
    }

    @Then("the FAQ section should be visible")
    public void theFaqSectionShouldBeVisible() {
        Assert.assertTrue(page.faq.isVisible(), "FAQ section is not visible");
    }

    @Then("the FAQ section should have {int} questions")
    public void theFaqSectionShouldHaveQuestions(int expected) {
        int actual = page.faq.getFaqCount();
        Assert.assertEquals(actual, expected,
            "Expected " + expected + " FAQ items but found " + actual);
    }

    @When("I click FAQ item {int}")
    public void iClickFaqItem(int index) {
        page.faq.clickFaqItem(index - 1);
    }

    @Then("FAQ item {int} answer should be expanded")
    public void faqItemAnswerShouldBeExpanded(int index) {
        Assert.assertTrue(page.faq.isFaqBodyOpen(index - 1),
            "FAQ item " + index + " is not expanded");
    }

    @Then("FAQ item {int} answer should be collapsed")
    public void faqItemAnswerShouldBeCollapsed(int index) {
        Assert.assertFalse(page.faq.isFaqBodyOpen(index - 1),
            "FAQ item " + index + " should be collapsed");
    }

    @Then("clicking FAQ item {int} should collapse it")
    public void clickingFaqItemShouldCollapseIt(int index) {
        page.faq.clickFaqItem(index - 1);
        Assert.assertFalse(page.faq.isFaqBodyOpen(index - 1),
            "FAQ item " + index + " should be collapsed after second click");
    }

    @Then("only one FAQ answer should be visible at a time")
    public void onlyOneFaqAnswerShouldBeVisibleAtATime() {
        Assert.assertTrue(page.faq.onlyOneFaqOpenAtATime(0),
            "More than one FAQ item is open at the same time");
    }

    @Then("the FAQ icon should be rotated when item {int} is open")
    public void theFaqIconShouldBeRotated(int index) {
        Assert.assertTrue(page.faq.isIconRotatedWhenOpen(index - 1),
            "FAQ icon is not rotated for item " + index);
    }

    @Then("the FAQ heading should contain {string}")
    public void theFaqHeadingShouldContain(String text) {
        Assert.assertTrue(page.faq.getHeadingText().contains(text),
            "FAQ heading does not contain: " + text);
    }

    @Then("FAQ item {int} should have non-empty answer text")
    public void faqItemShouldHaveNonEmptyAnswerText(int index) {
        page.faq.clickFaqItem(index - 1);
        String answer = page.faq.getFaqAnswerText(index - 1);
        Assert.assertFalse(answer == null || answer.trim().isEmpty(),
            "FAQ item " + index + " has empty answer text");
    }
}
