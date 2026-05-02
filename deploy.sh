#!/bin/bash

set -e

echo "📦 Building WAR..."
mvn clean package -DskipTests

echo "🐳 Building & starting container via docker-compose..."
docker-compose down || true  # in case it's already running
docker-compose up -d --build

echo "✅ App is live at http://<your-public-ip>:8080/"
