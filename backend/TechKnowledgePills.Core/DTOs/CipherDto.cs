namespace TechKnowledgePills.Core.DTOs;

public class CipherDto
{
    public int Id { get; set; }
    public int UserId { get; set; }
    public string KeyName { get; set; } = string.Empty;
    public string EncryptedData { get; set; } = string.Empty;
    public string? Description { get; set; }
    public string Algorithm { get; set; } = "AES256";
    public DateTime CreatedAt { get; set; }
    public DateTime? UpdatedAt { get; set; }
    public bool IsActive { get; set; }
}

public class CreateCipherRequest
{
    public string KeyName { get; set; } = string.Empty;
    public string EncryptedData { get; set; } = string.Empty;
    public string? Description { get; set; }
    public string Algorithm { get; set; } = "AES256";
}

public class UpdateCipherRequest
{
    public string? KeyName { get; set; }
    public string? EncryptedData { get; set; }
    public string? Description { get; set; }
    public string? Algorithm { get; set; }
    public bool? IsActive { get; set; }
}

