using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.Mvc.Testing;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Npgsql;
using NpgsqlTypes;
using System.Data;
using System.Threading;
using TechKnowledgePills.Infrastructure.Data;

namespace TechKnowledgePills.Tests.Support;

public class TestWebApplicationFactory : WebApplicationFactory<Program>
{
    private static readonly string TestConnectionString = 
        Environment.GetEnvironmentVariable("TEST_DB_CONNECTION") ?? 
        "Host=localhost;Port=5433;Database=techknowledgepills_test;Username=postgres;Password=postgres";

    protected override void ConfigureWebHost(IWebHostBuilder builder)
    {
        builder.ConfigureAppConfiguration((context, config) =>
        {
            // Override configuration for testing
            config.AddInMemoryCollection(new Dictionary<string, string?>
            {
                { "Jwt:Key", "YourSuperSecretKeyThatShouldBeAtLeast32CharactersLong!" },
                { "Jwt:Issuer", "TechKnowledgePills" },
                { "Jwt:Audience", "TechKnowledgePillsUsers" },
                { "ConnectionStrings:DefaultConnection", TestConnectionString }
            });
        });

        builder.ConfigureServices(services =>
        {
            // Remove the real database
            var descriptor = services.SingleOrDefault(
                d => d.ServiceType == typeof(DbContextOptions<ApplicationDbContext>));

            if (descriptor != null)
            {
                services.Remove(descriptor);
            }

            // Use PostgreSQL for testing (Docker)
            var useDocker = Environment.GetEnvironmentVariable("USE_DOCKER_DB")?.ToLower() == "true" ||
                           !string.IsNullOrEmpty(Environment.GetEnvironmentVariable("TEST_DB_CONNECTION"));

            if (useDocker)
            {
                // Use PostgreSQL from Docker
                services.AddDbContext<ApplicationDbContext>(options =>
                {
                    options.UseNpgsql(TestConnectionString, npgsqlOptions =>
                    {
                        npgsqlOptions.EnableRetryOnFailure(
                            maxRetryCount: 3,
                            maxRetryDelay: TimeSpan.FromSeconds(5),
                            errorCodesToAdd: null);
                    });
                });
            }
            else
            {
                // Fallback to in-memory database
                services.AddDbContext<ApplicationDbContext>(options =>
                {
                    options.UseInMemoryDatabase($"TestDb_{Guid.NewGuid()}");
                });
            }

            EnsureDatabaseReady(services, useDocker);
        });
    }

    private static void EnsureDatabaseReady(IServiceCollection services, bool useDocker)
    {
        using var scope = services.BuildServiceProvider().CreateScope();
        var db = scope.ServiceProvider.GetRequiredService<ApplicationDbContext>();

        void RecreateDatabase()
        {
            // Always use EnsureCreated for tests - faster and avoids migration conflicts
            db.Database.EnsureCreated();
        }

        try
        {
            if (useDocker)
            {
                ForceDropDatabase(TestConnectionString);
            }
            else
            {
                TryEnsureDeleted(db);
            }

            RecreateDatabase();
        }
        catch
        {
            if (!useDocker)
            {
                throw;
            }

            // If Postgres container is still initializing, wait briefly and retry once
            Thread.Sleep(TimeSpan.FromSeconds(2));

            if (useDocker)
            {
                ForceDropDatabase(TestConnectionString);
            }
            else
            {
                TryEnsureDeleted(db);
            }

            RecreateDatabase();
        }
    }

    private static void TryEnsureDeleted(ApplicationDbContext db)
    {
        try
        {
            db.Database.EnsureDeleted();
        }
        catch (PostgresException ex) when (ex.SqlState == "3D000")
        {
            // Database did not exist yet – safe to ignore
        }
        catch (InvalidOperationException ex) when (ex.InnerException is PostgresException { SqlState: "3D000" })
        {
            // Same as above but wrapped by EF
        }
    }

    private static void ForceDropDatabase(string connectionString)
    {
        var builder = new NpgsqlConnectionStringBuilder(connectionString);
        var databaseName = builder.Database;

        builder.Database = "postgres";
        builder.Pooling = false;

        using var connection = new NpgsqlConnection(builder.ConnectionString);
        connection.Open();

        using (var terminateCmd = connection.CreateCommand())
        {
            terminateCmd.CommandText = @"
                SELECT pg_terminate_backend(pid)
                FROM pg_stat_activity
                WHERE datname = @dbName AND pid <> pg_backend_pid();";
            terminateCmd.Parameters.Add(new NpgsqlParameter("@dbName", NpgsqlTypes.NpgsqlDbType.Varchar) { Value = databaseName });
            terminateCmd.ExecuteNonQuery();
        }

        using (var dropCmd = connection.CreateCommand())
        {
            dropCmd.CommandText = $"DROP DATABASE IF EXISTS \"{databaseName}\" WITH (FORCE);";
            dropCmd.ExecuteNonQuery();
        }
    }

    protected override void Dispose(bool disposing)
    {
        if (disposing)
        {
            // Clean up test database
            try
            {
                using var scope = Services.CreateScope();
                var db = scope.ServiceProvider.GetRequiredService<ApplicationDbContext>();
                db.Database.EnsureDeleted();
            }
            catch
            {
                // Ignore cleanup errors
            }
        }
        base.Dispose(disposing);
    }
}

