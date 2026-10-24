# UI/UX Specification — SignalBridge

## Brand

- Surfaces: deep midnight navy (#0B1020) and charcoal containers.
- Brand: refined indigo-blue (#4F66FF).
- Status: mint/teal verified (#2E9E7B), amber pending (#F2A13A), red error (#D94242).
- Typography: system fonts (Bengali + English), no fixed heights that clip at
  large font scales.

## Design tokens

Colors, typography, spacing, corners, elevation, motion, icon sizes, touch
targets (≥48dp), screen gutters, card padding, input heights — defined in
`app/src/main/java/com/signalbridge/app/ui/theme/` (Color.kt, Type.kt, Theme.kt).

## Screens (28) + states

01 Splash — icon, wordmark, tagline, no fake progress.
02 Welcome & language — বাংলা / English, immediate preview.
03 Capability & safety intro — three sections, powered-off note.
04 Permission & capability overview — per-card rationale, status, action.
05 SIM discovery — one card per subscription, refresh, empty state.
06 SIM selection — multi-select, per-SIM isolation summary.
07 Dashboard — protection card, SIM status, quick actions, activity, bottom nav.
08 SIM detail — per-condition rows, real query, unknown vs inactive.
09 Forwarding wizard — 8 steps, progress indicator, explicit confirmation.
10 Destination picker — country code, number input, validation, masking.
11 Condition details — per-condition enable/disable with confirmation.
12 Quick control — SIM/destination/condition, activation, duplicate-guard.
13 Smart protection — readiness checklist, unreachable rule required.
14 Profiles — create/edit/duplicate/activate/deactivate/delete.
15 Profile editor — name, SIMs, destinations, conditions, policy.
16 Diagnostics — categorized checks, retry/settings actions, sanitized export.
17 Operation history — chronological, filters, clear with confirmation.
18 Notification center — event list, no lock-screen numbers.
19 Settings — appearance, forwarding, notifications, privacy, support.
20 Language settings — Bengali/English.
21 Theme settings — system/light/dark.
22 Permission recovery — rationale, settings open, carrier-controlled note.
23 Carrier instructions — carrier, conditions, steps, verification, charges.
24 Failure & troubleshooting — categorized errors, retry/diagnostics/return.
25 About — version, developer, email, repo, credits.
26 Privacy & legal — data, carrier operations, licenses.
27 First-run completion — summary, pending tasks, go to dashboard.
28 Empty/loading/offline/unsupported/unknown — reusable states with next action.

## Interaction rules

- Idempotent operations; duplicate taps blocked; no main-thread telephony.
- Cancellation safe; state survives config changes.
- Destructive actions require confirmation; back navigation doesn't discard edits.
- Offline handled without crashing; stale data timestamped.
- Saved preference never shown as carrier-confirmed.
- Accessibility: content descriptions, state descriptions, TalkBack-friendly.
