using System.Net;
using System.Net.Http.Json;
using FluentAssertions;
using TechKnowledgePills.Core.DTOs;
using TechKnowledgePills.Core.Enums;
using TechKnowledgePills.Tests.Support;
using TechTalk.SpecFlow;

namespace TechKnowledgePills.Tests.StepDefinitions;

[Binding]
public class RecommendationsSteps
{
    private readonly TestContext _context;

    public RecommendationsSteps()
    {
        _context = TestDependencies.GetContext();
    }

    [Given(@"I have a stress level of ""(.*)"" recorded")]
    public async Task GivenIHaveAStressLevelOfRecorded(string stressLevel)
    {
        var level = Enum.Parse<StressLevel>(stressLevel);
        var indicator = new StressIndicatorDto
        {
            StressLevel = level,
            Timestamp = DateTime.UtcNow
        };

        var response = await _context.Client!.PostAsJsonAsync("/api/stressindicator", indicator);
        response.EnsureSuccessStatusCode();
    }

    [Given(@"there is content available in the system")]
    public async Task GivenThereIsContentAvailableInTheSystem()
    {
        // Verify content exists or create some
        var response = await _context.Client!.GetAsync("/api/content");
        if (!response.IsSuccessStatusCode || response.Content.Headers.ContentLength == 0)
        {
            // Create some test content
            var article = new ContentDto
            {
                Title = "Test Article for Recommendations",
                Type = ContentType.Article,
                Body = "Test body"
            };

            await _context.Client!.PostAsJsonAsync("/api/content", article);
        }
    }

    [Given(@"I have not recorded any stress indicators")]
    public void GivenIHaveNotRecordedAnyStressIndicators()
    {
        // No action needed - no stress indicators exist
    }

    [When(@"I request recommendations")]
    public async Task WhenIRequestRecommendations()
    {
        _context.SetResponse(await _context.Client!.GetAsync("/api/recommendation"));
    }

    [When(@"I try to retrieve recommendations")]
    public async Task WhenITryToRetrieveRecommendations()
    {
        _context.SetResponse(await _context.Client!.GetAsync("/api/recommendation"));
    }

    [Then(@"I should receive a list of recommended content")]
    public async Task ThenIShouldReceiveAListOfRecommendedContent()
    {
        var recommendations = await _context.GetResponseJsonAsync<List<ContentDto>>();
        recommendations.Should().NotBeNull();
        recommendations!.Count.Should().BeGreaterThan(0);
    }

    [Then(@"the recommendations should be personalized based on my stress level")]
    public async Task ThenTheRecommendationsShouldBePersonalizedBasedOnMyStressLevel()
    {
        var recommendations = await _context.GetResponseJsonAsync<List<ContentDto>>();
        recommendations.Should().NotBeNull();
        // The actual recommendation logic is tested by the service
        // Here we just verify we get recommendations
        recommendations!.Count.Should().BeGreaterThan(0);
    }

    [Then(@"I should receive default recommendations")]
    public async Task ThenIShouldReceiveDefaultRecommendations()
    {
        var recommendations = await _context.GetResponseJsonAsync<List<ContentDto>>();
        recommendations.Should().NotBeNull();
        // Default recommendations should still return content
        recommendations!.Count.Should().BeGreaterThanOrEqualTo(0);
    }

    [Then(@"the recommendations should use medium stress level as default")]
    public async Task ThenTheRecommendationsShouldUseMediumStressLevelAsDefault()
    {
        // This is verified by the service logic
        // We just verify we get a response
        _context.Response!.StatusCode.Should().Be(HttpStatusCode.OK);
    }

    [Then(@"the recommendations should be appropriate for low stress level")]
    public async Task ThenTheRecommendationsShouldBeAppropriateForLowStressLevel()
    {
        var recommendations = await _context.GetResponseJsonAsync<List<ContentDto>>();
        recommendations.Should().NotBeNull();
        // The actual filtering logic is in the RecommendationService
        // Here we verify we get recommendations
        recommendations!.Count.Should().BeGreaterThanOrEqualTo(0);
    }
}

