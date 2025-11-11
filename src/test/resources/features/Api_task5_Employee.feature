Feature: Employee API Test

  Scenario: Complete Employee CRUD Validation
    Given I get the list of all employees
    When I create a new employee with name "John Doe", salary "50000", and age "30"
    Then Verify employee is created and count increased by one
    Then I get the details of the created employee
    When I update the employee salary to "60000" and age to "35"
    Then Verify updated employee details
    When I delete the employee
    Then Verify employee deleted and count decreased by one