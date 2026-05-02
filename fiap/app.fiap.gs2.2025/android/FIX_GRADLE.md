# Fixing Gradle Configuration Error

If you're seeing the error:
```
Failed to notify project evaluation listener.
'org.gradle.api.file.FileCollection org.gradle.api.artifacts.Configuration.fileCollection(org.gradle.api.specs.Spec)'
```

## Quick Fix

1. **In Android Studio:**
   - Go to **File > Invalidate Caches / Restart**
   - Select **Invalidate and Restart**
   - Wait for Android Studio to restart and re-index

2. **Sync Gradle:**
   - Click **File > Sync Project with Gradle Files**
   - Wait for sync to complete

3. **If still failing, clean and rebuild:**
   ```bash
   cd android
   ./gradlew clean
   ./gradlew build
   ```

## What Was Fixed

1. ✅ Created `gradle-wrapper.properties` with Gradle 8.4
2. ✅ Updated Kotlin version to 1.9.22 (compatible with AGP 8.2.0)
3. ✅ Updated Compose compiler to 1.5.4
4. ✅ Fixed Hilt compiler dependency
5. ✅ Created Gradle wrapper script (`gradlew`)

## Manual Steps (if needed)

If Android Studio still can't download the Gradle wrapper:

1. **Download Gradle wrapper jar manually:**
   ```bash
   cd android
   curl -L -o gradle/wrapper/gradle-wrapper.jar \
     https://github.com/gradle/gradle/raw/v8.4.0/gradle/wrapper/gradle-wrapper.jar
   ```

2. **Or let Gradle download it:**
   ```bash
   cd android
   ./gradlew wrapper --gradle-version 8.4
   ```

## Verify Fix

After syncing, you should see:
- ✅ Gradle sync successful
- ✅ No configuration errors
- ✅ Project builds successfully

## Still Having Issues?

1. Check Android Studio version (should be Hedgehog or later)
2. Check JDK version (should be JDK 17)
3. Try deleting `.gradle` folder in project root
4. Try deleting `build` folders
5. Restart Android Studio

