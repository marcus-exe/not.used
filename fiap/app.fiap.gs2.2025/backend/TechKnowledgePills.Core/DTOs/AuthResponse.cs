namespace TechKnowledgePills.Core.DTOs;

public class AuthResponse
{
    public string Token { get; set; } = string.Empty;
    public string RefreshToken { get; set; } = string.Empty;
    public DateTime ExpiresAt { get; set; }
    public int UserId { get; set; }
    public string Email { get; set; } = string.Empty;
}

