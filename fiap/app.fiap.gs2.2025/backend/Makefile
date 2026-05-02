.PHONY: build up down restart logs clean migrate

# Build the Docker images
build:
	docker-compose build

# Start all services
up:
	docker-compose up -d

# Start all services with logs
up-logs:
	docker-compose up

# Stop all services
down:
	docker-compose down

# Stop and remove volumes
down-clean:
	docker-compose down -v

# Restart all services
restart:
	docker-compose restart

# View logs
logs:
	docker-compose logs -f

# View API logs only
logs-api:
	docker-compose logs -f api

# View DB logs only
logs-db:
	docker-compose logs -f db

# Run database migrations
migrate:
	docker-compose exec api dotnet ef database update --project TechKnowledgePills.Infrastructure --startup-project TechKnowledgePills.API

# Create a new migration
migration:
	docker-compose exec api dotnet ef migrations add $(name) --project TechKnowledgePills.Infrastructure --startup-project TechKnowledgePills.API

# Clean everything (containers, volumes, images)
clean:
	docker-compose down -v --rmi all

# Execute a command in the API container
exec-api:
	docker-compose exec api $(cmd)

# Execute a command in the DB container
exec-db:
	docker-compose exec db $(cmd)

# Open a PostgreSQL shell
psql:
	docker-compose exec db psql -U postgres -d techknowledgepills

