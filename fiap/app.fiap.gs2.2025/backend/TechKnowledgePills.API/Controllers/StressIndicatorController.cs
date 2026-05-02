using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using System.Security.Claims;
using TechKnowledgePills.Core.DTOs;
using TechKnowledgePills.Core.Entities;
using TechKnowledgePills.Core.Enums;
using TechKnowledgePills.Core.Interfaces;

namespace TechKnowledgePills.API.Controllers;

[ApiController]
[Route("api/[controller]")]
[Authorize]
public class StressIndicatorController : ControllerBase
{
    private readonly IStressIndicatorRepository _stressIndicatorRepository;

    public StressIndicatorController(IStressIndicatorRepository stressIndicatorRepository)
    {
        _stressIndicatorRepository = stressIndicatorRepository;
    }

    [HttpGet]
    public async Task<ActionResult<IEnumerable<StressIndicatorDto>>> GetUserStressIndicators()
    {
        var userIdClaim = User.FindFirst(ClaimTypes.NameIdentifier);
        if (userIdClaim == null || !int.TryParse(userIdClaim.Value, out var userId))
        {
            return Unauthorized();
        }

        var indicators = await _stressIndicatorRepository.GetByUserIdAsync(userId);
        var dtos = indicators.Select(i => new StressIndicatorDto
        {
            Id = i.Id,
            UserId = i.UserId,
            StressLevel = i.StressLevel,
            Timestamp = i.Timestamp,
            Notes = i.Notes
        });

        return Ok(dtos);
    }

    [HttpGet("latest")]
    public async Task<ActionResult<StressIndicatorDto>> GetLatest()
    {
        var userIdClaim = User.FindFirst(ClaimTypes.NameIdentifier);
        if (userIdClaim == null || !int.TryParse(userIdClaim.Value, out var userId))
        {
            return Unauthorized();
        }

        var indicator = await _stressIndicatorRepository.GetLatestByUserIdAsync(userId);
        if (indicator == null)
        {
            return NotFound();
        }

        var dto = new StressIndicatorDto
        {
            Id = indicator.Id,
            UserId = indicator.UserId,
            StressLevel = indicator.StressLevel,
            Timestamp = indicator.Timestamp,
            Notes = indicator.Notes
        };

        return Ok(dto);
    }

    [HttpPost("generate-mock")]
    public async Task<ActionResult<IEnumerable<StressIndicatorDto>>> GenerateMockData([FromQuery] int count = 30)
    {
        var userIdClaim = User.FindFirst(ClaimTypes.NameIdentifier);
        if (userIdClaim == null || !int.TryParse(userIdClaim.Value, out var userId))
        {
            return Unauthorized();
        }

        var indicators = await _stressIndicatorRepository.GenerateMockDataAsync(userId, count);
        var dtos = indicators.Select(i => new StressIndicatorDto
        {
            Id = i.Id,
            UserId = i.UserId,
            StressLevel = i.StressLevel,
            Timestamp = i.Timestamp,
            Notes = i.Notes
        });

        return Ok(dtos);
    }

    [HttpPost]
    public async Task<ActionResult<StressIndicatorDto>> Create([FromBody] StressIndicatorDto dto)
    {
        var userIdClaim = User.FindFirst(ClaimTypes.NameIdentifier);
        if (userIdClaim == null || !int.TryParse(userIdClaim.Value, out var userId))
        {
            return Unauthorized();
        }

        var indicator = new StressIndicator
        {
            UserId = userId,
            StressLevel = dto.StressLevel,
            Timestamp = dto.Timestamp,
            Notes = dto.Notes
        };

        indicator = await _stressIndicatorRepository.CreateAsync(indicator);

        var responseDto = new StressIndicatorDto
        {
            Id = indicator.Id,
            UserId = indicator.UserId,
            StressLevel = indicator.StressLevel,
            Timestamp = indicator.Timestamp,
            Notes = indicator.Notes
        };

        return CreatedAtAction(nameof(GetLatest), responseDto);
    }
}

