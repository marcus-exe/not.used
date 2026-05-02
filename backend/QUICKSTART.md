# Quick Start Guide

Get the backend running with Docker Compose in 3 steps:

## Step 1: Start Services

```bash
cd backend
docker-compose up -d
```

This will:
- Build the API Docker image
- Start PostgreSQL database
- Start the API service
- Automatically create the database schema

## Step 2: Verify Services

Check that both services are running:

```bash
docker-compose ps
```

You should see both `techknowledgepills-api` and `techknowledgepills-db` with status "Up".

## Step 3: Test the API

Open your browser and navigate to:
- **Swagger UI**: http://localhost:5001/swagger
- **API Health**: http://localhost:5001/api/auth/me (will return 401, which is expected)

**Note**: Port 5000 is reserved by macOS AirPlay, so the API uses port 5001.

## View Logs

```bash
# All services
docker-compose logs -f

# API only
docker-compose logs -f api

# Database only
docker-compose logs -f db
```

## Stop Services

```bash
docker-compose down
```

## Reset Everything

To completely reset (removes database data):

```bash
docker-compose down -v
docker-compose up -d
```

## Common Issues

### Port Already in Use

If port 5001 or 5432 is already in use, edit `docker-compose.yml` and change the port mappings:

```yaml
ports:
  - "5002:8080"  # Change 5001 to 5002
```

**Note**: Port 5000 is reserved by macOS AirPlay Receiver, so the default API port is 5001.

### Database Connection Errors

Wait a few seconds after starting services - the database needs time to initialize. Check logs:

```bash
docker-compose logs db
```

### API Not Starting

Check API logs for errors:

```bash
docker-compose logs api
```

## Next Steps

- Read the full [README.md](README.md) for detailed documentation
- Create your first user via Swagger UI or the Android app
- Generate mock stress data using the API

