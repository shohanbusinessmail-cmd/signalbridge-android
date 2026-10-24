# Release Process — SignalBridge

## Versioning

Semantic versioning (major.minor.patch) tracked in `app/build.gradle`
(versionName / versionCode) and git tags.

## Release gates (all must pass)

1. Compilation successful.
2. Unit tests pass.
3. Android lint passes.
4. Localization/resource validation passes.
5. APK built, non-zero size, correct package ID and version metadata.
6. APK signed with release keystore (debug APKs are NOT releases).
7. SHA-256 checksum generated.
8. Release notes prepared from actual CHANGELOG entries.
9. Compatibility limitations stated honestly.

## Build & sign

```bash
./gradlew assembleRelease
```
Release signing requires a `release` signingConfig in `app/build.gradle`
pointing to a keystore that is NOT committed. If no keystore is available,
build a debug artifact and document the remaining signing step — never label a
debug APK as a production release.

## GitHub Release

- Tag only after release gates pass.
- Attach the actual APK and SHA-256 checksum.
- State signing status, known compatibility limitations, and install instructions.
- Verify uploaded assets match the verified build.
- Least-privilege GitHub token; no secrets exposed to PR workflows.

## Current status

Blocked in this sandbox: no JDK/Android SDK to build/sign. CI workflow is
configured but cannot run locally. Do not declare a production release until
gates pass and a real device/carrier verification is recorded.
