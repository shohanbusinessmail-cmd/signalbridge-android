# Device Test Plan — SignalBridge

## Status

No physical Android device or operator SIM is available in this environment, so
device and carrier tests are **planned, not executed**. Execute when hardware
and operator access are available and record results here.

## Device matrix

| Device | SIMs | Android | Operator | Status |
|---|---|---|---|---|
| Single-SIM phone | 1 | 13/14/15 | Grameenphone | Pending |
| Dual-SIM phone | 2 | 13/14 | Grameenphone + Robi | Pending |
| eSIM device | eSIM | 14 | Any | Pending |

## Carrier matrix

| Operator | Unreachable | Busy | Unanswered | Unconditional | Verification |
|---|---|---|---|---|---|
| Grameenphone | Unknown | Unknown | Unknown | Unknown | Pending |
| Robi | Unknown | Unknown | Unknown | Unknown | Pending |
| Banglalink | Unknown | Unknown | Unknown | Unknown | Pending |
| Teletalk | Unknown | Unknown | Unknown | Unknown | Pending |

## Manual verification steps

1. Install debug APK; grant READ_PHONE_STATE / READ_PHONE_NUMBERS / POST_NOTIFICATIONS.
2. Discover SIMs; verify carrier name and slot labels.
3. Select SIM; enter destination; choose condition; run wizard.
4. Confirm in system dialer; observe PendingUserAction → check result.
5. Reboot; verify app reconciles per chosen recovery policy.
6. Power off; call the SIM from another number; confirm it routes to the
   alternate number (unreachable protection).
7. Out of coverage; same test.
8. View diagnostics, history, notification center; verify no full numbers exposed.
9. Switch language to Bengali; verify all screens render.
10. Clear local data; confirm carrier forwarding is NOT automatically disabled.

## Evidence to record

Device model, Android version, carrier, test date, SIM config, forwarding
condition, expected result, actual result, evidence, known limitations.

## Recorded results

None yet — blocked by lack of physical hardware and operator accounts.
