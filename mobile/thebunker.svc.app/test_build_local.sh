#!/bin/bash
# Script to test the CI/CD workflow locally
# This simulates what GitHub Actions does

set -e  # Exit on error

echo "🧪 Testing CI/CD Build Locally"
echo "================================"

# Colors for output
GREEN='\033[0;32m'
BLUE='\033[0;34m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Check Flutter installation
if ! command -v flutter &> /dev/null; then
    echo "❌ Flutter is not installed or not in PATH"
    exit 1
fi

echo -e "${BLUE}📦 Flutter version:${NC}"
flutter --version

echo -e "\n${BLUE}📥 Installing dependencies...${NC}"
flutter pub get

# Android Build Test
echo -e "\n${YELLOW}🤖 Testing Android Build${NC}"
echo "-------------------"

# For local testing, use debug signing to avoid keystore password issues
# CI/CD will use proper signing from GitHub Secrets
echo -e "${YELLOW}⚠ Building with debug signing for local test${NC}"
echo "  (CI/CD uses proper signing from secrets)"
echo "Building APK..."
flutter build apk --release

echo "Building AAB..."
flutter build appbundle --release

echo -e "${GREEN}✓ Android builds completed!${NC}"
echo "  APK: build/app/outputs/flutter-apk/app-release.apk"
echo "  AAB: build/app/outputs/bundle/release/app-release.aab"

# iOS Build Test
echo -e "\n${YELLOW}🍎 Testing iOS Build${NC}"
echo "----------------"

# Check if on macOS
if [[ "$OSTYPE" == "darwin"* ]]; then
    echo "Installing CocoaPods dependencies..."
    cd ios
    pod install --repo-update
    cd ..
    
    if [ -n "$IOS_TEAM_ID" ]; then
        echo -e "${GREEN}✓ iOS Team ID configured - building IPA${NC}"
        flutter build ipa --release --export-options-plist ios/ExportOptions.plist
        echo -e "${GREEN}✓ iOS build completed!${NC}"
        echo "  IPA: build/ios/ipa/*.ipa"
    else
        echo -e "${YELLOW}⚠ iOS Team ID not set - building without signing${NC}"
        echo "  (Set IOS_TEAM_ID environment variable for IPA build)"
        flutter build ios --release --no-codesign
    fi
else
    echo -e "${YELLOW}⚠ Skipping iOS build (requires macOS)${NC}"
fi

echo -e "\n${GREEN}✅ Local build test completed!${NC}"

