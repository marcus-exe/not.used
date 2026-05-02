using System.Net;
using System.Net.Http.Json;
using FluentAssertions;
using TechKnowledgePills.Core.DTOs;
using TechKnowledgePills.Core.Enums;
using TechKnowledgePills.Tests.Support;
using TechTalk.SpecFlow;

namespace TechKnowledgePills.Tests.StepDefinitions;

[Binding]
public class StressIndicatorSteps
{
    private readonly TestContext _context;

    public StressIndicatorSteps()
    {
        _context = TestDependencies.GetContext();
    }

    [Given(@"I want to record a stress level of ""([^""]+)""$")]
    public void GivenIWantToRecordAStressLevelOf(string stressLevel)
    {
        var level = Enum.Parse<StressLevel>(stressLevel);
        _context.StressIndicatorDto = new StressIndicatorDto
        {
            StressLevel = level,
            Timestamp = DateTime.UtcNow
        };
    }

    [Given(@"I want to record a stress level of ""([^""]+)"" with notes ""([^""]+)""$")]
    public void GivenIWantToRecordAStressLevelOfWithNotes(string stressLevel, string notes)
    {
        var level = Enum.Parse<StressLevel>(stressLevel);
        _context.StressIndicatorDto = new StressIndicatorDto
        {
            StressLevel = level,
            Timestamp = DateTime.UtcNow,
            Notes = notes
        };
    }

    [Given(@"I have created multiple stress indicators")]
    public async Task GivenIHaveCreatedMultipleStressIndicators()
    {
        var levels = new[] { StressLevel.Low, StressLevel.Medium, StressLevel.High };
        foreach (var level in levels)
        {
            var indicator = new StressIndicatorDto
            {
                StressLevel = level,
                Timestamp = DateTime.UtcNow.AddMinutes(-levels.Length + Array.IndexOf(levels, level))
            };

            await _context.Client!.PostAsJsonAsync("/api/stressindicator", indicator);
        }
    }

    [Given(@"I have created stress indicators at different times")]
    public async Task GivenIHaveCreatedStressIndicatorsAtDifferentTimes()
    {
        var indicators = new[]
        {
            new StressIndicatorDto { StressLevel = StressLevel.Low, Timestamp = DateTime.UtcNow.AddHours(-2) },
            new StressIndicatorDto { StressLevel = StressLevel.Medium, Timestamp = DateTime.UtcNow.AddHours(-1) },
            new StressIndicatorDto { StressLevel = StressLevel.High, Timestamp = DateTime.UtcNow }
        };

        foreach (var indicator in indicators)
        {
            await _context.Client!.PostAsJsonAsync("/api/stressindicator", indicator);
        }
    }

    [When(@"I create a stress indicator with level ""([^""]+)""$")]
    public async Task WhenICreateAStressIndicatorWithLevel(string stressLevel)
    {
        var level = Enum.Parse<StressLevel>(stressLevel);
        var indicator = new StressIndicatorDto
        {
            StressLevel = level,
            Timestamp = DateTime.UtcNow
        };

        _context.SetResponse(await _context.Client!.PostAsJsonAsync("/api/stressindicator", indicator));
    }

    [When(@"I create a stress indicator with level ""([^""]+)"" and notes ""([^""]+)""$")]
    public async Task WhenICreateAStressIndicatorWithLevelAndNotes(string stressLevel, string notes)
    {
        var level = Enum.Parse<StressLevel>(stressLevel);
        var indicator = new StressIndicatorDto
        {
            StressLevel = level,
            Timestamp = DateTime.UtcNow,
            Notes = notes
        };

        _context.SetResponse(await _context.Client!.PostAsJsonAsync("/api/stressindicator", indicator));
    }

    [When(@"I request all my stress indicators")]
    public async Task WhenIRequestAllMyStressIndicators()
    {
        _context.SetResponse(await _context.Client!.GetAsync("/api/stressindicator"));
    }

    [When(@"I request my latest stress indicator")]
    public async Task WhenIRequestMyLatestStressIndicator()
    {
        _context.SetResponse(await _context.Client!.GetAsync("/api/stressindicator/latest"));
    }

    [When(@"I try to retrieve my stress indicators")]
    public async Task WhenITryToRetrieveMyStressIndicators()
    {
        _context.SetResponse(await _context.Client!.GetAsync("/api/stressindicator"));
    }

    [Then(@"the stress indicator creation should be successful")]
    public void ThenTheStressIndicatorCreationShouldBeSuccessful()
    {
        _context.Response!.StatusCode.Should().BeOneOf(HttpStatusCode.Created, HttpStatusCode.OK);
    }

    [Then(@"the response should contain the created stress indicator")]
    public async Task ThenTheResponseShouldContainTheCreatedStressIndicator()
    {
        var indicator = await _context.GetResponseJsonAsync<StressIndicatorDto>();
        indicator.Should().NotBeNull();
        _context.CreatedStressIndicatorId = indicator!.Id;
    }

    [Then(@"the stress level should be ""(.*)""")]
    public async Task ThenTheStressLevelShouldBe(string stressLevel)
    {
        var level = Enum.Parse<StressLevel>(stressLevel);
        var indicator = await _context.GetResponseJsonAsync<StressIndicatorDto>();
        indicator!.StressLevel.Should().Be(level);
    }

    [Then(@"the stress indicator should have a timestamp")]
    public async Task ThenTheStressIndicatorShouldHaveATimestamp()
    {
        var indicator = await _context.GetResponseJsonAsync<StressIndicatorDto>();
        indicator!.Timestamp.Should().BeCloseTo(DateTime.UtcNow, TimeSpan.FromMinutes(1));
    }

    [Then(@"I should receive a list of stress indicators")]
    public async Task ThenIShouldReceiveAListOfStressIndicators()
    {
        var indicators = await _context.GetResponseJsonAsync<List<StressIndicatorDto>>();
        indicators.Should().NotBeNull();
        indicators!.Count.Should().BeGreaterThan(0);
    }

    [Then(@"all stress indicators should belong to my user account")]
    public async Task ThenAllStressIndicatorsShouldBelongToMyUserAccount()
    {
        var indicators = await _context.GetResponseJsonAsync<List<StressIndicatorDto>>();
        indicators!.All(i => i.UserId == _context.CreatedUserId).Should().BeTrue();
    }

    [Then(@"I should receive the most recent stress indicator")]
    public async Task ThenIShouldReceiveTheMostRecentStressIndicator()
    {
        var indicator = await _context.GetResponseJsonAsync<StressIndicatorDto>();
        indicator.Should().NotBeNull();
    }

    [Then(@"the stress indicator should have the latest timestamp")]
    public async Task ThenTheStressIndicatorShouldHaveTheLatestTimestamp()
    {
        var latestIndicator = await _context.GetResponseJsonAsync<StressIndicatorDto>();
        
        // Get all indicators to verify this is the latest
        var allResponse = await _context.Client!.GetAsync("/api/stressindicator");
        var allIndicators = await allResponse.Content.ReadFromJsonAsync<List<StressIndicatorDto>>();
        
        var maxTimestamp = allIndicators!.Max(i => i.Timestamp);
        latestIndicator!.Timestamp.Should().Be(maxTimestamp);
    }

    [Then(@"the notes should be ""(.*)""")]
    public async Task ThenTheNotesShouldBe(string notes)
    {
        var indicator = await _context.GetResponseJsonAsync<StressIndicatorDto>();
        indicator!.Notes.Should().Be(notes);
    }
}

