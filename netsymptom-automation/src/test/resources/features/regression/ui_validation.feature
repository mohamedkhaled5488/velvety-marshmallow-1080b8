@regression
Feature: UI Validation and Integrity
  Visual correctness, layout integrity, and content checks

  Background:
    Given I open the NetSymptom website

  @regression
  Scenario: Page title format is correct
    Then the page title should contain "NetSymptom"

  @regression
  Scenario: Header shows brand name
    Then the header should contain "NetSymptom"

  @regression
  Scenario: JSON-LD structured data is present
    Then the page should have JSON-LD structured data

  @regression
  Scenario: GA4 tracking is present
    Then the page should have Google Analytics tracking

  @regression
  Scenario: AdSense publisher ID is present
    Then the page should have AdSense meta tag

  @regression
  Scenario: Knowledge base has 4 articles with substantial content
    Then the knowledge base section should be visible
    And the knowledge base should contain 4 articles

  @regression
  Scenario: FAQ accordion shows exactly 6 items
    Then the FAQ section should be visible
    And the FAQ section should have 6 questions

  @regression
  Scenario: All FAQ items have answer text when opened
    When I click FAQ item 1
    Then FAQ item 1 should have non-empty answer text
    When I click FAQ item 2
    Then FAQ item 2 should have non-empty answer text
    When I click FAQ item 3
    Then FAQ item 3 should have non-empty answer text

  @regression
  Scenario: FAQ icon rotates when item is opened
    When I click FAQ item 1
    Then FAQ item 1 answer should be expanded
    And the FAQ icon should be rotated when item 1 is open

  @regression
  Scenario: Privacy Policy modal content is not empty
    When I click the footer Privacy Policy link
    Then the Privacy Policy modal should contain "Privacy"

  @regression
  Scenario: Terms of Service modal content is not empty
    When I click the footer Terms of Service link
    Then the Terms of Service modal should contain "Terms"

  @regression
  Scenario: fast.com link security attributes are correct
    Then the fast.com link should open in a new tab
    And the fast.com link should have rel noopener noreferrer
