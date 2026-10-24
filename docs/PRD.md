# Product Requirements Document — SignalBridge

## 1. Product overview

**Product:** SignalBridge — Smart Call Forwarding  
**Category:** Native Android telecommunications utility  
**Developer:** Shohan Khan (helloiamshohan@gmail.com)  
**Markets:** Bangladesh (primary), extensible to other countries  
**Languages:** Bengali (বাংলা) and English  
**Distribution:** GitHub Releases (APK)  
**Backend:** None. No mandatory paid services.

## 2. Problem

Users with one or two SIM cards can miss incoming calls when the phone runs out
of battery, is powered off while charging, loses coverage, or is temporarily
unavailable. Carrier call forwarding can route calls to an alternate number, but
configuring it is carrier-specific, often manual, and not survivable across
power loss unless the right conditional rule (unreachable) is pre-configured on
the network.

## 3. Goals

- Reliably configure, inspect, and manage carrier-supported call forwarding per
  SIM subscription.
- Provide powered-off protection via unreachable forwarding configured while
  the phone is on.
- Reconcile saved policy with actual carrier state after boot / network
  recovery, safely.
- Be honest about what is verified vs. assumed: no fake carrier status.
- Ship Bengali and English, accessible UI; local-first; no cloud dependency.

## 4. Non-goals

- No guaranteed universal carrier compatibility.
- No background service that survives power-off (impossible).
- No silent dialing / accessibility automation.
- No mandatory account, cloud sync, or payment.

## 5. User stories (summary)

1. As a user, I can choose Bengali or English on first launch.
2. As a user, I see what the app can and cannot automate before granting anything.
3. As a user, I discover my SIMs and select one or both.
4. As a user, I pick an alternate destination number and a forwarding condition.
5. As a user, I choose a restoration policy (preserve, reconcile, restore, manual).
6. As a user, I authorize forwarding through the system dialer and see whether
   the carrier confirmed it.
7. As a user, after reboot, the app checks actual state and reconciles per my
   policy.
8. As a user, I can view diagnostics, history, and retry failed operations.

## 6. Forwarding conditions

- Unconditional — all calls redirected; warned that normal reception may break.
- When unreachable — primary powered-off protection mode.
- When busy.
- When unanswered — timeout exposed only when the carrier/API allows it.
- Combined protection — only where supported, with conflict detection.

## 7. Result model

Forwarding results are rich, never Boolean:
SuccessConfirmed, SuccessUnverified, PendingUserAction, Unsupported,
PermissionDenied, CarrierRejected, NetworkUnavailable, SubscriptionUnavailable,
InvalidNumber, OperationTimedOut, StateUnknown, UnexpectedFailure.

## 8. Acceptance criteria

- All 28 screens + empty/loading/offline/unsupported/unknown states implemented.
- CallForwardingGateway abstraction with the operations in the spec.
- Carrier registry with Bangladesh operators, all capabilities "Unknown" until
  evidenced.
- English + Bengali string resources; Bengali glyphs render.
- Original adaptive app icon (foreground, background, monochrome).
- Unit + integration + UI tests written; CI workflow present.
- Honest documentation of build/test/release blockers (no Android SDK in this
  sandbox).
