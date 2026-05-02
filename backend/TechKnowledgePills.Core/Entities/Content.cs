using TechKnowledgePills.Core.Enums;

namespace TechKnowledgePills.Core.Entities;

public class Content
{
    public int Id { get; set; }
    public string Title { get; set; } = string.Empty;
    public ContentType Type { get; set; }
    public string Body { get; set; } = string.Empty;
    public string? VideoUrl { get; set; }
    public string? QuizData { get; set; } // JSON string for quiz questions/answers
    public DateTime CreatedAt { get; set; } = DateTime.UtcNow;
    public string? Tags { get; set; } // Comma-separated tags
    
    // Navigation properties
    public ICollection<UserContentInteraction> UserContentInteractions { get; set; } = new List<UserContentInteraction>();
}

