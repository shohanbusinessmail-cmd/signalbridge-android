# Testing — SignalBridge

## Strategy

- Unit tests: pure domain logic (number normalization, validation, profile
  creation, reconciliation, desired-vs-observed comparison, recovery policy,
  error classification, retry limits, duplicate prevention, persistence,
  migration, localization integrity, notification mapping).
- Integration tests: repositories, domain-use-case integration, gateway
  abstraction, supported/unsupported flows, permission denial, network failure,
  carrier rejection, user cancellation, unknown state, process recreation,
  subscription removal, partial success. Mock the telephony boundary.
- UI tests: first launch, language, onboarding, SIM selection, destination
  entry, validation, profile CRUD, wizard, confirmations, error/retry,
  dashboard states, diagnostics, history, settings, Bengali/English, large
  text, empty/loading states.

## Commands

```bash
./gradlew testDebugUnitTest          # unit tests (JVM)
./gradlew lintDebug                  # Android lint
./gradlew assembleDebug              # debug APK
./gradlew assembleRelease            # release APK (needs signing config)
```

Instrumented tests run on emulator/device via connectedCheck. Mock-based
results are labeled as simulated, not carrier tests.

## Coverage note

A green unit-test suite does NOT establish network-level compatibility.
Carrier behavior must be verified on real devices with real SIMs (see
docs/DEVICE_TEST_PLAN.md).

## Results

Not executed in this environment (no JDK/Android SDK available to run
Gradle). CI workflow (`.github/workflows/ci.yml`) runs these on GitHub-hosted
runners when pushed.
