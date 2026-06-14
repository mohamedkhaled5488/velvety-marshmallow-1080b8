@regression
Feature: Negative and Edge Case Tests
  Validate form validation, boundary values, and error handling

  Background:
    Given I am on the NetSymptom website

  @regression
  Scenario: Clicking analyze without selecting symptom shows no results
    When I click the Execute Diagnostic Analysis button
    Then the results panel should be hidden

  @regression
  Scenario: Minimum slider values produce valid results
    When I fill the form with symptom "gaming-lag", download 1 Mbps, upload 1 Mbps, ping 1 ms
    And I click the Execute Diagnostic Analysis button
    Then the results panel should be visible
    And the health score should be a valid number

  @regression
  Scenario: Maximum slider values produce valid results
    When I fill the form with symptom "stream-stutter", download 1000 Mbps, upload 500 Mbps, ping 1 ms
    And I click the Execute Diagnostic Analysis button
    Then the results panel should be visible
    And the health score should be a valid number

  @regression
  Scenario: Maximum ping produces valid results
    When I fill the form with symptom "voip-freeze", download 10 Mbps, upload 5 Mbps, ping 500 ms
    And I click the Execute Diagnostic Analysis button
    Then the results panel should be visible
    And the health score should be a valid number

  @regression
  Scenario: Results reset properly before running second diagnosis
    When I fill the form with symptom "gaming-lag", download 100 Mbps, upload 20 Mbps, ping 150 ms
    And I click the Execute Diagnostic Analysis button
    Then the results panel should be visible
    When I click Run New Diagnosis
    And I fill the form with symptom "video-buffering", download 25 Mbps, upload 5 Mbps, ping 80 ms
    And I click the Execute Diagnostic Analysis button
    Then the results panel should be visible
    And the health score should be a valid number

  @regression
  Scenario: Very high ping gaming yields poor score
    When I fill the form with symptom "gaming-lag", download 1 Mbps, upload 1 Mbps, ping 500 ms
    And I click the Execute Diagnostic Analysis button
    Then the results panel should be visible
    And the health score should be between 0 and 40

  @regression
  Scenario: FAQ items toggle correctly on repeated clicks
    When I click FAQ item 1
    Then FAQ item 1 answer should be expanded
    When I click FAQ item 1
    Then FAQ item 1 answer should be collapsed

  @regression
  Scenario: Deep link with unknown slug does not crash
    Given I navigate to the deep link "unknown-symptom"
    Then the diagnostic form should be visible

  @regression
  Scenario: Affiliate card link does not break with any symptom
    When I fill the form with symptom "slow-load", download 5 Mbps, upload 2 Mbps, ping 50 ms
    And I click the Execute Diagnostic Analysis button
    Then the results panel should be visible
    And the affiliate card should be visible
