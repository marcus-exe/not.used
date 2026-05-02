namespace TechKnowledgePills.Core.Entities;

public class Cipher
{
    public int Id { get; set; }
    public int UserId { get; set; }
    public string KeyName { get; set; } = string.Empty;
    public string EncryptedData { get; set; } = string.Empty;
    public string? Description { get; set; }
    public string Algorithm { get; set; } = "AES256"; // Default algorithm
    public DateTime CreatedAt { get; set; } = DateTime.UtcNow;
    public DateTime? UpdatedAt { get; set; }
    public bool IsActive { get; set; } = true;
    
    // Navigation property
    public User User { get; set; } = null!;
}

