using System.Net;
using System.Net.Http.Json;
using FluentAssertions;
using TechKnowledgePills.Core.DTOs;
using TechKnowledgePills.Core.Enums;
using TechKnowledgePills.Tests.Support;
using TechTalk.SpecFlow;

namespace TechKnowledgePills.Tests.StepDefinitions;

[Binding]
public class ContentManagementSteps
{
    private readonly TestContext _context;

    public ContentManagementSteps()
    {
        _context = TestDependencies.GetContext();
    }

    [Given(@"there are multiple content items in the system")]
    public async Task GivenThereAreMultipleContentItemsInTheSystem()
    {
        // Content should be seeded by the application startup
        // Verify by making a request
        var response = await _context.Client!.GetAsync("/api/content");
        response.EnsureSuccessStatusCode();
    }

    [Given(@"there is content with ID (\d+) in the system")]
    public async Task GivenThereIsContentWithIdInTheSystem(int contentId)
    {
        // Verify content exists
        var response = await _context.Client!.GetAsync($"/api/content/{contentId}");
        if (response.StatusCode == HttpStatusCode.NotFound)
        {
            // Create content if it doesn't exist
            var content = new ContentDto
            {
                Title = $"Test Content {contentId}",
                Type = ContentType.Article,
                Body = "Test body"
            };

            var createResponse = await _context.Client!.PostAsJsonAsync("/api/content", content);
            createResponse.EnsureSuccessStatusCode();
        }
    }

    [Given(@"there are articles in the system")]
    public async Task GivenThereAreArticlesInTheSystem()
    {
        // Verify articles exist or create one
        var response = await _context.Client!.GetAsync("/api/content/type/Article");
        if (!response.IsSuccessStatusCode)
        {
            var content = new ContentDto
            {
                Title = "Test Article",
                Type = ContentType.Article,
                Body = "Article body"
            };

            await _context.Client!.PostAsJsonAsync("/api/content", content);
        }
    }

    [Given(@"I have article content with title ""(.*)"" and body ""(.*)""")]
    public void GivenIHaveArticleContentWithTitleAndBody(string title, string body)
    {
        _context.ContentDto = new ContentDto
        {
            Title = title,
            Type = ContentType.Article,
            Body = body
        };
    }

    [Given(@"I have video content with title ""(.*)"" and video URL ""(.*)""")]
    public void GivenIHaveVideoContentWithTitleAndVideoUrl(string title, string videoUrl)
    {
        _context.ContentDto = new ContentDto
        {
            Title = title,
            Type = ContentType.Video,
            VideoUrl = videoUrl,
            Body = "Video description"
        };
    }

    [When(@"I request all content")]
    public async Task WhenIRequestAllContent()
    {
        _context.SetResponse(await _context.Client!.GetAsync("/api/content"));
    }

    [When(@"I request content with ID (\d+)")]
    public async Task WhenIRequestContentWithId(int id)
    {
        _context.SetResponse(await _context.Client!.GetAsync($"/api/content/{id}"));
    }

    [When(@"I request content of type ""(.*)""")]
    public async Task WhenIRequestContentOfType(string type)
    {
        var contentType = Enum.Parse<ContentType>(type);
        _context.SetResponse(await _context.Client!.GetAsync($"/api/content/type/{contentType}"));
    }

    [When(@"I create the content")]
    public async Task WhenICreateTheContent()
    {
        _context.SetResponse(await _context.Client!.PostAsJsonAsync("/api/content", _context.ContentDto!));
    }

    [When(@"I try to retrieve all content")]
    public async Task WhenITryToRetrieveAllContent()
    {
        _context.SetResponse(await _context.Client!.GetAsync("/api/content"));
    }

    [Then(@"the response should be successful")]
    public void ThenTheResponseShouldBeSuccessful()
    {
        _context.Response!.StatusCode.Should().Be(HttpStatusCode.OK);
    }

    [Then(@"I should receive a list of content items")]
    public async Task ThenIShouldReceiveAListOfContentItems()
    {
        var contents = await _context.GetResponseJsonAsync<List<ContentDto>>();
        contents.Should().NotBeNull();
        contents!.Count.Should().BeGreaterThan(0);
    }

    [Then(@"each content item should have an ID, title, and type")]
    public async Task ThenEachContentItemShouldHaveAnIdTitleAndType()
    {
        var contents = await _context.GetResponseJsonAsync<List<ContentDto>>();
        foreach (var content in contents!)
        {
            content.Id.Should().BeGreaterThan(0);
            content.Title.Should().NotBeNullOrEmpty();
            content.Type.Should().BeDefined();
        }
    }

    [Then(@"the content should have ID (\d+)")]
    public async Task ThenTheContentShouldHaveId(int id)
    {
        var content = await _context.GetResponseJsonAsync<ContentDto>();
        content!.Id.Should().Be(id);
    }

    [Then(@"the content should have a title")]
    public async Task ThenTheContentShouldHaveATitle()
    {
        var content = await _context.GetResponseJsonAsync<ContentDto>();
        content!.Title.Should().NotBeNullOrEmpty();
    }

    [Then(@"the content should have a type")]
    public async Task ThenTheContentShouldHaveAType()
    {
        var content = await _context.GetResponseJsonAsync<ContentDto>();
        content!.Type.Should().BeDefined();
    }

    [Then(@"all returned content should be of type ""(.*)""")]
    public async Task ThenAllReturnedContentShouldBeOfType(string type)
    {
        var contentType = Enum.Parse<ContentType>(type);
        var contents = await _context.GetResponseJsonAsync<List<ContentDto>>();
        contents!.All(c => c.Type == contentType).Should().BeTrue();
    }

    [Then(@"the content creation should be successful")]
    public void ThenTheContentCreationShouldBeSuccessful()
    {
        _context.Response!.StatusCode.Should().BeOneOf(HttpStatusCode.Created, HttpStatusCode.OK);
    }

    [Then(@"the response should contain the created content")]
    public async Task ThenTheResponseShouldContainTheCreatedContent()
    {
        var content = await _context.GetResponseJsonAsync<ContentDto>();
        content.Should().NotBeNull();
        _context.CreatedContentId = content!.Id;
    }

    [Then(@"the created content should have the title ""(.*)""")]
    public async Task ThenTheCreatedContentShouldHaveTheTitle(string title)
    {
        var content = await _context.GetResponseJsonAsync<ContentDto>();
        content!.Title.Should().Be(title);
    }

    [Then(@"the created content should have an ID")]
    public async Task ThenTheCreatedContentShouldHaveAnId()
    {
        var content = await _context.GetResponseJsonAsync<ContentDto>();
        content!.Id.Should().BeGreaterThan(0);
        _context.CreatedContentId = content.Id;
    }

    [Then(@"the created content should have the video URL ""(.*)""")]
    public async Task ThenTheCreatedContentShouldHaveTheVideoUrl(string videoUrl)
    {
        var content = await _context.GetResponseJsonAsync<ContentDto>();
        content!.VideoUrl.Should().Be(videoUrl);
    }

    [Then(@"the request should fail with status code (\d+)")]
    public void ThenTheRequestShouldFailWithStatusCode(int statusCode)
    {
        _context.Response!.StatusCode.Should().Be((HttpStatusCode)statusCode);
    }
}

