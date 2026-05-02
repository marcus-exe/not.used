using System.Collections.Generic;
using System.Net.Http.Headers;
using System.Text.Json;
using System.Threading.Tasks;
using TechKnowledgePills.Core.DTOs;

namespace TechKnowledgePills.Tests.Support;

public class TestContext
{
    private string? _responseBody;
    private readonly Dictionary<Type, object?> _responseCache = new();

    public HttpClient? Client { get; set; }
    public HttpResponseMessage? Response { get; private set; }
    public AuthResponse? AuthResponse { get; set; }
    public string? AuthToken { get; set; }
    public RegisterRequest? RegisterRequest { get; set; }
    public LoginRequest? LoginRequest { get; set; }
    public ContentDto? ContentDto { get; set; }
    public StressIndicatorDto? StressIndicatorDto { get; set; }
    public int? CreatedUserId { get; set; }
    public int? CreatedContentId { get; set; }
    public int? CreatedStressIndicatorId { get; set; }

    public void SetClient(HttpClient client)
    {
        Client?.Dispose();
        Client = client;

        if (!string.IsNullOrEmpty(AuthToken))
        {
            Client.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", AuthToken);
        }
    }

    public void SetResponse(HttpResponseMessage response)
    {
        Response = response;
        _responseBody = null;
        _responseCache.Clear();
    }

    public async Task<string> GetResponseBodyAsync()
    {
        if (Response is null)
        {
            throw new InvalidOperationException("Response has not been set yet.");
        }

        if (_responseBody is null)
        {
            _responseBody = await Response.Content.ReadAsStringAsync();
        }

        return _responseBody;
    }

    public async Task<T?> GetResponseJsonAsync<T>()
    {
        if (_responseCache.TryGetValue(typeof(T), out var cached))
        {
            return (T?)cached;
        }

        var body = await GetResponseBodyAsync();
        var result = JsonSerializer.Deserialize<T>(body, new JsonSerializerOptions
        {
            PropertyNameCaseInsensitive = true
        });

        _responseCache[typeof(T)] = result;
        return result;
    }

    public void SetAuthToken(string token)
    {
        AuthToken = token;
        if (Client != null)
        {
            Client.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", token);
        }
    }

    public void ClearAuthToken()
    {
        AuthToken = null;
        if (Client != null)
        {
            Client.DefaultRequestHeaders.Authorization = null;
        }
    }
}

