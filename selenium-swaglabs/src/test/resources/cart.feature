Feature: shopping cart

  Scenario: Viewing the cart with no products
    Given I am on the products page
      And I have added 0 products to the cart
    When I click the shopping cart icon
    Then I should see the cart page
      And The cart should show 0 products

  Scenario: Viewing the cart with a product
    Given I am on the products page
      And I have added 1 product to the cart
    When I click the shopping cart icon
    Then I should see the cart page
      And The cart should show 1 product