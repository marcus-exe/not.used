using Microsoft.EntityFrameworkCore;
using TechKnowledgePills.Core.Entities;
using TechKnowledgePills.Core.Enums;
using TechKnowledgePills.Core.Interfaces;
using TechKnowledgePills.Infrastructure.Data;

namespace TechKnowledgePills.Infrastructure.Repositories;

public class ContentRepository : IContentRepository
{
    private readonly ApplicationDbContext _context;

    public ContentRepository(ApplicationDbContext context)
    {
        _context = context;
    }

    public async Task<IEnumerable<Content>> GetAllAsync()
    {
        return await _context.Contents
            .OrderByDescending(c => c.CreatedAt)
            .ToListAsync();
    }

    public async Task<Content?> GetByIdAsync(int id)
    {
        return await _context.Contents.FindAsync(id);
    }

    public async Task<IEnumerable<Content>> GetByTypeAsync(ContentType type)
    {
        return await _context.Contents
            .Where(c => c.Type == type)
            .OrderByDescending(c => c.CreatedAt)
            .ToListAsync();
    }

    public async Task<IEnumerable<Content>> GetRecommendedAsync(int userId, StressLevel stressLevel)
    {
        // Get content that user hasn't interacted with yet
        var interactedContentIds = await _context.UserContentInteractions
            .Where(uci => uci.UserId == userId)
            .Select(uci => uci.ContentId)
            .ToListAsync();

        var query = _context.Contents
            .Where(c => !interactedContentIds.Contains(c.Id));

        // For high stress, recommend calming/educational content
        // For low stress, recommend more challenging content
        if (stressLevel >= StressLevel.High)
        {
            // Prefer articles and videos for high stress
            query = query.Where(c => c.Type == ContentType.Article || c.Type == ContentType.Video);
        }

        return await query
            .OrderByDescending(c => c.CreatedAt)
            .Take(10)
            .ToListAsync();
    }

    public async Task<Content> CreateAsync(Content content)
    {
        _context.Contents.Add(content);
        await _context.SaveChangesAsync();
        return content;
    }

    public async Task UpdateAsync(Content content)
    {
        _context.Contents.Update(content);
        await _context.SaveChangesAsync();
    }

    public async Task DeleteAsync(int id)
    {
        var content = await _context.Contents.FindAsync(id);
        if (content != null)
        {
            _context.Contents.Remove(content);
            await _context.SaveChangesAsync();
        }
    }
}

