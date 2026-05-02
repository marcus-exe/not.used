Feature: User Login
    As a registered user
    I want to login to my account
    So that I can access protected resources

    Background:
        Given the API is available

    Scenario: Successful login with valid credentials
        Given I have registered with email "login@example.com" and password "Password123"
        When I login with email "login@example.com" and password "Password123"
        Then the login should be successful
        And I should receive a JWT token
        And the response should contain my user ID
        And the response should contain my email "login@example.com"

    Scenario: Login fails with incorrect password
        Given I have registered with email "wrongpass@example.com" and password "Password123"
        When I login with email "wrongpass@example.com" and password "WrongPassword"
        Then the login should fail with status code 401
        And the error message should indicate invalid credentials

    Scenario: Login fails with non-existent email
        Given no user exists with email "nonexistent@example.com"
        When I login with email "nonexistent@example.com" and password "Password123"
        Then the login should fail with status code 401
        And the error message should indicate invalid credentials

    Scenario: Login fails with missing email
        Given I have a password "Password123"
        When I try to login without providing an email
        Then the login should fail with status code 400
        And the error message should indicate email is required

