namespace TechKnowledgePills.Core.Entities;

public class HealthMetric
{
    public int Id { get; set; }
    public int UserId { get; set; }
    public DateTime Timestamp { get; set; } = DateTime.UtcNow;
    
    // IoT Device Data
    public int? HeartRate { get; set; } // BPM
    public int? Steps { get; set; } // Daily steps
    public double? SleepHours { get; set; } // Hours of sleep
    public int? HeartRateVariability { get; set; } // HRV in ms
    public double? BodyTemperature { get; set; } // Celsius
    public string? DeviceId { get; set; } // IoT device identifier
    public string? DeviceType { get; set; } // e.g., "fitness_tracker", "smartwatch", "health_monitor"
    
    // Navigation property
    public User User { get; set; } = null!;
}

