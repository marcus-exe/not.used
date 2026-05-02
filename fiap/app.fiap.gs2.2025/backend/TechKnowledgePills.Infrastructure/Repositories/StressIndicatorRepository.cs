using Microsoft.EntityFrameworkCore;
using TechKnowledgePills.Core.Entities;
using TechKnowledgePills.Core.Enums;
using TechKnowledgePills.Core.Interfaces;
using TechKnowledgePills.Infrastructure.Data;

namespace TechKnowledgePills.Infrastructure.Repositories;

public class StressIndicatorRepository : IStressIndicatorRepository
{
    private readonly ApplicationDbContext _context;
    private readonly Random _random = new();

    public StressIndicatorRepository(ApplicationDbContext context)
    {
        _context = context;
    }

    public async Task<IEnumerable<StressIndicator>> GetByUserIdAsync(int userId)
    {
        return await _context.StressIndicators
            .Where(s => s.UserId == userId)
            .OrderByDescending(s => s.Timestamp)
            .ToListAsync();
    }

    public async Task<StressIndicator?> GetLatestByUserIdAsync(int userId)
    {
        return await _context.StressIndicators
            .Where(s => s.UserId == userId)
            .OrderByDescending(s => s.Timestamp)
            .FirstOrDefaultAsync();
    }

    public async Task<StressIndicator> CreateAsync(StressIndicator indicator)
    {
        _context.StressIndicators.Add(indicator);
        await _context.SaveChangesAsync();
        return indicator;
    }

    public async Task<IEnumerable<StressIndicator>> GenerateMockDataAsync(int userId, int count = 30)
    {
        var indicators = new List<StressIndicator>();
        var baseDate = DateTime.UtcNow.AddDays(-count);

        for (int i = 0; i < count; i++)
        {
            // Generate random stress level with some variation
            var stressLevel = (StressLevel)_random.Next(1, 5);
            
            // Add some realistic patterns (higher stress in afternoon, lower in morning)
            var hour = baseDate.AddDays(i).Hour;
            if (hour >= 14 && hour <= 18)
            {
                // Afternoon tends to be more stressful
                if (stressLevel < StressLevel.High && _random.NextDouble() > 0.5)
                {
                    stressLevel = StressLevel.High;
                }
            }

            var indicator = new StressIndicator
            {
                UserId = userId,
                StressLevel = stressLevel,
                Timestamp = baseDate.AddDays(i).AddHours(_random.Next(0, 24)),
                Notes = _random.NextDouble() > 0.7 ? $"Mock stress indicator {i + 1}" : null
            };

            indicators.Add(indicator);
        }

        _context.StressIndicators.AddRange(indicators);
        await _context.SaveChangesAsync();

        return indicators;
    }
}

