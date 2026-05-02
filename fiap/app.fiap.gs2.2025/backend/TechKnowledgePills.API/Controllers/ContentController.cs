using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using TechKnowledgePills.Core.DTOs;
using TechKnowledgePills.Core.Entities;
using TechKnowledgePills.Core.Enums;
using TechKnowledgePills.Core.Interfaces;

namespace TechKnowledgePills.API.Controllers;

[ApiController]
[Route("api/[controller]")]
[Authorize]
public class ContentController : ControllerBase
{
    private readonly IContentRepository _contentRepository;

    public ContentController(IContentRepository contentRepository)
    {
        _contentRepository = contentRepository;
    }

    [HttpGet]
    public async Task<ActionResult<IEnumerable<ContentDto>>> GetAll()
    {
        var contents = await _contentRepository.GetAllAsync();
        var dtos = contents.Select(c => new ContentDto
        {
            Id = c.Id,
            Title = c.Title,
            Type = c.Type,
            Body = c.Body,
            VideoUrl = c.VideoUrl,
            QuizData = c.QuizData,
            CreatedAt = c.CreatedAt,
            Tags = c.Tags
        });

        return Ok(dtos);
    }

    [HttpGet("{id}")]
    public async Task<ActionResult<ContentDto>> GetById(int id)
    {
        var content = await _contentRepository.GetByIdAsync(id);
        if (content == null)
        {
            return NotFound();
        }

        var dto = new ContentDto
        {
            Id = content.Id,
            Title = content.Title,
            Type = content.Type,
            Body = content.Body,
            VideoUrl = content.VideoUrl,
            QuizData = content.QuizData,
            CreatedAt = content.CreatedAt,
            Tags = content.Tags
        };

        return Ok(dto);
    }

    [HttpGet("type/{type}")]
    public async Task<ActionResult<IEnumerable<ContentDto>>> GetByType(ContentType type)
    {
        var contents = await _contentRepository.GetByTypeAsync(type);
        var dtos = contents.Select(c => new ContentDto
        {
            Id = c.Id,
            Title = c.Title,
            Type = c.Type,
            Body = c.Body,
            VideoUrl = c.VideoUrl,
            QuizData = c.QuizData,
            CreatedAt = c.CreatedAt,
            Tags = c.Tags
        });

        return Ok(dtos);
    }

    [HttpPost]
    public async Task<ActionResult<ContentDto>> Create([FromBody] ContentDto dto)
    {
        var content = new Content
        {
            Title = dto.Title,
            Type = dto.Type,
            Body = dto.Body,
            VideoUrl = dto.VideoUrl,
            QuizData = dto.QuizData,
            Tags = dto.Tags,
            CreatedAt = DateTime.UtcNow
        };

        content = await _contentRepository.CreateAsync(content);

        var responseDto = new ContentDto
        {
            Id = content.Id,
            Title = content.Title,
            Type = content.Type,
            Body = content.Body,
            VideoUrl = content.VideoUrl,
            QuizData = content.QuizData,
            CreatedAt = content.CreatedAt,
            Tags = content.Tags
        };

        return CreatedAtAction(nameof(GetById), new { id = content.Id }, responseDto);
    }

    [HttpPut("{id}")]
    public async Task<ActionResult<ContentDto>> Update(int id, [FromBody] ContentDto dto)
    {
        var content = await _contentRepository.GetByIdAsync(id);
        if (content == null)
        {
            return NotFound();
        }

        content.Title = dto.Title;
        content.Type = dto.Type;
        content.Body = dto.Body;
        content.VideoUrl = dto.VideoUrl;
        content.QuizData = dto.QuizData;
        content.Tags = dto.Tags;

        await _contentRepository.UpdateAsync(content);

        var responseDto = new ContentDto
        {
            Id = content.Id,
            Title = content.Title,
            Type = content.Type,
            Body = content.Body,
            VideoUrl = content.VideoUrl,
            QuizData = content.QuizData,
            CreatedAt = content.CreatedAt,
            Tags = content.Tags
        };

        return Ok(responseDto);
    }

    [HttpDelete("{id}")]
    public async Task<IActionResult> Delete(int id)
    {
        await _contentRepository.DeleteAsync(id);
        return NoContent();
    }
}

