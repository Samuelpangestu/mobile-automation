@mobile @login
Feature: SauceDemo Mobile - Login Functionality
  As a user
  I want to login to SauceDemo mobile app
  So that I can access the dashboard

  Background:
    Given the SauceDemo mobile app is launched

  @smoke
  Scenario: TC_01 - Successful login with valid credentials
    When I login with username "bob@example.com" and password "10203040"
    Then I should be redirected to the dashboard
    And the dashboard should be displayed

  @negative
  Scenario: TC_02 - Login with invalid credentials
    When I login with username "invalid@example.com" and password "wrongpass"
    Then I should see an error message
    And I should remain on the login page

  @negative
  Scenario: TC_03 - Login with empty credentials
    When I login with username "" and password ""
    Then I should see an error message
    And I should remain on the login page

  @smoke
  Scenario: TC_04 - Logout functionality
    When I login with username "bob@example.com" and password "10203040"
    Then I should be redirected to the dashboard
    When I logout from the dashboard
    Then I should be redirected to the login page
