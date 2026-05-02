using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.EntityFrameworkCore;
using Microsoft.IdentityModel.Tokens;
using Microsoft.OpenApi.Models;
using System.Linq;
using System.Text;
using System.Text.Json;
using TechKnowledgePills.Core.Entities;
using TechKnowledgePills.Core.Enums;
using TechKnowledgePills.Core.Interfaces;
using TechKnowledgePills.Infrastructure.Data;
using TechKnowledgePills.Infrastructure.Repositories;
using TechKnowledgePills.Infrastructure.Services;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container
builder.Services.AddControllers();
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen(c =>
{
    c.SwaggerDoc("v1", new OpenApiInfo { Title = "Tech Knowledge Pills API", Version = "v1" });
    
    // Add JWT authentication to Swagger
    c.AddSecurityDefinition("Bearer", new OpenApiSecurityScheme
    {
        Description = "JWT Authorization header using the Bearer scheme. Enter 'Bearer' [space] and then your token",
        Name = "Authorization",
        In = ParameterLocation.Header,
        Type = SecuritySchemeType.ApiKey,
        Scheme = "Bearer"
    });
    
    c.AddSecurityRequirement(new OpenApiSecurityRequirement
    {
        {
            new OpenApiSecurityScheme
            {
                Reference = new OpenApiReference
                {
                    Type = ReferenceType.SecurityScheme,
                    Id = "Bearer"
                }
            },
            Array.Empty<string>()
        }
    });
});

// Database
builder.Services.AddDbContext<ApplicationDbContext>(options =>
    options.UseNpgsql(builder.Configuration.GetConnectionString("DefaultConnection")));

// JWT Authentication
var jwtKey = builder.Configuration["Jwt:Key"] ?? throw new InvalidOperationException("JWT Key not configured");
var jwtIssuer = builder.Configuration["Jwt:Issuer"] ?? throw new InvalidOperationException("JWT Issuer not configured");
var jwtAudience = builder.Configuration["Jwt:Audience"] ?? throw new InvalidOperationException("JWT Audience not configured");

builder.Services.AddAuthentication(JwtBearerDefaults.AuthenticationScheme)
    .AddJwtBearer(options =>
    {
        options.TokenValidationParameters = new TokenValidationParameters
        {
            ValidateIssuerSigningKey = true,
            IssuerSigningKey = new SymmetricSecurityKey(Encoding.UTF8.GetBytes(jwtKey)),
            ValidateIssuer = true,
            ValidIssuer = jwtIssuer,
            ValidateAudience = true,
            ValidAudience = jwtAudience,
            ValidateLifetime = true,
            ClockSkew = TimeSpan.Zero
        };
    });

builder.Services.AddAuthorization();

// CORS
builder.Services.AddCors(options =>
{
    options.AddPolicy("AllowAndroidApp", policy =>
    {
        policy.WithOrigins(
                "http://localhost:8080",
                "http://10.0.2.2:5001",  // Android emulator
                "http://localhost:5001",
                "http://127.0.0.1:5001",
                "http://192.168.0.137:5001",  // Physical device - update with your machine's IP
                "http://192.168.31.119:5001",  // Device IP - update if needed
                "http://192.168.15.115:5001",  // Current machine IP (primary network)
                "http://192.168.139.3:5001",   // Alternative machine IP
                "http://192.168.147.0:5001",   // Alternative machine IP
                "http://192.168.107.0:5001"    // Alternative machine IP
              )
              .SetIsOriginAllowed(origin => 
              {
                  // Allow any local network IP (192.168.x.x, 10.x.x.x, 172.16-31.x.x)
                  var uri = new Uri(origin);
                  var host = uri.Host;
                  return host.StartsWith("192.168.") || 
                         host.StartsWith("10.") || 
                         host.StartsWith("172.16.") || 
                         host.StartsWith("172.17.") || 
                         host.StartsWith("172.18.") || 
                         host.StartsWith("172.19.") || 
                         host.StartsWith("172.20.") || 
                         host.StartsWith("172.21.") || 
                         host.StartsWith("172.22.") || 
                         host.StartsWith("172.23.") || 
                         host.StartsWith("172.24.") || 
                         host.StartsWith("172.25.") || 
                         host.StartsWith("172.26.") || 
                         host.StartsWith("172.27.") || 
                         host.StartsWith("172.28.") || 
                         host.StartsWith("172.29.") || 
                         host.StartsWith("172.30.") || 
                         host.StartsWith("172.31.") ||
                         host == "localhost" ||
                         host == "127.0.0.1" ||
                         host == "10.0.2.2";
              })
              .AllowAnyHeader()
              .AllowAnyMethod()
              .AllowCredentials();
    });
});

