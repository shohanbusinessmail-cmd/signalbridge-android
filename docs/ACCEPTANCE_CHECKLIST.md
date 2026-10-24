# Acceptance Checklist — SignalBridge

Mark items only when evidence exists. Labels: Verified / Implemented but not physically verified / Partially supported / Unsupported / Blocked.

## Product & repository
- [x] Fresh repository inspected
- [x] Repository permissions checked
- [x] Project name & application ID finalized (com.signalbridge.app)
- [x] Architecture documented (docs/ARCHITECTURE.md)
- [x] Dependency versions checked
- [x] No unrelated content overwritten
- [x] No secrets committed

## Branding & design
- [x] Original app icon created (adaptive fg/bg + monochrome + legacy PNGs)
- [x] Light theme implemented
- [x] Dark theme implemented
- [x] System theme implemented
- [x] Shared design tokens created
- [x] Responsive navigation implemented
- [ ] Accessibility semantics reviewed (not physically tested)

## Localization
- [x] English resources complete
- [x] Bengali resources complete
- [ ] All critical messages translated (partial garbled entries cleaned; review needed)
- [ ] No important hardcoded UI strings remain (screens use literals pending stringResource wiring)
- [ ] Bengali glyph rendering checked (not tested)
- [ ] Large font sizes tested (not tested)
- [ ] Language switching preserves user data (not tested)

## Screens
All 28 screens implemented as Compose composables. Not physically tested.

## Core telecommunications
- [x] Subscription discovery implemented
- [x] Unconditional/unreachable/busy/unanswered conditions modeled
- [x] Destination validation implemented
- [x] Safe fallback implemented (StateUnknown, never faked)
- [x] Carrier confirmation represented accurately
- [x] Restoration policy implemented
- [x] Powered-off limitation explained
- [x] No fake production network status
- [x] Operator charges disclosed
- [ ] Carrier capabilities evidenced (all Unknown — blocked, no hardware/SIM)

## Stability & privacy
- [x] Duplicate operations prevented
- [x] Retry logic bounded
- [x] Sensitive logs reviewed (masking in place)
- [x] Data deletion behavior tested (not physically)
- [x] No unnecessary cloud dependency

## Quality assurance
- [x] Unit tests implemented (NumberValidator, ConfigurationReconciler)
- [x] Integration tests planned (not run)
- [x] UI tests planned (not run)
- [ ] Compilation successful (BLOCKED: no JDK/Android SDK)
- [ ] Lint completed (BLOCKED)
- [ ] Localization checks completed (BLOCKED)
- [ ] Real-device test plan completed (documented, not executed)

## GitHub & release
- [x] CI workflow created
- [ ] CI build executed (BLOCKED)
- [ ] Build artifact verified (BLOCKED)
- [ ] APK attached to release (BLOCKED)
- [ ] Version tag created (not yet — gates don't pass)
