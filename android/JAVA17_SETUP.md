# Java 17 Setup for KAPT

## Problem
KAPT (Kotlin Annotation Processing Tool) doesn't work with Java 22. You need Java 17.

## Quick Solution

### Option 1: Use Android Studio's Bundled JDK (Easiest)

1. **Find Android Studio's JDK:**
   ```bash
   # Check common locations
   /Applications/Android\ Studio.app/Contents/jbr/Contents/Home
   ~/Library/Application\ Support/Google/AndroidStudio*/jbr/Contents/Home
   ```

2. **Set in Android Studio:**
   - File > Project Structure > SDK Location
   - Set **JDK location** to Android Studio's bundled JDK (usually Java 17)
   - Or set in `local.properties`:
     ```properties
     org.gradle.java.home=/Applications/Android Studio.app/Contents/jbr/Contents/Home
     ```

### Option 2: Install Java 17 via Homebrew

```bash
brew install --cask temurin@17
```

Then update `local.properties`:
```properties
org.gradle.java.home=$(/usr/libexec/java_home -v 17)
```

### Option 3: Download Java 17 Manually

1. Download from: https://adoptium.net/temurin/releases/?version=17
2. Install the .pkg file
3. Update `local.properties`:
   ```properties
   org.gradle.java.home=/Library/Java/JavaVirtualMachines/temurin-17.jdk/Contents/Home
   ```

## Verify Setup

After setting Java 17, verify:
```bash
cd android
./gradlew --stop
./gradlew :app:assembleDebug
```

## Alternative: Use KSP Instead of KAPT

KSP (Kotlin Symbol Processing) works with Java 22 and is the modern replacement:

1. Update `build.gradle.kts`:
   ```kotlin
   plugins {
       id("com.google.devtools.ksp") version "1.9.20-1.0.14"
   }
   
   dependencies {
       ksp("com.google.dagger:hilt-android-compiler:2.48")
       ksp("androidx.room:room-compiler:2.6.1")
   }
   ```

2. Remove `kotlin-kapt` plugin
3. Replace all `kapt` with `ksp` in dependencies

