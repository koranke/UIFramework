Feature: Login as user


  Scenario: User login with valid credentials
    Given I am on the login page
    When I login with valid credentials
    Then I should see the Products page

  Scenario Outline: User login with invalid credentials
    Given I am on the login page
    When I login with invalid credentials <username> <password>
    Then I should see an error message that contains <message>
    Examples:
      | username | password | message |
      | "standard_user" | "invalid" | "Username and password do not match any user in this service" |
      | "standard_user" | "" | "Password is required" |
