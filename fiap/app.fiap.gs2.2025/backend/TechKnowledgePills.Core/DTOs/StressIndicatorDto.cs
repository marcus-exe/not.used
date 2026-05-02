using TechKnowledgePills.Core.Enums;

namespace TechKnowledgePills.Core.DTOs;

public class StressIndicatorDto
{
    public int Id { get; set; }
    public int UserId { get; set; }
    public StressLevel StressLevel { get; set; }
    public DateTime Timestamp { get; set; }
    public string? Notes { get; set; }
}

