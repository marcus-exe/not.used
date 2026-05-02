using TechKnowledgePills.Core.Enums;

namespace TechKnowledgePills.Core.Entities;

public class StressIndicator
{
    public int Id { get; set; }
    public int UserId { get; set; }
    public StressLevel StressLevel { get; set; }
    public DateTime Timestamp { get; set; } = DateTime.UtcNow;
    public string? Notes { get; set; }
    
    // Navigation property
    public User User { get; set; } = null!;
}

