using TechKnowledgePills.Core.Entities;
using TechKnowledgePills.Core.Enums;

namespace TechKnowledgePills.Core.Interfaces;

public interface IStressIndicatorRepository
{
    Task<IEnumerable<StressIndicator>> GetByUserIdAsync(int userId);
    Task<StressIndicator?> GetLatestByUserIdAsync(int userId);
    Task<StressIndicator> CreateAsync(StressIndicator indicator);
    Task<IEnumerable<StressIndicator>> GenerateMockDataAsync(int userId, int count = 30);
}

