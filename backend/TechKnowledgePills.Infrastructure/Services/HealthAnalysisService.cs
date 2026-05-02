using TechKnowledgePills.Core.Entities;
using TechKnowledgePills.Core.Enums;
using TechKnowledgePills.Core.Interfaces;

namespace TechKnowledgePills.Infrastructure.Services;

public class HealthAnalysisService : IHealthAnalysisService
{
    private readonly IStressIndicatorRepository _stressIndicatorRepository;

    public HealthAnalysisService(IStressIndicatorRepository stressIndicatorRepository)
    {
        _stressIndicatorRepository = stressIndicatorRepository;
    }

    public StressLevel CalculateStressLevelFromHealthMetrics(HealthMetric healthMetric)
    {
        var stressScore = 0;

        // Heart Rate Analysis (normal: 60-100 BPM)
        if (healthMetric.HeartRate.HasValue)
        {
            var hr = healthMetric.HeartRate.Value;
            if (hr < 60) stressScore += 1; // Low heart rate (could indicate fatigue)
            else if (hr > 100) stressScore += 2; // Elevated heart rate (stress indicator)
            else if (hr > 120) stressScore += 3; // High heart rate (high stress)
        }

        // Sleep Analysis (recommended: 7-9 hours)
        if (healthMetric.SleepHours.HasValue)
        {
            var sleep = healthMetric.SleepHours.Value;
            if (sleep < 6) stressScore += 2; // Insufficient sleep
            else if (sleep < 7) stressScore += 1; // Below recommended
            else if (sleep > 9) stressScore += 1; // Oversleeping (could indicate fatigue)
        }

        // Heart Rate Variability Analysis (lower HRV = higher stress)
        if (healthMetric.HeartRateVariability.HasValue)
        {
            var hrv = healthMetric.HeartRateVariability.Value;
            if (hrv < 20) stressScore += 3; // Very low HRV (high stress)
            else if (hrv < 30) stressScore += 2; // Low HRV
            else if (hrv < 40) stressScore += 1; // Moderate HRV
        }

        // Body Temperature Analysis (normal: 36.1-37.2°C)
        if (healthMetric.BodyTemperature.HasValue)
        {
            var temp = healthMetric.BodyTemperature.Value;
            if (temp < 36.0 || temp > 37.5) stressScore += 1; // Abnormal temperature
        }

        // Steps Analysis (low activity can indicate stress/fatigue)
        if (healthMetric.Steps.HasValue)
        {
            var steps = healthMetric.Steps.Value;
            if (steps < 3000) stressScore += 1; // Very low activity
            else if (steps < 5000) stressScore += 0; // Low but acceptable
        }

        // Convert stress score to StressLevel
        return stressScore switch
        {
            0 => StressLevel.Low,
            1 => StressLevel.Low,
            2 => StressLevel.Medium,
            3 => StressLevel.Medium,
            4 => StressLevel.High,
            5 => StressLevel.High,
            _ => StressLevel.Critical
        };
    }

    public async Task<StressIndicator?> AnalyzeHealthMetricsAndCreateStressIndicatorAsync(int userId, HealthMetric healthMetric)
    {
        var stressLevel = CalculateStressLevelFromHealthMetrics(healthMetric);

        var stressIndicator = new StressIndicator
        {
            UserId = userId,
            StressLevel = stressLevel,
            Timestamp = healthMetric.Timestamp,
            Notes = $"Auto-generated from health metrics: HR={healthMetric.HeartRate}, Sleep={healthMetric.SleepHours}h, HRV={healthMetric.HeartRateVariability}ms"
        };

        return await _stressIndicatorRepository.CreateAsync(stressIndicator);
    }
}

