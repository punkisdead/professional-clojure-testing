@ui
Feature: ROI Calculator
  As a budding investor
  I want to be able to check the ROI on various stocks
  So that I can determine whether or not to purchase a stock

  Scenario: Link in navbar takes me to homepage
    Given I am at the "homepage"
    When I click the title bar
    Then I should be at the "homepage"

  Scenario: Submit a symbol for calculation
    Given I am at the "homepage"
    When I calculate the ROI for the symbol "AAPL"
    Then I should see an initial value
    And I should see a final value
    And I should see an ROI

  Scenario: Page not found
    Given I am at an invalid page
    Then I should see a message stating "Page Not Found"
    When I click on the "Home" button
    Then I should be at the "homepage"

