Feature: Content Management
    As an authenticated user
    I want to manage content
    So that I can view and create knowledge pills

    Background:
        Given the API is available
        And I am authenticated as a user

    Scenario: Retrieve all content
        Given there are multiple content items in the system
        When I request all content
        Then the response should be successful
        And I should receive a list of content items
        And each content item should have an ID, title, and type

    Scenario: Retrieve content by ID
        Given there is content with ID 1 in the system
        When I request content with ID 1
        Then the response should be successful
        And the content should have ID 1
        And the content should have a title
        And the content should have a type

    Scenario: Retrieve content by type
        Given there are articles in the system
        When I request content of type "Article"
        Then the response should be successful
        And all returned content should be of type "Article"

    Scenario: Create new article content
        Given I have article content with title "New Article" and body "Article body"
        When I create the content
        Then the content creation should be successful
        And the response should contain the created content
        And the created content should have the title "New Article"
        And the created content should have an ID

    Scenario: Create new video content
        Given I have video content with title "New Video" and video URL "https://example.com/video"
        When I create the content
        Then the content creation should be successful
        And the created content should have the title "New Video"
        And the created content should have the video URL "https://example.com/video"

    Scenario: Access content without authentication
        Given I am not authenticated
        When I try to retrieve all content
        Then the request should fail with status code 401

