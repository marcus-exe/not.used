using TechKnowledgePills.Core.Entities;
using TechKnowledgePills.Core.Enums;

namespace TechKnowledgePills.Core.Interfaces;

public interface IRecommendationService
{
    Task<IEnumerable<Content>> GetRecommendationsAsync(int userId, StressLevel currentStressLevel);
}

