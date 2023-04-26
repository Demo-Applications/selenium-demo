@Test
Feature: Dummy SauceLabs UI : User Login

  # Background : add some context to the scenarios that follow it- one or more Given steps
  Background: 
    Given User opens a Web Borwser

  @Login @Invalid
  Scenario: Verify that user is unable to login with invalid credentials
    Given User is on the login page
    And User inputs credentials:
      | user_name | invalid@test.mail |
      | password  |           0000000 |
    Then User should not be able to login

  @Login @Valid
  Scenario Outline: Verify that user logs in successfully with valid credentials
    Given User is on the login page
    And User inputs name "<user_name>" and password "<password>" and submits
    Then User should be able to login successfully

    Examples: 
      | id | user_name     | password     |
      |  1 | standard_user | secret_sauce |
      |  2 | problem_user  | secret_sauce |
