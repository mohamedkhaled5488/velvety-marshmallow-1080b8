@functional
Feature: Navigation and Deep Links
  Verify hash-based routing, modal navigation, and footer links

  Background:
    Given I am on the NetSymptom website

  @functional
  Scenario: Privacy Policy modal opens from footer
    When I click the footer Privacy Policy link
    Then the Privacy Policy modal should open

  @functional
  Scenario: Privacy Policy modal can be closed
    When I click the footer Privacy Policy link
    Then the Privacy Policy modal should open
    When I close the Privacy Policy modal
    Then the Privacy Policy modal should be closed

  @functional
  Scenario: Terms of Service modal opens from footer
    When I click the footer Terms of Service link
    Then the Terms of Service modal should open

  @functional
  Scenario: Terms of Service modal can be closed
    When I click the footer Terms of Service link
    Then the Terms of Service modal should open
    When I close the Terms of Service modal
    Then the Terms of Service modal should be closed

  @functional
  Scenario: Privacy Policy modal contains expected content
    When I click the footer Privacy Policy link
    Then the Privacy Policy modal should contain "Privacy"

  @functional
  Scenario: Terms of Service modal contains expected content
    When I click the footer Terms of Service link
    Then the Terms of Service modal should contain "Terms"

  @functional
  Scenario: Footer shows copyright text
    Then the footer should contain copyright text

  @functional
  Scenario: Escape key closes open modal
    When I click the footer Privacy Policy link
    Then the Privacy Policy modal should open
    When I press Escape
    Then any open modal should close

  @functional
  Scenario: Hero section is visible on page load
    Then the hero section headline should be visible

  @functional
  Scenario: About section is accessible
    Then the about section should be visible

  @functional
  Scenario Outline: Deep links auto-run diagnosis for known slugs
    Given I navigate to the deep link "<slug>"
    Then the URL hash should contain "<slug>"
    And the results should be auto-generated

    Examples:
      | slug            |
      | gaming-lag      |
      | video-buffering |
      | voip-freeze     |
      | slow-load       |
      | stream-stutter  |

  @functional
  Scenario: Deep link updates meta description
    Given I navigate to the deep link "gaming-lag"
    Then the meta description should contain "gaming"
