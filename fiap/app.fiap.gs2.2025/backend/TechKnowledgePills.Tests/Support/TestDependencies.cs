using System.Threading;
using TechKnowledgePills.Tests.Support;
using TechTalk.SpecFlow;

namespace TechKnowledgePills.Tests.Support;

[Binding]
public class TestDependencies
{
    private static TestWebApplicationFactory? _factory;
    private static readonly AsyncLocal<TestContext?> _context = new();

    [BeforeTestRun]
    public static void BeforeTestRun()
    {
        _factory = new TestWebApplicationFactory();
        _context.Value = new TestContext();
    }

    [AfterTestRun]
    public static void AfterTestRun()
    {
        _factory?.Dispose();
        _context.Value = null;
    }

    public static TestWebApplicationFactory GetFactory()
    {
        return _factory ??= new TestWebApplicationFactory();
    }

    public static TestContext GetContext()
    {
        return _context.Value ??= new TestContext();
    }

    public static TestContext CreateContext()
    {
        var context = new TestContext();
        _context.Value = context;
        return context;
    }

    public static void ClearContext()
    {
        _context.Value = null;
    }
}

