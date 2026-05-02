using System.Net;
using System.Net.Http.Json;
using FluentAssertions;
using TechKnowledgePills.Core.DTOs;
using TechKnowledgePills.Tests.Support;
using TechTalk.SpecFlow;

namespace TechKnowledgePills.Tests.StepDefinitions;

[Binding]
public class UserLoginSteps
{
    private readonly TestContext _context;

    public UserLoginSteps()
    {
        _context = TestDependencies.GetContext();
    }

    [Given(@"I have registered with email ""(.*)"" and password ""(.*)""")]
    public async Task GivenIHaveRegisteredWithEmailAndPassword(string email, string password)
    {
        var registerRequest = new RegisterRequest
        {
            Email = email,
            Password = password
        };

        var response = await _context.Client!.PostAsJsonAsync("/api/auth/register", registerRequest);
        response.EnsureSuccessStatusCode();
    }

    [Given(@"no user exists with email ""(.*)""")]
    public void GivenNoUserExistsWithEmail(string email)
    {
        // No action needed - user doesn't exist
    }

    [Given(@"I have a password ""(.*)""")]
    public void GivenIHaveAPassword(string password)
    {
        _context.LoginRequest ??= new LoginRequest();
        _context.LoginRequest.Password = password;
    }

    [When(@"I login with email ""(.*)"" and password ""(.*)""")]
    public async Task WhenILoginWithEmailAndPassword(string email, string password)
    {
        var loginRequest = new LoginRequest
        {
            Email = email,
            Password = password
        };

        _context.SetResponse(await _context.Client!.PostAsJsonAsync("/api/auth/login", loginRequest));
    }

    [When(@"I try to login without providing an email")]
    public async Task WhenITryToLoginWithoutProvidingAnEmail()
    {
        var loginRequest = new LoginRequest
        {
            Email = string.Empty,
            Password = _context.LoginRequest?.Password ?? "Password123"
        };

        _context.SetResponse(await _context.Client!.PostAsJsonAsync("/api/auth/login", loginRequest));
    }

    [Then(@"the login should be successful")]
    public void ThenTheLoginShouldBeSuccessful()
    {
        _context.Response!.StatusCode.Should().Be(HttpStatusCode.OK);
    }

    [Then(@"the login should fail with status code (\d+)")]
    public void ThenTheLoginShouldFailWithStatusCode(int statusCode)
    {
        _context.Response!.StatusCode.Should().Be((HttpStatusCode)statusCode);
    }

    [Then(@"the error message should indicate invalid credentials")]
    public async Task ThenTheErrorMessageShouldIndicateInvalidCredentials()
    {
        var content = await _context.GetResponseBodyAsync();
        var normalizedContent = content.ToLowerInvariant();
        normalizedContent.Should().ContainAny(new[] { "invalid", "unauthorized" });
    }

    [Then(@"the error message should indicate email is required")]
    public async Task ThenTheErrorMessageShouldIndicateEmailIsRequired()
    {
        var content = await _context.GetResponseBodyAsync();
        var normalizedContent = content.ToLowerInvariant();
        normalizedContent.Should().ContainAny(new[] { "email", "required" });
    }
}

