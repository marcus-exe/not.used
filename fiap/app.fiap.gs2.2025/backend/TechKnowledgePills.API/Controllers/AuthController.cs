using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using TechKnowledgePills.Core.DTOs;
using TechKnowledgePills.Core.Interfaces;
using BCrypt.Net;

namespace TechKnowledgePills.API.Controllers;

[ApiController]
[Route("api/[controller]")]
public class AuthController : ControllerBase
{
    private readonly IUserRepository _userRepository;
    private readonly IJwtService _jwtService;

    public AuthController(IUserRepository userRepository, IJwtService jwtService)
    {
        _userRepository = userRepository;
        _jwtService = jwtService;
    }

    [HttpPost("register")]
    public async Task<ActionResult<AuthResponse>> Register([FromBody] RegisterRequest request)
    {
        if (!ModelState.IsValid)
        {
            return BadRequest(ModelState);
        }

        var existingUser = await _userRepository.GetByEmailAsync(request.Email);
        if (existingUser != null)
        {
            return BadRequest(new { message = "Email already registered" });
        }

        var user = new TechKnowledgePills.Core.Entities.User
        {
            Email = request.Email,
            PasswordHash = BCrypt.Net.BCrypt.HashPassword(request.Password),
            CreatedAt = DateTime.UtcNow
        };

        user = await _userRepository.CreateAsync(user);

        var token = _jwtService.GenerateToken(user);
        var refreshToken = _jwtService.GenerateRefreshToken();

        var response = new AuthResponse
        {
            Token = token,
            RefreshToken = refreshToken,
            ExpiresAt = DateTime.UtcNow.AddHours(24),
            UserId = user.Id,
            Email = user.Email
        };

        return Ok(response);
    }

    [HttpPost("login")]
    public async Task<ActionResult<AuthResponse>> Login([FromBody] LoginRequest request)
    {
        if (!ModelState.IsValid)
        {
            return BadRequest(ModelState);
        }

        var user = await _userRepository.GetByEmailAsync(request.Email);
        if (user == null || !BCrypt.Net.BCrypt.Verify(request.Password, user.PasswordHash))
        {
            return Unauthorized(new { message = "Invalid email or password" });
        }

        user.LastLogin = DateTime.UtcNow;
        await _userRepository.UpdateAsync(user);

        var token = _jwtService.GenerateToken(user);
        var refreshToken = _jwtService.GenerateRefreshToken();

        var response = new AuthResponse
        {
            Token = token,
            RefreshToken = refreshToken,
            ExpiresAt = DateTime.UtcNow.AddHours(24),
            UserId = user.Id,
            Email = user.Email
        };

        return Ok(response);
    }

    [Authorize]
    [HttpGet("me")]
    public async Task<ActionResult> GetCurrentUser()
    {
        var userIdClaim = User.FindFirst(System.Security.Claims.ClaimTypes.NameIdentifier);
        if (userIdClaim == null || !int.TryParse(userIdClaim.Value, out var userId))
        {
            return Unauthorized();
        }

        var user = await _userRepository.GetByIdAsync(userId);
        if (user == null)
        {
            return NotFound();
        }

        return Ok(new { Id = user.Id, Email = user.Email, CreatedAt = user.CreatedAt, LastLogin = user.LastLogin });
    }
}

