@mobile @product
Feature: SauceDemo Mobile - Product Functionality
  As a user
  I want to interact with products
  So that I can browse and add items to cart

  Background:
    Given the SauceDemo mobile app is launched

  @smoke @test
  Scenario: TC_09 - Add product to cart
    When I click on the first product
    And I click add to cart button
    Then the product should be added to cart
    And cart badge should show "1" item

  Scenario: TC_10 - Sort products by price
    When I click on the sort button
    And I select sort by "Price - ascending"
    Then products should be sorted by price ascending

  Scenario: TC_11 - View product details
    When I click on the first product
    Then product details should be displayed
    And product name should be visible
    And product price should be visible

  Scenario: TC_12 - Remove product from cart
    When I click on the first product
    And I click add to cart button
    And I open the cart
    And I remove the product from cart
    Then cart should be empty
