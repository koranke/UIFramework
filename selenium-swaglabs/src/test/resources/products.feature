Feature: Products page with search functionality

  Scenario: View all products, default view
    Given I am on the products page
    Then I should see all products sorted by Name ascending

  Scenario Outline: Sort products
    Given I am on the products page
    When I sort products by <sort_by> <sort_order>
    Then I should see all products sorted by <sort_by> <sort_order>
    Examples:
        | sort_by | sort_order |
        | Name    | ascending  |
        | Name    | descending |
        | Price   | ascending  |
        | Price   | descending |

  Scenario: Add product to cart
    Given I am on the products page
    When I add a product to the cart
    Then I should see 1 item in the cart

  Scenario: Add another product to cart
    Given I am on the products page
    And I have added a product to the cart
    When I add another product to the cart
    Then I should see 2 items in the cart
