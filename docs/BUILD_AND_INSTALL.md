# Build & Install — SignalBridge

## Prerequisites

- JDK 17
- Android SDK (compileSdk 35, build-tools, platform-tools)
- Gradle (wrapper)

## Build commands

```bash
# Debug APK
./gradlew assembleDebug

# Release APK (requires release signing config; see docs/RELEASE_PROCESS.md)
./gradlew assembleRelease

# Lint
./gradlew lintDebug

# Unit tests
./gradlew testDebugUnitTest
```

## APK output

- Debug: `app/build/outputs/apk/debug/app-debug.apk`
- Release: `app/build/outputs/apk/release/app-release.apk`

## Install

```bash
adb install app/build/outputs/apk/debug/app-debug.apk
```

On a device without Play Store, enable "Install unknown apps" for the installer
used (browser or file manager). Sideloading is not equivalent to Play Store
distribution.

## Signing

Debug builds use the Android debug keystore. Release builds require a dedicated
release keystore — never commit signing credentials. If no release keystore is
available, do not ship a release APK; document the step (see
docs/RELEASE_PROCESS.md).

## Blocked in this environment

This sandbox has no JDK or Android SDK, and the network allowlist prevents
downloading them. Compilation, APK building, and device testing are blocked
here; CI on GitHub-hosted runners is configured to perform them.
