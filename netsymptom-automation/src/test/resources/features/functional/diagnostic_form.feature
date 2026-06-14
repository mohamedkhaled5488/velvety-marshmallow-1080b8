@functional
Feature: Diagnostic Form Interaction
  Validate symptom form inputs, slider synchronization, and button states

  Background:
    Given I am on the NetSymptom website

  @functional
  Scenario: Analyze button is disabled when no symptom is selected
    Then the analyze button should be disabled when no symptom is selected

  @functional
  Scenario: Analyze button enables after selecting a symptom
    When I select "gaming-lag" as the network symptom
    Then the analyze button should be enabled

  @functional
  Scenario: Download slider syncs with display value
    When I select "video-buffering" as the network symptom
    And I set the download speed to 50 Mbps
    Then the download display should show 50 Mbps

  @functional
  Scenario: Upload slider syncs with display value
    When I select "video-buffering" as the network symptom
    And I set the upload speed to 20 Mbps
    Then the upload display should show 20 Mbps

  @functional
  Scenario: Ping slider syncs with display value
    When I select "gaming-lag" as the network symptom
    And I set the ping to 150 ms
    Then the ping display should show 150 ms

  @functional
  Scenario: fast.com link opens in new tab with security attributes
    Then the fast.com link should open in a new tab
    And the fast.com link should have rel noopener noreferrer

  @functional
  Scenario Outline: All symptoms can be selected and submitted
    When I fill the form with symptom "<symptom>", download <download> Mbps, upload <upload> Mbps, ping <ping> ms
    And I click the Execute Diagnostic Analysis button
    Then the results panel should be visible

    Examples:
      | symptom         | download | upload | ping |
      | video-buffering | 25       | 5      | 80   |
      | gaming-lag      | 100      | 20     | 150  |
      | voip-freeze     | 10       | 5      | 200  |
      | slow-load       | 5        | 2      | 50   |
      | stream-stutter  | 50       | 10     | 30   |

  @functional
  Scenario: Spinner appears while analysis is running
    When I fill the form with symptom "gaming-lag", download 100 Mbps, upload 20 Mbps, ping 150 ms
    And I click the Execute Diagnostic Analysis button
    Then the analyze button should show a spinner during analysis
