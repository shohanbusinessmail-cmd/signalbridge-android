# SignalBridge — Smart Call Forwarding

Native Android telecom utility that reliably configures, inspects, and manages
carrier-supported call forwarding across one or multiple SIM subscriptions —
including protection when the device is powered off or unreachable.

**Developer:** Shohan Khan — helloiamshohan@gmail.com  
**Primary markets:** Bangladesh (Bengali + English)  
**Distribution:** GitHub Releases (downloadable APK)  
**Backend:** None required. No mandatory paid services.

## What it does

- Discover SIM subscriptions (single-SIM and dual-SIM devices).
- Configure call forwarding per SIM: unconditional, when unreachable, when busy,
  when unanswered, plus a combined protection profile.
- Primary powered-off protection mode: configure **unreachable forwarding** on
  the carrier network while the phone is on, so calls reach your alternate
  number even after the phone powers down.
- Boot and network recovery: on reconnect, the app inspects actual carrier
  state and reconciles it with your saved policy (never changing carrier
  settings automatically unless you choose a corrective policy).
- Local profiles, operation history, diagnostics, and sanitized export.
- Bengali and English interface.

## Honest limitations

- The public Android SDK does **not** expose call-forwarding provisioning or
  querying to ordinary third-party apps. SignalBridge therefore activates
  forwarding through the **system dialer** using carrier MMI/USSD codes, with
  your explicit confirmation — it never silently dials.
- Because network state often cannot be queried, results are reported as
  "Carrier confirmed", "Request submitted", or "Unknown" — never faked.
- Powered-off protection depends on your carrier supporting unreachable
  forwarding. If it does not, reliable protection cannot be guaranteed.
- Operator call-forwarding charges are outside the app's control.

See `docs/` for the full specification, research, test plan, and release
process.

## Build

Requires the Android SDK (compileSdk 35, JDK 17) and Gradle.

```bash
./gradlew assembleDebug
./gradlew assembleRelease
```

CI builds and tests run on every push/PR via GitHub Actions
(`.github/workflows/ci.yml`).

## Release

Releases are published to GitHub Releases with a signed APK and SHA-256
checksum. See `docs/RELEASE_PROCESS.md`.

## License

MIT — see `LICENSE`.
