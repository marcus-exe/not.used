using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using TechKnowledgePills.Core.DTOs;
using TechKnowledgePills.Core.Entities;
using TechKnowledgePills.Core.Interfaces;

namespace TechKnowledgePills.API.Controllers;

[ApiController]
[Route("api/[controller]")]
[Authorize]
public class CipherController : ControllerBase
{
    private readonly ICipherRepository _cipherRepository;

    public CipherController(ICipherRepository cipherRepository)
    {
        _cipherRepository = cipherRepository;
    }

    private int GetCurrentUserId()
    {
        var userIdClaim = User.FindFirst(System.Security.Claims.ClaimTypes.NameIdentifier);
        if (userIdClaim == null || !int.TryParse(userIdClaim.Value, out var userId))
        {
            throw new UnauthorizedAccessException("Invalid user token");
        }
        return userId;
    }

    [HttpGet]
    public async Task<ActionResult<IEnumerable<CipherDto>>> GetAll()
    {
        var userId = GetCurrentUserId();
        var ciphers = await _cipherRepository.GetByUserIdAsync(userId);
        var dtos = ciphers.Select(c => new CipherDto
        {
            Id = c.Id,
            UserId = c.UserId,
            KeyName = c.KeyName,
            EncryptedData = c.EncryptedData,
            Description = c.Description,
            Algorithm = c.Algorithm,
            CreatedAt = c.CreatedAt,
            UpdatedAt = c.UpdatedAt,
            IsActive = c.IsActive
        });

        return Ok(dtos);
    }

    [HttpGet("{id}")]
    public async Task<ActionResult<CipherDto>> GetById(int id)
    {
        var userId = GetCurrentUserId();
        var cipher = await _cipherRepository.GetByIdAndUserIdAsync(id, userId);
        if (cipher == null)
        {
            return NotFound();
        }

        var dto = new CipherDto
        {
            Id = cipher.Id,
            UserId = cipher.UserId,
            KeyName = cipher.KeyName,
            EncryptedData = cipher.EncryptedData,
            Description = cipher.Description,
            Algorithm = cipher.Algorithm,
            CreatedAt = cipher.CreatedAt,
            UpdatedAt = cipher.UpdatedAt,
            IsActive = cipher.IsActive
        };

        return Ok(dto);
    }

    [HttpGet("key/{keyName}")]
    public async Task<ActionResult<CipherDto>> GetByKeyName(string keyName)
    {
        var userId = GetCurrentUserId();
        var cipher = await _cipherRepository.GetByKeyNameAsync(userId, keyName);
        if (cipher == null)
        {
            return NotFound();
        }

        var dto = new CipherDto
        {
            Id = cipher.Id,
            UserId = cipher.UserId,
            KeyName = cipher.KeyName,
            EncryptedData = cipher.EncryptedData,
            Description = cipher.Description,
            Algorithm = cipher.Algorithm,
            CreatedAt = cipher.CreatedAt,
            UpdatedAt = cipher.UpdatedAt,
            IsActive = cipher.IsActive
        };

        return Ok(dto);
    }

    [HttpPost]
    public async Task<ActionResult<CipherDto>> Create([FromBody] CreateCipherRequest request)
    {
        if (!ModelState.IsValid)
        {
            return BadRequest(ModelState);
        }

        var userId = GetCurrentUserId();

        // Check if key name already exists for this user
        var existingCipher = await _cipherRepository.GetByKeyNameAsync(userId, request.KeyName);
        if (existingCipher != null)
        {
            return BadRequest(new { message = "A cipher with this key name already exists" });
        }

        var cipher = new Cipher
        {
            UserId = userId,
            KeyName = request.KeyName,
            EncryptedData = request.EncryptedData,
            Description = request.Description,
            Algorithm = request.Algorithm,
            CreatedAt = DateTime.UtcNow,
            IsActive = true
        };

        cipher = await _cipherRepository.CreateAsync(cipher);

        var dto = new CipherDto
        {
            Id = cipher.Id,
            UserId = cipher.UserId,
            KeyName = cipher.KeyName,
            EncryptedData = cipher.EncryptedData,
            Description = cipher.Description,
            Algorithm = cipher.Algorithm,
            CreatedAt = cipher.CreatedAt,
            UpdatedAt = cipher.UpdatedAt,
            IsActive = cipher.IsActive
        };

        return CreatedAtAction(nameof(GetById), new { id = cipher.Id }, dto);
    }

    [HttpPut("{id}")]
    public async Task<ActionResult<CipherDto>> Update(int id, [FromBody] UpdateCipherRequest request)
    {
        var userId = GetCurrentUserId();
        var cipher = await _cipherRepository.GetByIdAndUserIdAsync(id, userId);
        if (cipher == null)
        {
            return NotFound();
        }

        // If key name is being updated, check if it conflicts with another cipher
        if (!string.IsNullOrEmpty(request.KeyName) && request.KeyName != cipher.KeyName)
        {
            var existingCipher = await _cipherRepository.GetByKeyNameAsync(userId, request.KeyName);
            if (existingCipher != null && existingCipher.Id != id)
            {
                return BadRequest(new { message = "A cipher with this key name already exists" });
            }
            cipher.KeyName = request.KeyName;
        }

        if (!string.IsNullOrEmpty(request.EncryptedData))
        {
            cipher.EncryptedData = request.EncryptedData;
        }

        if (request.Description != null)
        {
            cipher.Description = request.Description;
        }

        if (!string.IsNullOrEmpty(request.Algorithm))
        {
            cipher.Algorithm = request.Algorithm;
        }

        if (request.IsActive.HasValue)
        {
            cipher.IsActive = request.IsActive.Value;
        }

        cipher.UpdatedAt = DateTime.UtcNow;
        await _cipherRepository.UpdateAsync(cipher);

        var dto = new CipherDto
        {
            Id = cipher.Id,
            UserId = cipher.UserId,
            KeyName = cipher.KeyName,
            EncryptedData = cipher.EncryptedData,
            Description = cipher.Description,
            Algorithm = cipher.Algorithm,
            CreatedAt = cipher.CreatedAt,
            UpdatedAt = cipher.UpdatedAt,
            IsActive = cipher.IsActive
        };

        return Ok(dto);
    }

    [HttpDelete("{id}")]
    public async Task<IActionResult> Delete(int id)
    {
        var userId = GetCurrentUserId();
        var cipher = await _cipherRepository.GetByIdAndUserIdAsync(id, userId);
        if (cipher == null)
        {
            return NotFound();
        }

        await _cipherRepository.DeleteAsync(id);
        return NoContent();
    }
}

