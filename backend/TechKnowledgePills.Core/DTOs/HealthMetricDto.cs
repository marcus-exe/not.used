namespace TechKnowledgePills.Core.DTOs;

public class HealthMetricDto
{
    public int Id { get; set; }
    public int UserId { get; set; }
    public DateTime Timestamp { get; set; }
    public int? HeartRate { get; set; }
    public int? Steps { get; set; }
    public double? SleepHours { get; set; }
    public int? HeartRateVariability { get; set; }
    public double? BodyTemperature { get; set; }
    public string? DeviceId { get; set; }
    public string? DeviceType { get; set; }
}

