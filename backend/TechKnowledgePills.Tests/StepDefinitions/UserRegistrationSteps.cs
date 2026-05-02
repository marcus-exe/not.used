using System.Net;
using System.Net.Http.Json;
using System.Text.Json;
using FluentAssertions;
using TechKnowledgePills.Core.DTOs;
using TechKnowledgePills.Tests.Support;
using TechTalk.SpecFlow;

namespace TechKnowledgePills.Tests.StepDefinitions;

[Binding]
public class UserRegistrationSteps
{
    private readonly TestContext _context;

    public UserRegistrationSteps()
    {
        _context = TestDependencies.GetContext();
    }

    [Given(@"I have a valid email ""(.*)""")]
    public void GivenIHaveAValidEmail(string email)
    {
        _context.RegisterRequest ??= new RegisterRequest();
        _context.RegisterRequest.Email = email;
    }

    [Given(@"I have a valid password ""(.*)""")]
    public void GivenIHaveAValidPassword(string password)
    {
        _context.RegisterRequest ??= new RegisterRequest();
        _context.RegisterRequest.Password = password;
    }

    [Given(@"I have an invalid email ""(.*)""")]
    public void GivenIHaveAnInvalidEmail(string email)
    {
        _context.RegisterRequest ??= new RegisterRequest();
        _context.RegisterRequest.Email = email;
    }

    [Given(@"I have a short password ""(.*)""")]
    public void GivenIHaveAShortPassword(string password)
    {
        _context.RegisterRequest ??= new RegisterRequest();
        _context.RegisterRequest.Password = password;
    }

    [Given(@"I have already registered with email ""(.*)"" and password ""(.*)""")]
    public async Task GivenIHaveAlreadyRegisteredWithEmailAndPassword(string email, string password)
    {
        var registerRequest = new RegisterRequest
        {
            Email = email,
            Password = password
        };

        var response = await _context.Client!.PostAsJsonAsync("/api/auth/register", registerRequest);
        response.EnsureSuccessStatusCode();
    }

    [When(@"I register with these credentials")]
    public async Task WhenIRegisterWithTheseCredentials()
    {
        _context.SetResponse(await _context.Client!.PostAsJsonAsync("/api/auth/register", _context.RegisterRequest!));
    }

    [When(@"I try to register again with email ""(.*)"" and password ""(.*)""")]
    public async Task WhenITryToRegisterAgainWithEmailAndPassword(string email, string password)
    {
        var registerRequest = new RegisterRequest
        {
            Email = email,
            Password = password
        };

        _context.SetResponse(await _context.Client!.PostAsJsonAsync("/api/auth/register", registerRequest));
    }

    [Then(@"the registration should be successful")]
    public void ThenTheRegistrationShouldBeSuccessful()
    {
        _context.Response!.StatusCode.Should().Be(HttpStatusCode.OK);
    }

    [Then(@"I should receive a JWT token")]
    public async Task ThenIShouldReceiveAJwtToken()
    {
        var authResponse = await _context.GetResponseJsonAsync<AuthResponse>();
        authResponse.Should().NotBeNull();
        authResponse!.Token.Should().NotBeNullOrEmpty();
        _context.AuthResponse = authResponse;
    }

    [Then(@"the response should contain my user ID")]
    public async Task ThenTheResponseShouldContainMyUserId()
    {
        var authResponse = await _context.GetResponseJsonAsync<AuthResponse>();
        authResponse!.UserId.Should().BeGreaterThan(0);
        _context.CreatedUserId = authResponse.UserId;
    }

    [Then(@"the response should contain my email ""(.*)""")]
    public async Task ThenTheResponseShouldContainMyEmail(string email)
    {
        var authResponse = await _context.GetResponseJsonAsync<AuthResponse>();
        authResponse!.Email.Should().Be(email);
    }

    [Then(@"the registration should fail with status code (\d+)")]
    public void ThenTheRegistrationShouldFailWithStatusCode(int statusCode)
    {
        _context.Response!.StatusCode.Should().Be((HttpStatusCode)statusCode);
    }

    [Then(@"the error message should indicate the email is already registered")]
    public async Task ThenTheErrorMessageShouldIndicateTheEmailIsAlreadyRegistered()
    {
        var content = await _context.GetResponseBodyAsync();
        content.ToLowerInvariant().Should().Contain("already registered");
    }

    [Then(@"the error message should indicate invalid email format")]
    public async Task ThenTheErrorMessageShouldIndicateInvalidEmailFormat()
    {
        var content = await _context.GetResponseBodyAsync();
        // ModelState validation errors
        var normalizedContent = content.ToLowerInvariant();
        normalizedContent.Should().ContainAny(new[] { "email", "invalid" });
    }

    [Then(@"the error message should indicate password validation failure")]
    public async Task ThenTheErrorMessageShouldIndicatePasswordValidationFailure()
    {
        var content = await _context.GetResponseBodyAsync();
        // ModelState validation errors
        var normalizedContent = content.ToLowerInvariant();
        normalizedContent.Should().ContainAny(new[] { "password", "length" });
    }
}

