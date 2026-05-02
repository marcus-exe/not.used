using TechKnowledgePills.Core.Entities;

namespace TechKnowledgePills.Core.Interfaces;

public interface ICipherRepository
{
    Task<Cipher?> GetByIdAsync(int id);
    Task<Cipher?> GetByIdAndUserIdAsync(int id, int userId);
    Task<IEnumerable<Cipher>> GetByUserIdAsync(int userId);
    Task<Cipher?> GetByKeyNameAsync(int userId, string keyName);
    Task<Cipher> CreateAsync(Cipher cipher);
    Task UpdateAsync(Cipher cipher);
    Task DeleteAsync(int id);
}