// Dependency Injection
builder.Services.AddScoped<IUserRepository, UserRepository>();
builder.Services.AddScoped<IContentRepository, ContentRepository>();
builder.Services.AddScoped<IStressIndicatorRepository, StressIndicatorRepository>();
builder.Services.AddScoped<IHealthMetricRepository, HealthMetricRepository>();
builder.Services.AddScoped<ICipherRepository, CipherRepository>();
builder.Services.AddScoped<IHealthAnalysisService, HealthAnalysisService>();
builder.Services.AddScoped<IJwtService, JwtService>();
builder.Services.AddScoped<IRecommendationService, RecommendationService>();

var app = builder.Build();

// Configure the HTTP request pipeline
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

// Only redirect to HTTPS in production
if (!app.Environment.IsDevelopment())
{
    app.UseHttpsRedirection();
}

app.UseCors("AllowAndroidApp");
app.UseAuthentication();
app.UseAuthorization();
app.MapControllers();

// Apply database migrations on startup
using (var scope = app.Services.CreateScope())
{
    var context = scope.ServiceProvider.GetRequiredService<ApplicationDbContext>();
    var logger = scope.ServiceProvider.GetRequiredService<ILogger<Program>>();
    
    try
    {
        // Try to apply migrations if they exist
        var pendingMigrations = context.Database.GetPendingMigrations().ToList();
        if (pendingMigrations.Any())
        {
            logger.LogInformation("Applying {Count} pending database migrations...", pendingMigrations.Count);
            context.Database.Migrate();
            logger.LogInformation("Database migrations applied successfully.");
        }
        else
        {
            // Check if database exists and has tables
            if (!context.Database.CanConnect())
            {
                logger.LogInformation("Database does not exist. Creating database...");
                context.Database.EnsureCreated();
                logger.LogInformation("Database created successfully.");
            }
            else
            {
                // Check if tables exist by trying to query the Users table
                try
                {
                    var userCount = context.Users.Count();
                    logger.LogInformation("Database is up to date. Found {Count} users.", userCount);
                }
                catch (Exception ex)
                {
                    // If query fails, tables don't exist - create them
                    logger.LogWarning(ex, "Tables not found. Creating tables...");
                    context.Database.EnsureCreated();
                    logger.LogInformation("Tables created successfully.");
                }
            }
        }
        
        // Seed knowledge pills if database is empty
        if (!context.Contents.Any())
        {
            logger.LogInformation("Seeding knowledge pills...");
            SeedKnowledgePills(context);
            logger.LogInformation("Knowledge pills seeded successfully.");
        }
    }
    catch (Exception ex)
    {
        logger.LogError(ex, "An error occurred while setting up the database.");
        // In development, we might want to continue anyway
        if (app.Environment.IsProduction())
        {
            throw;
        }
    }
}

