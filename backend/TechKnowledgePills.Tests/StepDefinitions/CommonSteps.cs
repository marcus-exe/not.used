using System.Net;
using System.Net.Http.Json;
using FluentAssertions;
using TechKnowledgePills.Core.DTOs;
using TechKnowledgePills.Tests.Support;
using TechTalk.SpecFlow;

namespace TechKnowledgePills.Tests.StepDefinitions;

[Binding]
public class CommonSteps
{
    private readonly TestContext _context;
    private readonly TestWebApplicationFactory _factory;

    public CommonSteps()
    {
        _context = TestDependencies.GetContext();
        _factory = TestDependencies.GetFactory();
    }

    [Given(@"the API is available")]
    public void GivenTheApiIsAvailable()
    {
        _context.SetClient(_factory.CreateClient());
    }

    [Given(@"I am authenticated as a user")]
    public async Task GivenIAmAuthenticatedAsAUser()
    {
        // Register and login a test user
        var registerRequest = new RegisterRequest
        {
            Email = $"auth_{Guid.NewGuid()}@example.com",
            Password = "Password123"
        };

        var registerResponse = await _context.Client!.PostAsJsonAsync("/api/auth/register", registerRequest);
        registerResponse.EnsureSuccessStatusCode();

        var authResponse = await registerResponse.Content.ReadFromJsonAsync<AuthResponse>();
        _context.AuthResponse = authResponse;
        _context.SetAuthToken(authResponse!.Token);
        _context.CreatedUserId = authResponse.UserId;
    }

    [Given(@"I am not authenticated")]
    public void GivenIAmNotAuthenticated()
    {
        _context.ClearAuthToken();
    }
}

