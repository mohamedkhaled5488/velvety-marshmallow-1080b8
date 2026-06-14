@smoke
Feature: NetSymptom Smoke Tests
  Core sanity checks — site loads, form exists, diagnosis runs, results appear

  Background:
    Given I open the NetSymptom website

  @smoke
  Scenario: Page loads with correct title
    Then the page title should contain "NetSymptom"

  @smoke
  Scenario: Logo and navigation are visible
    Then the site logo should be visible
    And the header navigation links should be visible

  @smoke
  Scenario: Diagnostic form is visible on load
    Then the diagnostic form should be visible
    And the symptom dropdown should have 6 options

  @smoke
  Scenario: Full diagnostic workflow with video buffering
    When I fill the form with symptom "video-buffering", download 25 Mbps, upload 5 Mbps, ping 80 ms
    And I click the Execute Diagnostic Analysis button
    Then the results panel should be visible
    And the health score should be a valid number

  @smoke
  Scenario: FAQ section is present
    Then the FAQ section should be visible
    And the FAQ section should have 6 questions

  @smoke
  Scenario: Knowledge base articles are present
    Then the knowledge base section should be visible
    And the knowledge base should contain 4 articles

  @smoke
  Scenario: Page has tracking and schema
    Then the page should have Google Analytics tracking
    And the page should have AdSense meta tag
    And the page should have JSON-LD structured data
