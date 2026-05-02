#!/bin/bash
# Script to seed the database with knowledge pills
# This script runs the C# seed script inside the Docker container

echo "Seeding database with knowledge pills..."

# Copy the seed script to the container
docker cp scripts/seed-content.cs techknowledgepills-api:/tmp/seed-content.cs

# Run the seed script
docker exec techknowledgepills-api dotnet script /tmp/seed-content.cs

echo "Done!"

