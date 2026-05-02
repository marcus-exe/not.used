#!/bin/bash
# Quick script to find your machine's IP address for Android device testing

echo "=========================================="
echo "Finding your machine's IP address..."
echo "=========================================="
echo ""

# Try different methods based on OS
if [[ "$OSTYPE" == "darwin"* ]]; then
    # macOS
    echo "macOS detected"
    echo ""
    IP=$(ifconfig | grep "inet " | grep -v 127.0.0.1 | awk '{print $2}' | head -1)
    if [ -z "$IP" ]; then
        IP=$(ipconfig getifaddr en0)
    fi
elif [[ "$OSTYPE" == "linux-gnu"* ]]; then
    # Linux
    echo "Linux detected"
    echo ""
    IP=$(hostname -I | awk '{print $1}')
elif [[ "$OSTYPE" == "msys" || "$OSTYPE" == "cygwin" ]]; then
    # Windows
    echo "Windows detected"
    echo ""
    IP=$(ipconfig | grep "IPv4" | awk '{print $14}' | head -1)
fi

if [ -z "$IP" ]; then
    echo "❌ Could not automatically detect IP address"
    echo ""
    echo "Please find your IP manually:"
    echo "  macOS:   ifconfig | grep 'inet '"
    echo "  Linux:   hostname -I"
    echo "  Windows: ipconfig"
    exit 1
fi

echo "✅ Your IP address: $IP"
echo ""
echo "=========================================="
echo "Update RetrofitModule.kt with:"
echo "=========================================="
echo ""
echo "private const val BASE_URL = \"http://$IP:5001/\""
echo ""
echo "=========================================="
echo "Test API from device browser:"
echo "=========================================="
echo ""
echo "http://$IP:5001/swagger"
echo ""

