
Feature: Jira Defect Management

  Scenario: Create, Update, Search, Attach, and Delete Jira Defect
    Given I create a new defect in Jira
    When I update the created defect
    And I search for the defect by ID
    And I add an attachment to the defect
    And I create a defect with an attached document
    Then I delete the created defect
