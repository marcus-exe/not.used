using Microsoft.EntityFrameworkCore;
using TechKnowledgePills.Core.Entities;
using TechKnowledgePills.Core.Interfaces;
using TechKnowledgePills.Infrastructure.Data;

namespace TechKnowledgePills.Infrastructure.Repositories;

public class HealthMetricRepository : IHealthMetricRepository
{
    private readonly ApplicationDbContext _context;

    public HealthMetricRepository(ApplicationDbContext context)
    {
        _context = context;
    }

    public async Task<HealthMetric> CreateAsync(HealthMetric healthMetric)
    {
        _context.HealthMetrics.Add(healthMetric);
        await _context.SaveChangesAsync();
        return healthMetric;
    }

    public async Task<IEnumerable<HealthMetric>> GetByUserIdAsync(int userId, DateTime? startDate = null, DateTime? endDate = null)
    {
        var query = _context.HealthMetrics
            .Where(h => h.UserId == userId);

        if (startDate.HasValue)
        {
            query = query.Where(h => h.Timestamp >= startDate.Value);
        }

        if (endDate.HasValue)
        {
            query = query.Where(h => h.Timestamp <= endDate.Value);
        }

        return await query
            .OrderByDescending(h => h.Timestamp)
            .ToListAsync();
    }

    public async Task<HealthMetric?> GetLatestByUserIdAsync(int userId)
    {
        return await _context.HealthMetrics
            .Where(h => h.UserId == userId)
            .OrderByDescending(h => h.Timestamp)
            .FirstOrDefaultAsync();
    }

    public async Task<IEnumerable<HealthMetric>> GetByDeviceIdAsync(string deviceId, DateTime? startDate = null, DateTime? endDate = null)
    {
        var query = _context.HealthMetrics
            .Where(h => h.DeviceId == deviceId);

        if (startDate.HasValue)
        {
            query = query.Where(h => h.Timestamp >= startDate.Value);
        }

        if (endDate.HasValue)
        {
            query = query.Where(h => h.Timestamp <= endDate.Value);
        }

        return await query
            .OrderByDescending(h => h.Timestamp)
            .ToListAsync();
    }
}

