# Use the official .NET SDK image for building
FROM mcr.microsoft.com/dotnet/sdk:8.0 AS build
WORKDIR /src

# Copy solution and project files
COPY TechKnowledgePills.sln ./
COPY TechKnowledgePills.Core/*.csproj ./TechKnowledgePills.Core/
COPY TechKnowledgePills.Infrastructure/*.csproj ./TechKnowledgePills.Infrastructure/
COPY TechKnowledgePills.API/*.csproj ./TechKnowledgePills.API/

# Restore dependencies
RUN dotnet restore

# Copy all source files
COPY . .

# Build the application
WORKDIR /src/TechKnowledgePills.API
RUN dotnet build -c Release -o /app/build

# Publish the application
FROM build AS publish
RUN dotnet publish -c Release -o /app/publish

# Build runtime image
FROM mcr.microsoft.com/dotnet/aspnet:8.0 AS final
WORKDIR /app

# Copy published files
COPY --from=publish /app/publish .

# Expose port
EXPOSE 8080

# Set environment variables
ENV ASPNETCORE_URLS=http://+:8080
ENV ASPNETCORE_ENVIRONMENT=Development

# Run the application
ENTRYPOINT ["dotnet", "TechKnowledgePills.API.dll"]

