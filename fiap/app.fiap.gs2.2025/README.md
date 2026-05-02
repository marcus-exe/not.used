# Tech Knowledge Pills - Health + Education App

A full-stack application combining health monitoring (stress indicators) with educational content (tech knowledge pills). Built with Kotlin/Jetpack Compose for Android and C# ASP.NET Core for the backend.

## Project Structure

```
app.fiap.gs2.2025/
├── backend/                    # C# ASP.NET Core Web API
│   ├── TechKnowledgePills.API/
│   ├── TechKnowledgePills.Core/
│   ├── TechKnowledgePills.Infrastructure/
│   ├── Dockerfile             # Docker image definition
│   ├── docker-compose.yml      # Docker Compose configuration
│   ├── Makefile               # Convenience commands
│   └── TechKnowledgePills.sln
├── android/                    # Kotlin + Jetpack Compose
│   └── app/
└── README.md
```

## Features

### Backend (C# ASP.NET Core)
- **Authentication**: JWT-based authentication with refresh tokens
- **Content Management**: CRUD operations for knowledge pills (articles, videos, quizzes)
- **Stress Indicators**: Mock stress data generation and tracking
- **Recommendation Engine**: Personalized content suggestions based on stress levels
- **PostgreSQL Database**: Entity Framework Core with PostgreSQL

### Android App (Kotlin + Jetpack Compose)
- **Authentication**: Beautiful login and registration screens with gradient backgrounds
- **Home Dashboard**: Enhanced overview with stress indicators and recommendations
- **Stress Monitoring**: Visual display of stress levels with mock data generation
- **Content Browsing**: Browse and view all knowledge pills with enhanced card design
- **Content Viewing**: 
  - Articles with rich text display
  - **Inline video player** supporting YouTube URLs and direct video URLs
  - **Interactive quiz component** with questions, options, and result display
- **Recommendations**: Personalized content based on current stress level
- **Material Design 3**: Modern, accessible UI with gradient headers, icons, and enhanced styling

## Technology Stack

### Backend
- .NET 8 / ASP.NET Core
- Entity Framework Core
- PostgreSQL (containerized with Docker)
- JWT Authentication
- Swagger/OpenAPI
- Docker & Docker Compose

### Android
- Kotlin
- Jetpack Compose
- Hilt (Dependency Injection)
- Retrofit (API calls)
- DataStore (Token management)
- ExoPlayer (Video playback)
- WebView (YouTube integration)
- Material Design 3
- Coroutines
- KSP (Kotlin Symbol Processing)

## Setup Instructions

### Backend Setup (Docker Compose - Recommended)

1. **Prerequisites**
   - Docker Desktop (or Docker Engine + Docker Compose)
   - .NET 8 SDK (optional, only needed for local development without Docker)

2. **Quick Start with Docker Compose**
   ```bash
   cd backend
   docker-compose up -d
   ```
   
   This will:
   - Build the API Docker image
   - Start PostgreSQL database container
   - Start the API service
   - Automatically create and migrate the database

3. **Verify Services**
   ```bash
   docker-compose ps
   ```
   Both `techknowledgepills-api` and `techknowledgepills-db` should be running.

4. **Access the API**
   - **API**: http://localhost:5001
   - **Swagger UI**: http://localhost:5001/swagger
   - **Database**: localhost:5432
   
   **Note**: Port 5000 is used by macOS AirPlay Receiver, so the API uses port 5001 instead.

5. **View Logs**
   ```bash
   # All services
   docker-compose logs -f
   
   # API only
   docker-compose logs -f api
   
   # Database only
   docker-compose logs -f db
   ```

6. **Stop Services**
   ```bash
   docker-compose down
   ```

7. **Reset Database** (removes all data)
   ```bash
   docker-compose down -v
   docker-compose up -d
   ```

**For more details, see [backend/README.md](backend/README.md) or [backend/QUICKSTART.md](backend/QUICKSTART.md)**

### Backend Setup (Local Development - Alternative)

If you prefer to run without Docker:

1. **Prerequisites**
   - .NET 8 SDK
   - PostgreSQL database running locally

2. **Database Configuration**
   - Update the connection string in `backend/TechKnowledgePills.API/appsettings.Development.json`:
   ```json
   "ConnectionStrings": {
     "DefaultConnection": "Host=localhost;Port=5432;Database=techknowledgepills;Username=postgres;Password=postgres"
   }
   ```

3. **Run the Backend**
   ```bash
   cd backend/TechKnowledgePills.API
   dotnet restore
   dotnet run
   ```
   The API will be available at `http://localhost:5001`

4. **Swagger UI**
   - Navigate to `http://localhost:5001/swagger` to view and test the API endpoints

### Android Setup

1. **Prerequisites**
   - Android Studio Hedgehog or later
   - JDK 17
   - Android SDK (API 24+)

