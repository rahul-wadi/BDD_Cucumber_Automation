
Feature: PetStore API Testing

  Scenario: Create a pet and validate pet details
    Given I create a pet using the POST API
    When I retrieve the pet details using the GET API
    Then the status code should be 200
    And the content type should be "application/json"
    And the category name should be "dog"
    And the pet name should be "snoopie"
    And the pet status should be "pending"