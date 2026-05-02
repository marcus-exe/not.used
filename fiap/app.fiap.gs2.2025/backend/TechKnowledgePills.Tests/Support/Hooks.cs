using TechKnowledgePills.Tests.Support;
using TechTalk.SpecFlow;

namespace TechKnowledgePills.Tests.Support;

[Binding]
public class Hooks
{
    private TestContext? _context;
    private readonly TestWebApplicationFactory _factory;

    public Hooks()
    {
        _factory = TestDependencies.GetFactory();
    }

    [BeforeScenario]
    public void BeforeScenario()
    {
        _context = TestDependencies.CreateContext();

        // Reset context for each scenario
        _context.AuthResponse = null;
        _context.AuthToken = null;
        _context.RegisterRequest = null;
        _context.LoginRequest = null;
        _context.ContentDto = null;
        _context.StressIndicatorDto = null;
        _context.CreatedUserId = null;
        _context.CreatedContentId = null;
        _context.CreatedStressIndicatorId = null;

        // Create a new client for each scenario
        _context.SetClient(_factory.CreateClient());
    }

    [AfterScenario]
    public void AfterScenario()
    {
        if (_context != null)
        {
            _context.Client?.Dispose();
            TestDependencies.ClearContext();
        }
    }
}

