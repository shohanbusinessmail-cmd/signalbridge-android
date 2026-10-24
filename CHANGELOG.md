# Changelog — SignalBridge

## 1.0.0 (unreleased)

- Initial implementation of SignalBridge smart call forwarding.
- Full screen set (28 screens + empty/loading/offline/unsupported/unknown states).
- CallForwardingGateway abstraction; TelephonyManager/SubscriptionManager reads;
  MMI dialer activation with user confirmation.
- Carrier registry: Grameenphone, Robi, Banglalink, Teletalk (all Unknown).
- Bengali (বাংলা) and English string resources.
- Adaptive app icon (foreground, background, monochrome) + legacy PNGs.
- DataStore preferences + Room profiles/history.
- Unit tests for NumberValidator and ConfigurationReconciler.
- GitHub Actions CI workflow.
- Comprehensive docs (PRD, architecture, research, UI/UX, localization, test
  plan, build, troubleshooting, privacy, release).

## Notes

- Not yet compiled/built (no Android SDK in this environment).
- No carrier or device tests performed.
- Not a production release.
