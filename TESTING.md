# Testing CI/CD Pipeline Locally

You can test the CI/CD pipeline locally in two ways:

## Option 1: Manual Testing Script (Recommended)

Use the provided script that simulates what GitHub Actions does:

```bash
./test_build_local.sh
```

This script will:
- ✅ Install dependencies
- ✅ Build Android APK and AAB (if keystore is configured)
- ✅ Build iOS IPA (on macOS, if Team ID is configured)

### Prerequisites for Full Testing

**Android:**
- Copy `android/key.properties.template` to `android/key.properties`
- Fill in your keystore details
- Place your `upload-keystore.jks` in `android/app/`

**iOS (macOS only):**
- Set `IOS_TEAM_ID` environment variable: `export IOS_TEAM_ID=YOUR_TEAM_ID`
- Configure signing in Xcode or use automatic signing

### Quick Test Without Signing

If you just want to verify the build works (without release signing):

```bash
# Android (debug signing)
flutter build apk --release
flutter build appbundle --release

# iOS (no codesign)
flutter build ios --release --no-codesign
```

## Option 2: Using `act` (GitHub Actions Locally)

[`act`](https://github.com/nektos/act) is a tool that runs GitHub Actions workflows locally using Docker.

### Installation

```bash
# macOS
brew install act

# Linux (using install script)
curl https://raw.githubusercontent.com/nektos/act/master/install.sh | sudo bash

# Or download from: https://github.com/nektos/act/releases
```

### Usage

```bash
# List all workflows
act -l

# Run a specific job
act -j build-android
act -j build-ios

# Dry run (see what would run)
act -n

# Run with specific event
act push
```

### Limitations

- **macOS Runner**: `act` can't fully simulate macOS runners, so iOS builds may not work perfectly
- **Secrets**: You'll need to create a `.secrets` file or use `-s` flag for secrets
- **Docker Required**: Needs Docker running

### Example with Secrets

Create a `.secrets` file in the project root:

```bash
# .secrets (DO NOT COMMIT THIS FILE!)
ANDROID_KEYSTORE_BASE64=<your-base64-encoded-keystore>
ANDROID_KEYSTORE_PASSWORD=<your-password>
ANDROID_KEY_ALIAS=upload
ANDROID_KEY_PASSWORD=<your-key-password>
IOS_TEAM_ID=<your-team-id>
# ... other secrets
```

Then run:
```bash
act push --secret-file .secrets
```

## Option 3: Manual Command Testing

Run the exact commands from the workflow file:

### Android

```bash
flutter pub get
flutter build apk --release
flutter build appbundle --release
```

### iOS

```bash
flutter pub get
flutter precache --ios
cd ios && pod install && cd ..
flutter build ipa --release --export-options-plist ios/ExportOptions.plist
```

## Checking Build Artifacts

After running builds, artifacts will be in:

- **Android APK**: `build/app/outputs/flutter-apk/app-release.apk`
- **Android AAB**: `build/app/outputs/bundle/release/app-release.aab`
- **iOS IPA**: `build/ios/ipa/*.ipa`

## Troubleshooting

### Android Build Issues

- **Keystore not found**: Make sure `android/key.properties` exists or the build will use debug signing
- **local.properties missing**: Run `flutter pub get` first - Flutter generates this automatically

### iOS Build Issues

- **Pod install fails**: Run `flutter precache --ios` first
- **Signing errors**: Configure Team ID in Xcode or set `IOS_TEAM_ID` environment variable
- **ExportOptions.plist**: Make sure the Team ID placeholder is replaced if needed

## Next Steps

Once local testing passes:
1. Push to GitHub
2. Watch the Actions tab for the workflow run
3. Download artifacts from the completed workflow run

