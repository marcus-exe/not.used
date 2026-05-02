using TechKnowledgePills.Core.Entities;
using TechKnowledgePills.Core.Enums;

namespace TechKnowledgePills.Core.Interfaces;

public interface IContentRepository
{
    Task<IEnumerable<Content>> GetAllAsync();
    Task<Content?> GetByIdAsync(int id);
    Task<IEnumerable<Content>> GetByTypeAsync(ContentType type);
    Task<IEnumerable<Content>> GetRecommendedAsync(int userId, StressLevel stressLevel);
    Task<Content> CreateAsync(Content content);
    Task UpdateAsync(Content content);
    Task DeleteAsync(int id);
}

