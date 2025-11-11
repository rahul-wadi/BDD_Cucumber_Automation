Feature: Books API

  Scenario: 1) Basic API Health Check
    Given I make a GET request to "/status"
    Then the status code should be 200
    And the response should contain "status" as "OK"

  Scenario: 2) Retrieve List of Books
    Given I make a GET request to "/books"
    Then the status code should be 200
    And there should be at least one book in the list
    And I print the title and type of all books

  Scenario: 3) Filter Books by Type
    Given I make a GET request to "/books?type=fiction"
    Then the status code should be 200
    And all books should be of type "fiction"

  Scenario: 4) Retrieve Book Details by ID
    Given I make a GET request to "/books/1"
    Then the status code should be 200
    And the book ID should be 1

  Scenario: 5) Generate Access Token for API Authentication
    Given I generate an access token
    Then the status code should be 201
    And I store and print the access token

  Scenario: 6) Place an Order (POST Request)
    Given I have a valid access token
    When I place an order for bookId 1 and customer "John Doe"
    Then the status code should be 201
    And I should see an order ID in the response

  Scenario: 7) Retrieve List of Orders
    Given I have a valid access token
    When I retrieve all orders
    Then the status code should be 200
    And I print all order details

  Scenario: 8) Retrieve Order Details by ID
    Given I have a valid access token
    When I retrieve the order details by the stored order ID
    Then the status code should be 200
    And the order ID in the response should match the stored ID

  Scenario: 9) Update an Order (PUT Request)
    Given I have a valid access token
    When I update the order customer name to "Jane Doe"
    Then the status code should be 204
    And the order details should reflect the updated name

  Scenario: 10) Cancel an Order (DELETE Request)
    Given I have a valid access token
    When I cancel the order by its ID
    Then the status code should be 204
    And retrieving the same order should return 404

  Scenario: 11) Negative Tests (Boundary & Error Validation)
    Given I have a valid access token
    When I place an order with invalid bookId 999
    Then the status code should be 400
    And the error message should be "Invalid or non-existent book ID."

    When I retrieve order with invalid ID 999
    Then the status code should be 404
    And the error message should be "Invalid order ID."