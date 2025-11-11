Feature: Calculator SOAP API testing

  Scenario Outline: Test calculator operations
    Given the calculator method is "<Method>"
    And the input values are <A> and <B>
    When I send the SOAP request
    Then the response status should be 200
    And the result tag "<Tag>" should be "<Expected>"

    Examples:
      | Method    | A | B | Tag           | Expected |
      | Add       | 5 | 3 | AddResult     | 8        |
      | Subtract  | 10 | 4 | SubtractResult | 6      |
      | Multiply  | 2 | 7 | MultiplyResult | 14     |
      | Divide    | 8 | 2 | DivideResult   | 4      |
      | Divide    | 8 | 0 | DivideResult   | error   |