using TechKnowledgePills.Core.Entities;

namespace TechKnowledgePills.Core.Interfaces;

public interface IHealthMetricRepository
{
    Task<HealthMetric> CreateAsync(HealthMetric healthMetric);
    Task<IEnumerable<HealthMetric>> GetByUserIdAsync(int userId, DateTime? startDate = null, DateTime? endDate = null);
    Task<HealthMetric?> GetLatestByUserIdAsync(int userId);
    Task<IEnumerable<HealthMetric>> GetByDeviceIdAsync(string deviceId, DateTime? startDate = null, DateTime? endDate = null);
}

