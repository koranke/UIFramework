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

  Scenario: Viewing the cart with multiple products
    Given I am on the products page
      And I have added the following products to the cart
        | Sauce Labs Onesie |
        | Sauce Labs Bike Light |
    When I click the shopping cart icon
    Then I should see the cart page
      And The cart should show 2 products
      And The cart should show the correct products