2. **Configure API Base URL**
   - For Android Emulator: The base URL is set to `http://10.0.2.2:5001/` (localhost proxy)
   - For physical device: 
     - If backend is running in Docker: Use your machine's IP address (e.g., `http://192.168.1.100:5001`)
     - Update `android/app/src/main/java/com/techknowledgepills/data/api/RetrofitModule.kt` with the correct IP and port

3. **Build and Run**
   - Open the `android` folder in Android Studio
   - Sync Gradle files
   - Run the app on an emulator or physical device

## API Endpoints

### Authentication
- `POST /api/auth/register` - Register a new user
- `POST /api/auth/login` - Login
- `GET /api/auth/me` - Get current user (requires authentication)

### Content
- `GET /api/content` - Get all content
- `GET /api/content/{id}` - Get content by ID
- `GET /api/content/type/{type}` - Get content by type (1=Article, 2=Video, 3=Quiz)
- `POST /api/content` - Create content (admin)
- `PUT /api/content/{id}` - Update content (admin)
- `DELETE /api/content/{id}` - Delete content (admin)

### Stress Indicators
- `GET /api/stressindicator` - Get all stress indicators for current user
- `GET /api/stressindicator/latest` - Get latest stress indicator
- `POST /api/stressindicator/generate-mock` - Generate mock stress data
- `POST /api/stressindicator` - Create a new stress indicator

### Recommendations
- `GET /api/recommendation` - Get personalized content recommendations

## Usage

1. **First Time Setup**
   - Register a new account in the Android app
   - Login with your credentials

2. **Generate Mock Stress Data**
   - Navigate to the Stress Indicator screen
   - Tap "Generate Mock Data" to create 30 days of simulated stress data

3. **View Recommendations**
   - The home screen shows recommendations based on your current stress level
   - Navigate to the Recommendations screen for more suggestions

4. **Browse Content**
   - Use "Browse All Content" to see all available knowledge pills
   - Tap any content item to view details
   - **Articles**: Read full content with rich formatting
   - **Videos**: Watch inline with YouTube or direct video player support
   - **Quizzes**: Take interactive quizzes with multiple-choice questions and see your results

## Development Notes

- The app uses mock stress data for demonstration purposes
- Content types include Articles, Videos, and Quizzes
- **AI-generated knowledge pills** are automatically seeded into the database on first startup
- The recommendation algorithm suggests content based on stress levels:
  - High stress: Prefers articles and videos (calming content)
  - Low stress: More challenging content
- JWT tokens are stored securely using DataStore
- The backend uses clean architecture with separation of concerns
- Backend is containerized with Docker Compose for easy setup and deployment
- Database migrations are automatically applied on startup
- Environment variables can be configured via `.env` file or `docker-compose.yml`
- **Interactive quiz component** parses JSON quiz data and renders questions with selectable options
- **Inline video player** supports both YouTube URLs (via WebView) and direct video URLs (via ExoPlayer)
- Enhanced UI with gradient headers, Material Design 3 components, and improved visual hierarchy

## Docker Commands Reference

### Using Make (Recommended)
```bash
cd backend
make build          # Build Docker images
make up             # Start all services
make up-logs        # Start with logs visible
make down           # Stop all services
make logs-api       # View API logs
make logs-db        # View database logs
make migrate        # Run database migrations
make clean          # Remove everything including volumes
```

### Using Docker Compose Directly
```bash
cd backend
docker-compose up -d              # Start services in background
docker-compose up                 # Start services with logs
docker-compose down               # Stop services
docker-compose down -v            # Stop and remove volumes
docker-compose logs -f api        # Follow API logs
docker-compose ps                 # Check service status
docker-compose exec api bash      # Access API container shell
docker-compose exec db psql -U postgres -d techknowledgepills  # Access database
```

## Troubleshooting

### Backend Issues

**Port already in use:**
- Edit `backend/docker-compose.yml` and change port mappings
- Or stop the conflicting service

**Database connection errors:**
- Wait a few seconds after starting - database needs time to initialize
- Check database logs: `docker-compose logs db`
- Verify connection string in environment variables

**API not starting:**
- Check API logs: `docker-compose logs api`
- Verify database is healthy: `docker-compose ps`

### Android App Issues

**Cannot connect to API:**
- Ensure backend is running: `docker-compose ps`
- For physical device: Update API base URL with your machine's IP address and port 5001
- Check CORS settings in `backend/TechKnowledgePills.API/Program.cs`
- Verify API is accessible: Open http://localhost:5001/swagger in browser
- **Note**: Port 5000 is reserved by macOS AirPlay, so the API uses port 5001

## Future Enhancements

- Real sensor integration for stress monitoring
- Offline content caching with Room
- Push notifications for new content
- User progress tracking
- Content rating and feedback
- Social features (sharing, comments)
- Advanced analytics and insights
- Kubernetes deployment configuration
- CI/CD pipeline setup

## License

This project is created for educational purposes.

