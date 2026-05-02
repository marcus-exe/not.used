using Microsoft.EntityFrameworkCore;
using TechKnowledgePills.Core.Entities;
using TechKnowledgePills.Core.Interfaces;
using TechKnowledgePills.Infrastructure.Data;

namespace TechKnowledgePills.Infrastructure.Repositories;

public class CipherRepository : ICipherRepository
{
    private readonly ApplicationDbContext _context;

    public CipherRepository(ApplicationDbContext context)
    {
        _context = context;
    }

    public async Task<Cipher?> GetByIdAsync(int id)
    {
        return await _context.Ciphers.FindAsync(id);
    }

    public async Task<Cipher?> GetByIdAndUserIdAsync(int id, int userId)
    {
        return await _context.Ciphers
            .FirstOrDefaultAsync(c => c.Id == id && c.UserId == userId);
    }

    public async Task<IEnumerable<Cipher>> GetByUserIdAsync(int userId)
    {
        return await _context.Ciphers
            .Where(c => c.UserId == userId)
            .OrderByDescending(c => c.CreatedAt)
            .ToListAsync();
    }

    public async Task<Cipher?> GetByKeyNameAsync(int userId, string keyName)
    {
        return await _context.Ciphers
            .FirstOrDefaultAsync(c => c.UserId == userId && c.KeyName == keyName);
    }

    public async Task<Cipher> CreateAsync(Cipher cipher)
    {
        _context.Ciphers.Add(cipher);
        await _context.SaveChangesAsync();
        return cipher;
    }

    public async Task UpdateAsync(Cipher cipher)
    {
        _context.Ciphers.Update(cipher);
        await _context.SaveChangesAsync();
    }

    public async Task DeleteAsync(int id)
    {
        var cipher = await _context.Ciphers.FindAsync(id);
        if (cipher != null)
        {
            _context.Ciphers.Remove(cipher);
            await _context.SaveChangesAsync();
        }
    }
}

