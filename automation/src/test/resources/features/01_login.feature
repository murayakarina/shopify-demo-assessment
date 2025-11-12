@login
Feature: Login

  @validLogin
  Scenario: User Want to Login : with valid credentials
    Given I am Testing Case : "1"
    And User arrives to Login Page
    And I enter a valid username
    And I enter a valid password
    And I click on the login button
    Then I verify login

  Scenario: user can logout
    Given I am Testing Case : "2"
    And user clicks on the menu button
    And user clicks on the logout button
    Then user is logged out

  Scenario: user cannot login with invalid credentials
    Given I am Testing Case : "3"
    And User arrives to Login Page
    And I enter a valid username
    And I enter an invalid password
    And I click on the login button
    Then I verify the Error message

