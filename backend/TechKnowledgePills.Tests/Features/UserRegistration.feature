Feature: User Registration
    As a new user
    I want to register an account
    So that I can access the application

    Background:
        Given the API is available

    Scenario: Successful user registration
        Given I have a valid email "test@example.com"
        And I have a valid password "Password123"
        When I register with these credentials
        Then the registration should be successful
        And I should receive a JWT token
        And the response should contain my user ID
        And the response should contain my email "test@example.com"

    Scenario: Registration fails with duplicate email
        Given I have already registered with email "existing@example.com" and password "Password123"
        When I try to register again with email "existing@example.com" and password "Password456"
        Then the registration should fail with status code 400
        And the error message should indicate the email is already registered

    Scenario: Registration fails with invalid email format
        Given I have an invalid email "notanemail"
        And I have a valid password "Password123"
        When I register with these credentials
        Then the registration should fail with status code 400
        And the error message should indicate invalid email format

    Scenario: Registration fails with short password
        Given I have a valid email "test2@example.com"
        And I have a short password "12345"
        When I register with these credentials
        Then the registration should fail with status code 400
        And the error message should indicate password validation failure

