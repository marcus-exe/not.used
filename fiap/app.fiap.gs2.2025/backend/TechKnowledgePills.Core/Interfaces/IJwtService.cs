using TechKnowledgePills.Core.Entities;

namespace TechKnowledgePills.Core.Interfaces;

public interface IJwtService
{
    string GenerateToken(User user);
    string GenerateRefreshToken();
    bool ValidateToken(string token);
    int? GetUserIdFromToken(string token);
}

