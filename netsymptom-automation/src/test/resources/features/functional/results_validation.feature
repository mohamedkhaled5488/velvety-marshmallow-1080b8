@functional
Feature: Diagnostic Results Validation
  Verify that results panel components render correctly after analysis

  Background:
    Given I am on the NetSymptom website

  @functional
  Scenario: Results panel shows health score in valid range
    When I fill the form with symptom "video-buffering", download 25 Mbps, upload 5 Mbps, ping 80 ms
    And I click the Execute Diagnostic Analysis button
    Then the results panel should be visible
    And the health score should be between 0 and 100

  @functional
  Scenario: Results panel shows score label
    When I fill the form with symptom "gaming-lag", download 100 Mbps, upload 20 Mbps, ping 150 ms
    And I click the Execute Diagnostic Analysis button
    Then the results panel should be visible
    And the score label should not be empty

  @functional
  Scenario: Probability bars are rendered
    When I fill the form with symptom "slow-load", download 5 Mbps, upload 2 Mbps, ping 50 ms
    And I click the Execute Diagnostic Analysis button
    Then the results panel should be visible
    And the probability bars should be visible
    And there should be 3 probability bars

  @functional
  Scenario: Network path diagram is visible with multiple nodes
    When I fill the form with symptom "voip-freeze", download 10 Mbps, upload 5 Mbps, ping 200 ms
    And I click the Execute Diagnostic Analysis button
    Then the results panel should be visible
    And the network path diagram should be visible
    And the network path should have at least 3 nodes

  @functional
  Scenario: Bottleneck node is highlighted in network path
    When I fill the form with symptom "gaming-lag", download 100 Mbps, upload 20 Mbps, ping 150 ms
    And I click the Execute Diagnostic Analysis button
    Then the results panel should be visible
    And a bottleneck node should be highlighted

  @functional
  Scenario: Paradox translator displays explanation text
    When I fill the form with symptom "stream-stutter", download 50 Mbps, upload 10 Mbps, ping 30 ms
    And I click the Execute Diagnostic Analysis button
    Then the results panel should be visible
    And the paradox translator text should not be empty

  @functional
  Scenario: Fix checklist contains actionable items
    When I fill the form with symptom "video-buffering", download 25 Mbps, upload 5 Mbps, ping 80 ms
    And I click the Execute Diagnostic Analysis button
    Then the results panel should be visible
    And the fix checklist should contain at least 2 items

  @functional
  Scenario: Walkthrough section has expandable items
    When I fill the form with symptom "gaming-lag", download 100 Mbps, upload 20 Mbps, ping 150 ms
    And I click the Execute Diagnostic Analysis button
    Then the results panel should be visible
    And the walkthrough section should contain at least 1 items
    When I open walkthrough item 1
    Then walkthrough item 1 should be expanded

  @functional
  Scenario: Affiliate card is visible in results
    When I fill the form with symptom "slow-load", download 5 Mbps, upload 2 Mbps, ping 50 ms
    And I click the Execute Diagnostic Analysis button
    Then the results panel should be visible
    And the affiliate card should be visible

  @functional
  Scenario: Results badge shows classification text
    When I fill the form with symptom "voip-freeze", download 10 Mbps, upload 5 Mbps, ping 200 ms
    And I click the Execute Diagnostic Analysis button
    Then the results panel should be visible
    And the results badge should not be empty

  @functional
  Scenario: Reset returns to form view
    When I fill the form with symptom "gaming-lag", download 100 Mbps, upload 20 Mbps, ping 150 ms
    And I click the Execute Diagnostic Analysis button
    Then the results panel should be visible
    When I click Run New Diagnosis
    Then the results panel should be hidden
    And the diagnostic form should be shown again

  @functional
  Scenario: High ping gaming scenario yields low score
    When I fill the form with symptom "gaming-lag", download 5 Mbps, upload 1 Mbps, ping 300 ms
    And I click the Execute Diagnostic Analysis button
    Then the results panel should be visible
    And the health score should be between 0 and 45

  @functional
  Scenario: Good speeds video buffering yields higher score
    When I fill the form with symptom "video-buffering", download 200 Mbps, upload 50 Mbps, ping 10 ms
    And I click the Execute Diagnostic Analysis button
    Then the results panel should be visible
    And the health score should be between 55 and 100
