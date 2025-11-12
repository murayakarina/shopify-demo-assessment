@dashboard


Feature: Checkout

  Scenario: user can add item to cart and checkout
    Given I am Testing Case : "1"
    And I login into Application
    And I add a product to the cart
    And I click on the cart
    And I click on checkout
    And I enter the First Name
    And I enter the Last Name
    And I enter a Postal Code
    And I click on continue
    Then I click on Finish
    And I get a thank you message



