@accessibility
Feature: Responsiveness and Accessibility
  Layout, semantic HTML, and interaction accessibility checks

  Background:
    Given I open the NetSymptom website

  @accessibility
  Scenario: Hero section headline is visible on load
    Then the hero section headline should be visible

  @accessibility
  Scenario: FAQ section heading contains expected text
    Then the FAQ heading should contain "FAQ"

  @accessibility
  Scenario: Only one FAQ answer is shown at a time (accordion)
    When I click FAQ item 1
    Then only one FAQ answer should be visible at a time
    When I click FAQ item 3
    Then only one FAQ answer should be visible at a time

  @accessibility
  Scenario: About section is reachable via page scroll
    Then the about section should be visible

  @accessibility
  Scenario: Knowledge base articles have headings
    Then the knowledge base section should be visible
    And the knowledge base should contain 4 articles

  @accessibility
  Scenario: Diagnostic form labels are present
    Then the diagnostic form should be visible

  @accessibility
  Scenario: Results reset flow is accessible
    When I fill the form with symptom "video-buffering", download 25 Mbps, upload 5 Mbps, ping 80 ms
    And I click the Execute Diagnostic Analysis button
    Then the results panel should be visible
    When I click Run New Diagnosis
    Then the diagnostic form should be shown again

  @accessibility
  Scenario: Privacy Policy modal can be closed with close button
    When I click the footer Privacy Policy link
    Then the Privacy Policy modal should open
    When I close the Privacy Policy modal
    Then the Privacy Policy modal should be closed

  @accessibility
  Scenario: Terms modal can be closed with close button
    When I click the footer Terms of Service link
    Then the Terms of Service modal should open
    When I close the Terms of Service modal
    Then the Terms of Service modal should be closed
