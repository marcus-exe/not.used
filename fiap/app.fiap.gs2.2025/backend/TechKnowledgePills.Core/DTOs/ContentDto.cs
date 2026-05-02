using TechKnowledgePills.Core.Enums;

namespace TechKnowledgePills.Core.DTOs;

public class ContentDto
{
    public int Id { get; set; }
    public string Title { get; set; } = string.Empty;
    public ContentType Type { get; set; }
    public string Body { get; set; } = string.Empty;
    public string? VideoUrl { get; set; }
    public string? QuizData { get; set; }
    public DateTime CreatedAt { get; set; }
    public string? Tags { get; set; }
}

