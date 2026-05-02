using Microsoft.EntityFrameworkCore;
using TechKnowledgePills.Core.Entities;

namespace TechKnowledgePills.Infrastructure.Data;

public class ApplicationDbContext : DbContext
{
    public ApplicationDbContext(DbContextOptions<ApplicationDbContext> options)
        : base(options)
    {
    }

    public DbSet<User> Users { get; set; }
    public DbSet<Content> Contents { get; set; }
    public DbSet<StressIndicator> StressIndicators { get; set; }
    public DbSet<UserContentInteraction> UserContentInteractions { get; set; }
    public DbSet<HealthMetric> HealthMetrics { get; set; }
    public DbSet<Cipher> Ciphers { get; set; }

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        base.OnModelCreating(modelBuilder);

        modelBuilder.Entity<User>(entity =>
        {
            entity.HasKey(e => e.Id);
            entity.HasIndex(e => e.Email).IsUnique();
            entity.Property(e => e.Email).IsRequired().HasMaxLength(255);
            entity.Property(e => e.PasswordHash).IsRequired();
        });

        modelBuilder.Entity<Content>(entity =>
        {
            entity.HasKey(e => e.Id);
            entity.Property(e => e.Title).IsRequired().HasMaxLength(255);
            entity.Property(e => e.Type).IsRequired();
        });

        modelBuilder.Entity<StressIndicator>(entity =>
        {
            entity.HasKey(e => e.Id);
            entity.HasOne(e => e.User)
                .WithMany(u => u.StressIndicators)
                .HasForeignKey(e => e.UserId)
                .OnDelete(DeleteBehavior.Cascade);
        });

        modelBuilder.Entity<UserContentInteraction>(entity =>
        {
            entity.HasKey(e => e.Id);
            entity.HasOne(e => e.User)
                .WithMany(u => u.UserContentInteractions)
                .HasForeignKey(e => e.UserId)
                .OnDelete(DeleteBehavior.Cascade);
            entity.HasOne(e => e.Content)
                .WithMany(c => c.UserContentInteractions)
                .HasForeignKey(e => e.ContentId)
                .OnDelete(DeleteBehavior.Cascade);
        });

        modelBuilder.Entity<HealthMetric>(entity =>
        {
            entity.HasKey(e => e.Id);
            entity.HasOne(e => e.User)
                .WithMany(u => u.HealthMetrics)
                .HasForeignKey(e => e.UserId)
                .OnDelete(DeleteBehavior.Cascade);
            entity.HasIndex(e => e.DeviceId);
            entity.HasIndex(e => new { e.UserId, e.Timestamp });
        });

        modelBuilder.Entity<Cipher>(entity =>
        {
            entity.HasKey(e => e.Id);
            entity.HasOne(e => e.User)
                .WithMany(u => u.Ciphers)
                .HasForeignKey(e => e.UserId)
                .OnDelete(DeleteBehavior.Cascade);
            entity.Property(e => e.KeyName).IsRequired().HasMaxLength(255);
            entity.Property(e => e.EncryptedData).IsRequired();
            entity.Property(e => e.Algorithm).IsRequired().HasMaxLength(50);
            entity.HasIndex(e => new { e.UserId, e.KeyName });
            entity.HasIndex(e => e.UserId);
        });
    }
}

