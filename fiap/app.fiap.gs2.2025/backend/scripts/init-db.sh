#!/bin/bash
# Database initialization script
# This script waits for the database to be ready and then runs migrations

set -e

echo "Waiting for database to be ready..."
until docker-compose exec -T db pg_isready -U postgres > /dev/null 2>&1; do
  echo "Database is unavailable - sleeping"
  sleep 1
done

echo "Database is ready!"
echo "Running migrations..."

docker-compose exec api dotnet ef database update --project TechKnowledgePills.Infrastructure --startup-project TechKnowledgePills.API

echo "Migrations completed successfully!"

