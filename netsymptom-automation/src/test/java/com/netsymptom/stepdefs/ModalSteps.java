package com.netsymptom.stepdefs;

import com.netsymptom.pages.HomePage;
import com.netsymptom.utils.DriverManager;
import io.cucumber.java.en.*;
import org.testng.Assert;

public class ModalSteps {

    private final HomePage page;

    public ModalSteps() {
        this.page = new HomePage(DriverManager.getDriver());
    }

    @Then("the Privacy Policy modal should open")
    public void thePrivacyPolicyModalShouldOpen() {
        Assert.assertTrue(page.modal.isPrivacyModalVisible(),
            "Privacy Policy modal did not open");
    }

    @Then("the Terms of Service modal should open")
    public void theTermsOfServiceModalShouldOpen() {
        Assert.assertTrue(page.modal.isTermsModalVisible(),
            "Terms of Service modal did not open");
    }

    @When("I close the Privacy Policy modal")
    public void iCloseThePrivacyPolicyModal() {
        page.modal.closePrivacyModal();
    }

    @When("I close the Terms of Service modal")
    public void iCloseTheTermsOfServiceModal() {
        page.modal.closeTermsModal();
    }

    @Then("the Privacy Policy modal should be closed")
    public void thePrivacyPolicyModalShouldBeClosed() {
        Assert.assertFalse(page.modal.isPrivacyModalVisible(),
            "Privacy Policy modal is still open after closing");
    }

    @Then("the Terms of Service modal should be closed")
    public void theTermsOfServiceModalShouldBeClosed() {
        Assert.assertFalse(page.modal.isTermsModalVisible(),
            "Terms of Service modal is still open after closing");
    }

    @Then("the Privacy Policy modal should contain {string}")
    public void thePrivacyPolicyModalShouldContain(String text) {
        String content = page.modal.getPrivacyModalContent();
        Assert.assertTrue(content.contains(text),
            "Privacy Policy modal does not contain: " + text);
    }

    @Then("the Terms of Service modal should contain {string}")
    public void theTermsOfServiceModalShouldContain(String text) {
        String content = page.modal.getTermsModalContent();
        Assert.assertTrue(content.contains(text),
            "Terms of Service modal does not contain: " + text);
    }

    @When("I press Escape")
    public void iPressEscape() {
        page.modal.pressEscapeKey();
    }

    @Then("any open modal should close")
    public void anyOpenModalShouldClose() {
        Assert.assertFalse(page.modal.isPrivacyModalVisible() || page.modal.isTermsModalVisible(),
            "A modal is still open after pressing Escape");
    }
}
