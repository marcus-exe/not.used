using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using System.Security.Claims;
using TechKnowledgePills.Core.DTOs;
using TechKnowledgePills.Core.Interfaces;

namespace TechKnowledgePills.API.Controllers;

[ApiController]
[Route("api/[controller]")]
[Authorize]
public class RecommendationController : ControllerBase
{
    private readonly IRecommendationService _recommendationService;
    private readonly IStressIndicatorRepository _stressIndicatorRepository;

    public RecommendationController(
        IRecommendationService recommendationService,
        IStressIndicatorRepository stressIndicatorRepository)
    {
        _recommendationService = recommendationService;
        _stressIndicatorRepository = stressIndicatorRepository;
    }

    [HttpGet]
    public async Task<ActionResult<IEnumerable<ContentDto>>> GetRecommendations()
    {
        var userIdClaim = User.FindFirst(ClaimTypes.NameIdentifier);
        if (userIdClaim == null || !int.TryParse(userIdClaim.Value, out var userId))
        {
            return Unauthorized();
        }

        // Get latest stress level
        var latestStress = await _stressIndicatorRepository.GetLatestByUserIdAsync(userId);
        var stressLevel = latestStress?.StressLevel ?? Core.Enums.StressLevel.Medium;

        var recommendations = await _recommendationService.GetRecommendationsAsync(userId, stressLevel);
        
        var dtos = recommendations.Select(c => new ContentDto
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
}

