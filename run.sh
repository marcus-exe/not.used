#!/bin/bash

# Alias docker to podman if Podman is being used
# alias docker=podman

echo "🛑 Stopping and removing existing containers..."
docker stop $(docker ps -q) 2>/dev/null
docker rm -f $(docker ps -aq) 2>/dev/null

echo "🛠 Building Maven project..."
if ! mvn clean package; then
    echo "❌ Maven build failed!"
    exit 1
fi

# Check if the WAR file was created
if [ ! -f target/*.war ]; then
    echo "❌ WAR file not found! Exiting..."
    exit 1
fi

echo "🐳 Building Docker image..."
docker build -t localhost/my-jsp-app:latest .

echo "🚀 Running Docker container..."
docker run -d -p 8080:8080 localhost/my-jsp-app:latest

echo "✅ Application is running at: http://localhost:8080"
