Feature: Stress Indicator Management
    As an authenticated user
    I want to track my stress levels
    So that the system can provide personalized recommendations

    Background:
        Given the API is available
        And I am authenticated as a user

    Scenario: Create a new stress indicator
        Given I want to record a stress level of "High"
        When I create a stress indicator with level "High"
        Then the stress indicator creation should be successful
        And the response should contain the created stress indicator
        And the stress level should be "High"
        And the stress indicator should have a timestamp

    Scenario: Retrieve all my stress indicators
        Given I have created multiple stress indicators
        When I request all my stress indicators
        Then the response should be successful
        And I should receive a list of stress indicators
        And all stress indicators should belong to my user account

    Scenario: Retrieve latest stress indicator
        Given I have created stress indicators at different times
        When I request my latest stress indicator
        Then the response should be successful
        And I should receive the most recent stress indicator
        And the stress indicator should have the latest timestamp

    Scenario: Create stress indicator with notes
        Given I want to record a stress level of "Medium" with notes "Feeling stressed about deadlines"
        When I create a stress indicator with level "Medium" and notes "Feeling stressed about deadlines"
        Then the stress indicator creation should be successful
        And the notes should be "Feeling stressed about deadlines"

    Scenario: Access stress indicators without authentication
        Given I am not authenticated
        When I try to retrieve my stress indicators
        Then the request should fail with status code 401

