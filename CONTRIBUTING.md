# Contributing — SignalBridge

## Setup

1. Install JDK 17 and the Android SDK (compileSdk 35).
2. Clone the repo and ensure the Gradle wrapper is present.
3. `./gradlew assembleDebug` to verify the build.

## Code quality

- Kotlin, Jetpack Compose, Material 3.
- Follow the spec's interaction rules (no main-thread telephony, duplicate
  guards, confirmation for destructive actions).
- All user-facing text in string resources; Bengali + English.
- No fake network status in production code.
- No secrets committed.

## Tests

- Unit tests in `src/test/` (pure domain logic).
- Instrumented/Compose UI tests in `src/androidTest/`.
- Run `./gradlew testDebugUnitTest` and `./gradlew lintDebug`.

## Workflow

- Branch from `main`, open a PR, ensure CI passes, then merge.
- Tag releases only after release gates pass.
