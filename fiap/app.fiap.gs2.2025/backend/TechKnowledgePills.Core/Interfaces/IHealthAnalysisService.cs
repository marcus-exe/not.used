using TechKnowledgePills.Core.Entities;
using TechKnowledgePills.Core.Enums;

namespace TechKnowledgePills.Core.Interfaces;

public interface IHealthAnalysisService
{
    Task<StressIndicator?> AnalyzeHealthMetricsAndCreateStressIndicatorAsync(int userId, HealthMetric healthMetric);
    StressLevel CalculateStressLevelFromHealthMetrics(HealthMetric healthMetric);
}

