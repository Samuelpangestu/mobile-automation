@mobile @dashboard
Feature: SauceDemo Mobile - Dashboard Functionality
  As a logged-in user
  I want to interact with the dashboard
  So that I can view and navigate through products

  Background:
    Given the SauceDemo mobile app is launched
    And I login with username "bob@example.com" and password "10203040"

  @smoke
  Scenario: TC_05 - Verify main dashboard is displayed
    Then I should be redirected to the dashboard
    And the dashboard should be displayed
    And the menu button should be visible

  Scenario: TC_06 - Verify product catalog is displayed
    Then the dashboard should be displayed
    And products should be displayed on the dashboard

  Scenario: TC_07 - Navigate through menu
    When I open the menu
    Then the menu should be displayed

  Scenario: TC_08 - Verify cart is accessible
    Then the cart badge should be displayed
