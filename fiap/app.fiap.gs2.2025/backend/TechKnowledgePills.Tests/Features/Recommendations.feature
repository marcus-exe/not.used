Feature: Content Recommendations
    As an authenticated user
    I want to receive personalized content recommendations
    So that I can access relevant knowledge pills based on my stress level

    Background:
        Given the API is available
        And I am authenticated as a user

    Scenario: Get recommendations based on stress level
        Given I have a stress level of "High" recorded
        And there is content available in the system
        When I request recommendations
        Then the response should be successful
        And I should receive a list of recommended content
        And the recommendations should be personalized based on my stress level

    Scenario: Get recommendations without stress indicator
        Given I have not recorded any stress indicators
        And there is content available in the system
        When I request recommendations
        Then the response should be successful
        And I should receive default recommendations
        And the recommendations should use medium stress level as default

    Scenario: Recommendations are filtered by stress level
        Given I have a stress level of "Low" recorded
        And there is content available in the system
        When I request recommendations
        Then the response should be successful
        And the recommendations should be appropriate for low stress level

    Scenario: Access recommendations without authentication
        Given I am not authenticated
        When I try to retrieve recommendations
        Then the request should fail with status code 401

