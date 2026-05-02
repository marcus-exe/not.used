using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using System.Security.Claims;
using TechKnowledgePills.Core.DTOs;
using TechKnowledgePills.Core.Entities;
using TechKnowledgePills.Core.Interfaces;

namespace TechKnowledgePills.API.Controllers;

[ApiController]
[Route("api/[controller]")]
public class HealthMetricController : ControllerBase
{
    private readonly IHealthMetricRepository _healthMetricRepository;
    private readonly IHealthAnalysisService _healthAnalysisService;
    private readonly ILogger<HealthMetricController> _logger;

    public HealthMetricController(
        IHealthMetricRepository healthMetricRepository,
        IHealthAnalysisService healthAnalysisService,
        ILogger<HealthMetricController> logger)
    {
        _healthMetricRepository = healthMetricRepository;
        _healthAnalysisService = healthAnalysisService;
        _logger = logger;
    }

    /// <summary>
    /// Endpoint for IoT devices to submit health data
    /// Uses API key authentication (can be extended to use device tokens)
    /// </summary>
    [HttpPost("iot")]
    [AllowAnonymous] // Allow anonymous for IoT devices, but you can add API key validation
    public async Task<ActionResult<HealthMetricDto>> SubmitIoTData([FromBody] IoTHealthDataDto dto)
    {
        try
        {
            // Validate required fields
            if (dto.UserId <= 0)
            {
                return BadRequest("UserId is required and must be greater than 0");
            }

            var healthMetric = new HealthMetric
            {
                UserId = dto.UserId,
                Timestamp = dto.Timestamp ?? DateTime.UtcNow,
                HeartRate = dto.HeartRate,
                Steps = dto.Steps,
                SleepHours = dto.SleepHours,
                HeartRateVariability = dto.HeartRateVariability,
                BodyTemperature = dto.BodyTemperature,
                DeviceId = dto.DeviceId,
                DeviceType = dto.DeviceType ?? "unknown"
            };

            healthMetric = await _healthMetricRepository.CreateAsync(healthMetric);

            // Automatically analyze and create stress indicator
            try
            {
                await _healthAnalysisService.AnalyzeHealthMetricsAndCreateStressIndicatorAsync(
                    dto.UserId, healthMetric);
                _logger.LogInformation("Created stress indicator from health metrics for user {UserId}", dto.UserId);
            }
            catch (Exception ex)
            {
                _logger.LogWarning(ex, "Failed to create stress indicator from health metrics");
                // Don't fail the request if stress indicator creation fails
            }

            var responseDto = new HealthMetricDto
            {
                Id = healthMetric.Id,
                UserId = healthMetric.UserId,
                Timestamp = healthMetric.Timestamp,
                HeartRate = healthMetric.HeartRate,
                Steps = healthMetric.Steps,
                SleepHours = healthMetric.SleepHours,
                HeartRateVariability = healthMetric.HeartRateVariability,
                BodyTemperature = healthMetric.BodyTemperature,
                DeviceId = healthMetric.DeviceId,
                DeviceType = healthMetric.DeviceType
            };

            return Ok(responseDto);
        }
        catch (Exception ex)
        {
            _logger.LogError(ex, "Error processing IoT health data");
            return StatusCode(500, "An error occurred while processing health data");
        }
    }

    [HttpGet]
    [Authorize]
    public async Task<ActionResult<IEnumerable<HealthMetricDto>>> GetUserHealthMetrics(
        [FromQuery] DateTime? startDate = null,
        [FromQuery] DateTime? endDate = null)
    {
        var userIdClaim = User.FindFirst(ClaimTypes.NameIdentifier);
        if (userIdClaim == null || !int.TryParse(userIdClaim.Value, out var userId))
        {
            return Unauthorized();
        }

        var metrics = await _healthMetricRepository.GetByUserIdAsync(userId, startDate, endDate);
        var dtos = metrics.Select(m => new HealthMetricDto
        {
            Id = m.Id,
            UserId = m.UserId,
            Timestamp = m.Timestamp,
            HeartRate = m.HeartRate,
            Steps = m.Steps,
            SleepHours = m.SleepHours,
            HeartRateVariability = m.HeartRateVariability,
            BodyTemperature = m.BodyTemperature,
            DeviceId = m.DeviceId,
            DeviceType = m.DeviceType
        });

        return Ok(dtos);
    }

    [HttpGet("latest")]
    [Authorize]
    public async Task<ActionResult<HealthMetricDto>> GetLatest()
    {
        var userIdClaim = User.FindFirst(ClaimTypes.NameIdentifier);
        if (userIdClaim == null || !int.TryParse(userIdClaim.Value, out var userId))
        {
            return Unauthorized();
        }

        var metric = await _healthMetricRepository.GetLatestByUserIdAsync(userId);
        if (metric == null)
        {
            return NotFound();
        }

        var dto = new HealthMetricDto
        {
            Id = metric.Id,
            UserId = metric.UserId,
            Timestamp = metric.Timestamp,
            HeartRate = metric.HeartRate,
            Steps = metric.Steps,
            SleepHours = metric.SleepHours,
            HeartRateVariability = metric.HeartRateVariability,
            BodyTemperature = metric.BodyTemperature,
            DeviceId = metric.DeviceId,
            DeviceType = metric.DeviceType
        };

        return Ok(dto);
    }
}

