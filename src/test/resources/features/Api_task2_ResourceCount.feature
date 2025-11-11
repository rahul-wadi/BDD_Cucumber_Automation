Feature: Validate JSONPlaceholder Resources

  Scenario Outline: Verify resource endpoints
    Given User sets resource endpoint as "<resource>"
    When User retrieves all resources
    Then Verify response code is 200
    And Verify response contains at least 1 items

    When User gets resource with id 1
    Then Verify response code is 200
    And Verify response body contains "id"

    When User updates resource with id 1
    Then Verify response code is 200
    And Verify response body contains "id"

    When User deletes resource with id 1
    Then Verify response code is 200

    When User creates a new resource
    Then Verify response code is 201
    And Verify response body contains "id"

    Examples:
      | resource   |
      | /posts     |
      | /comments  |
      | /albums    |
      | /photos    |
      | /todos     |
      | /users     |