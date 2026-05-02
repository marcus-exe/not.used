namespace TechKnowledgePills.Core.Entities;

public class User
{
    public int Id { get; set; }
    public string Email { get; set; } = string.Empty;
    public string PasswordHash { get; set; } = string.Empty;
    public DateTime CreatedAt { get; set; } = DateTime.UtcNow;
    public DateTime? LastLogin { get; set; }
    
    // Navigation properties
    public ICollection<StressIndicator> StressIndicators { get; set; } = new List<StressIndicator>();
    public ICollection<UserContentInteraction> UserContentInteractions { get; set; } = new List<UserContentInteraction>();
    public ICollection<HealthMetric> HealthMetrics { get; set; } = new List<HealthMetric>();
    public ICollection<Cipher> Ciphers { get; set; } = new List<Cipher>();
}