static void SeedKnowledgePills(ApplicationDbContext context)
{
    var contents = new List<Content>
    {
        // Articles
        new Content
        {
            Title = "Understanding RESTful APIs: A Beginner's Guide",
            Type = ContentType.Article,
            Body = @"REST (Representational State Transfer) is an architectural style for designing networked applications. A RESTful API uses HTTP requests to GET, PUT, POST, and DELETE data.

Key Principles:
1. Stateless: Each request contains all information needed to process it
2. Client-Server: Separation of concerns between client and server
3. Uniform Interface: Consistent way of interacting with resources
4. Cacheable: Responses can be cached for better performance

Common HTTP Methods:
- GET: Retrieve data
- POST: Create new resources
- PUT: Update existing resources
- DELETE: Remove resources

Best Practices:
- Use meaningful URLs (/api/users instead of /api/getUsers)
- Return appropriate HTTP status codes
- Use JSON for data exchange
- Implement proper error handling",
            Tags = "API, REST, Web Development, Backend",
            CreatedAt = DateTime.UtcNow.AddDays(-5)
        },
        new Content
        {
            Title = "Docker Containers Explained: From Zero to Hero",
            Type = ContentType.Article,
            Body = @"Docker is a platform that uses containerization to package applications with all their dependencies.

What are Containers?
Containers are lightweight, portable units that package code and dependencies together. Unlike virtual machines, containers share the host OS kernel.

Key Concepts:
1. Image: A read-only template for creating containers
2. Container: A running instance of an image
3. Dockerfile: Instructions for building an image
4. Docker Compose: Tool for defining multi-container applications

Benefits:
- Consistency across environments
- Isolation of applications
- Resource efficiency
- Easy scaling

Common Commands:
- docker build: Build an image
- docker run: Run a container
- docker ps: List running containers
- docker-compose up: Start services",
            Tags = "Docker, DevOps, Containers, Infrastructure",
            CreatedAt = DateTime.UtcNow.AddDays(-4)
        },
        new Content
        {
            Title = "Async/Await in JavaScript: Mastering Asynchronous Programming",
            Type = ContentType.Article,
            Body = @"Asynchronous programming allows JavaScript to handle multiple operations without blocking the main thread.

Promises vs Async/Await:
Promises use .then() chains, while async/await provides a cleaner, more readable syntax.

Example:
async function fetchUserData() {
  try {
    const response = await fetch('/api/user');
    const data = await response.json();
    return data;
  } catch (error) {
    console.error('Error:', error);
  }
}

Key Points:
- async functions always return a Promise
- await pauses execution until the Promise resolves
- Use try/catch for error handling
- Parallel execution with Promise.all()

Best Practices:
- Don't use await in loops (use Promise.all instead)
- Handle errors properly
- Understand the event loop
- Use async/await for cleaner code",
            Tags = "JavaScript, Async, Programming, Frontend",
            CreatedAt = DateTime.UtcNow.AddDays(-3)
        },
        new Content
        {
            Title = "Git Workflow: Branching Strategies for Teams",
            Type = ContentType.Article,
            Body = @"Effective Git workflows are crucial for team collaboration and code management.

Common Branching Strategies:

1. Git Flow:
   - main: Production code
   - develop: Integration branch
   - feature/*: New features
   - release/*: Preparing releases
   - hotfix/*: Emergency fixes

2. GitHub Flow:
   - Simpler approach
   - main branch always deployable
   - Feature branches merged via pull requests

3. GitLab Flow:
   - Environment branches (staging, production)
   - Upstream first principle

Best Practices:
- Write clear commit messages
- Keep branches short-lived
- Review code before merging
- Use meaningful branch names
- Regular merges from main

Common Commands:
- git checkout -b feature/new-feature
- git merge feature/new-feature
- git rebase main
- git cherry-pick <commit>",
            Tags = "Git, Version Control, DevOps, Collaboration",
            CreatedAt = DateTime.UtcNow.AddDays(-2)
        },
        new Content
        {
            Title = "Microservices Architecture: When and How to Use It",
            Type = ContentType.Article,
            Body = @"Microservices architecture breaks applications into small, independent services that communicate over well-defined APIs.

Characteristics:
- Loosely coupled services
- Independently deployable
- Organized around business capabilities
- Decentralized data management

When to Use:
- Large, complex applications
- Need for independent scaling
- Different teams working on different parts
- Technology diversity requirements

Benefits:
- Scalability: Scale services independently
- Flexibility: Use different technologies
- Resilience: Failure isolation
- Team autonomy: Independent development

Challenges:
- Increased complexity
- Network latency
- Data consistency
- Service coordination

Best Practices:
- Start with monolith, extract when needed
- Use API Gateway for routing
- Implement service discovery
- Monitor and log everything
- Design for failure",
            Tags = "Architecture, Microservices, Backend, System Design",
            CreatedAt = DateTime.UtcNow.AddDays(-1)
        },

        // Videos
        new Content
        {
            Title = "Introduction to React Hooks",
            Type = ContentType.Video,
            Body = "Learn how to use React Hooks to manage state and side effects in functional components. This video covers useState, useEffect, useContext, and custom hooks.",
            VideoUrl = "https://www.youtube.com/watch?v=demo-react-hooks",
            Tags = "React, Hooks, Frontend, JavaScript",
            CreatedAt = DateTime.UtcNow.AddDays(-5)
        },
        new Content
        {
            Title = "Building REST APIs with ASP.NET Core",
            Type = ContentType.Video,
            Body = "Step-by-step tutorial on creating RESTful APIs using ASP.NET Core. Learn about controllers, routing, dependency injection, and best practices.",
            VideoUrl = "https://www.youtube.com/watch?v=demo-aspnet-api",
            Tags = "ASP.NET, C#, API, Backend",
            CreatedAt = DateTime.UtcNow.AddDays(-4)
        },
        new Content
        {
            Title = "Kotlin Coroutines Deep Dive",
            Type = ContentType.Video,
            Body = "Master Kotlin Coroutines for asynchronous programming. Understand suspend functions, coroutine scopes, channels, and flow.",
            VideoUrl = "https://www.youtube.com/watch?v=demo-kotlin-coroutines",
            Tags = "Kotlin, Coroutines, Android, Asynchronous",
            CreatedAt = DateTime.UtcNow.AddDays(-3)
        },
        new Content
        {
            Title = "Database Design Fundamentals",
            Type = ContentType.Video,
            Body = "Learn database design principles including normalization, relationships, indexing, and query optimization. Perfect for backend developers.",
            VideoUrl = "https://www.youtube.com/watch?v=demo-database-design",
            Tags = "Database, SQL, Design, Backend",
            CreatedAt = DateTime.UtcNow.AddDays(-2)
        },

        // Quizzes
        new Content
        {
            Title = "JavaScript Fundamentals Quiz",
            Type = ContentType.Quiz,
            Body = "Test your JavaScript knowledge with this comprehensive quiz covering variables, functions, closures, and ES6+ features.",
            QuizData = JsonSerializer.Serialize(new
            {
                questions = new[]
                {
                    new { question = "What is the output of: console.log(typeof null)", options = new[] { "null", "object", "undefined", "boolean" }, correct = 1, explanation = "In JavaScript, typeof null returns 'object' due to a historical bug." },
                    new { question = "What does 'const' keyword do?", options = new[] { "Creates a constant that cannot be reassigned", "Creates a variable that cannot be changed", "Creates a read-only property", "Creates a function constant" }, correct = 0, explanation = "const creates a block-scoped constant that cannot be reassigned, but the value itself can be mutable." },
                    new { question = "What is a closure in JavaScript?", options = new[] { "A function that returns another function", "A function that has access to variables in its outer scope", "A way to hide variables", "A type of loop" }, correct = 1, explanation = "A closure is a function that has access to variables in its outer (enclosing) lexical scope, even after the outer function has returned." },
                    new { question = "What is the difference between == and === in JavaScript?", options = new[] { "== compares values, === compares values and types", "== compares types, === compares values", "No difference", "== is faster" }, correct = 0, explanation = "== performs type coercion before comparison, while === compares both value and type without coercion." }
                }
            }),
            Tags = "JavaScript, Quiz, Programming, Fundamentals",
            CreatedAt = DateTime.UtcNow.AddDays(-5)
        },
        new Content
        {
            Title = "SQL Database Quiz",
            Type = ContentType.Quiz,
            Body = "Challenge yourself with SQL queries, joins, indexes, and database optimization questions.",
            QuizData = JsonSerializer.Serialize(new
            {
                questions = new[]
                {
                    new { question = "What is the purpose of an INDEX in a database?", options = new[] { "To store data", "To speed up query performance", "To enforce constraints", "To backup data" }, correct = 1, explanation = "Indexes are used to speed up query performance by creating a data structure that allows faster data retrieval." },
                    new { question = "What is the difference between INNER JOIN and LEFT JOIN?", options = new[] { "INNER JOIN returns all rows, LEFT JOIN returns matching rows", "INNER JOIN returns matching rows, LEFT JOIN returns all rows from left table", "No difference", "INNER JOIN is faster" }, correct = 1, explanation = "INNER JOIN returns only rows with matching values in both tables, while LEFT JOIN returns all rows from the left table and matching rows from the right table." },
                    new { question = "What does ACID stand for in database transactions?", options = new[] { "Atomicity, Consistency, Isolation, Durability", "Access, Control, Integrity, Data", "Analysis, Code, Implementation, Design", "Application, Connection, Interface, Database" }, correct = 0, explanation = "ACID properties ensure reliable database transactions: Atomicity (all or nothing), Consistency (valid state), Isolation (concurrent transactions), Durability (permanent changes)." }
                }
            }),
            Tags = "SQL, Database, Quiz, Backend",
            CreatedAt = DateTime.UtcNow.AddDays(-4)
        },
        new Content
        {
            Title = "Docker & Containerization Quiz",
            Type = ContentType.Quiz,
            Body = "Test your knowledge of Docker, containers, images, and container orchestration.",
            QuizData = JsonSerializer.Serialize(new
            {
                questions = new[]
                {
                    new { question = "What is the difference between a Docker image and a container?", options = new[] { "Image is running, container is stopped", "Image is a template, container is a running instance", "No difference", "Image is larger" }, correct = 1, explanation = "A Docker image is a read-only template used to create containers. A container is a running instance of an image." },
                    new { question = "What does 'docker-compose' do?", options = new[] { "Builds a single container", "Orchestrates multi-container applications", "Deletes containers", "Updates Docker" }, correct = 1, explanation = "docker-compose is a tool for defining and running multi-container Docker applications using a YAML file." },
                    new { question = "What is the purpose of a Dockerfile?", options = new[] { "To run containers", "To define how to build a Docker image", "To manage volumes", "To configure networks" }, correct = 1, explanation = "A Dockerfile contains instructions for building a Docker image, including base image, dependencies, and commands to run." }
                }
            }),
            Tags = "Docker, Containers, DevOps, Quiz",
            CreatedAt = DateTime.UtcNow.AddDays(-3)
        },
        new Content
        {
            Title = "API Design Best Practices Quiz",
            Type = ContentType.Quiz,
            Body = "Test your understanding of RESTful API design, HTTP methods, status codes, and API security.",
            QuizData = JsonSerializer.Serialize(new
            {
                questions = new[]
                {
                    new { question = "Which HTTP status code should be returned when a resource is successfully created?", options = new[] { "200 OK", "201 Created", "204 No Content", "202 Accepted" }, correct = 1, explanation = "HTTP 201 Created is the appropriate status code when a new resource is successfully created." },
                    new { question = "What is the purpose of API versioning?", options = new[] { "To improve performance", "To allow changes without breaking existing clients", "To add security", "To reduce latency" }, correct = 1, explanation = "API versioning allows you to make changes to your API while maintaining backward compatibility for existing clients." },
                    new { question = "Which HTTP method should be used to update an existing resource?", options = new[] { "GET", "POST", "PUT", "DELETE" }, correct = 2, explanation = "PUT is used to update an existing resource. POST is typically used to create new resources." }
                }
            }),
            Tags = "API, REST, Web Development, Quiz",
            CreatedAt = DateTime.UtcNow.AddDays(-2)
        }
    };

    context.Contents.AddRange(contents);
    context.SaveChanges();
}

app.Run();

// Make Program class accessible for testing
public partial class Program { }

