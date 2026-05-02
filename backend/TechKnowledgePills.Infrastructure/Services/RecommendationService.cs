using TechKnowledgePills.Core.Entities;
using TechKnowledgePills.Core.Enums;
using TechKnowledgePills.Core.Interfaces;

namespace TechKnowledgePills.Infrastructure.Services;

public class RecommendationService : IRecommendationService
{
    private readonly IContentRepository _contentRepository;

    public RecommendationService(IContentRepository contentRepository)
    {
        _contentRepository = contentRepository;
    }

    public async Task<IEnumerable<Content>> GetRecommendationsAsync(int userId, StressLevel currentStressLevel)
    {
        return await _contentRepository.GetRecommendedAsync(userId, currentStressLevel);
    }
}

