Feature: Stocks REST API
  As an admin
  I want to be view and modify the stocks table
  So that I can manage it appropriately

  Scenario: Get All Stocks
    When I send a GET request to "/api/stocks"
    Then the response status should be "200"
    And I should see the following JSON in the body:
    """
    ["AAPL","MSFT","YHOO","AMZN","GOOGL","FB"]
    """

  Scenario: Add New Stock
    When I send a POST request to "/api/stocks" with the following params:
    | param | value |
    | sym   | NOK   |
    Then the response status should be "201"
    And the response body should be empty
    And a stock with symbol "NOK" exists in the database

  Scenario: Remove Stock
    Given a stock with symbol "NOK" exists in the database
    When I send a DELETE request to "/api/stocks/NOK"
    Then the response status should be "204"
    And a stock with symbol "NOK" does not exist in the database
