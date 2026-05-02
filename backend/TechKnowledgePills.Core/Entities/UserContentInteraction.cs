namespace TechKnowledgePills.Core.Entities;

public class UserContentInteraction
{
    public int Id { get; set; }
    public int UserId { get; set; }
    public int ContentId { get; set; }
    public DateTime CompletedAt { get; set; } = DateTime.UtcNow;
    public int? Rating { get; set; } // 1-5 rating
    
    // Navigation properties
    public User User { get; set; } = null!;
    public Content Content { get; set; } = null!;
}

