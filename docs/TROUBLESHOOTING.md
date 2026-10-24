# Troubleshooting — SignalBridge

## Common failures

| Symptom | Likely cause | Action |
|---|---|---|
| No SIMs discovered | READ_PHONE_STATE denied, or no telephony hardware | Grant permission; check Android SIM settings; retry |
| Forwarding not confirmed | Carrier doesn't expose query; MMI not run | Check dashboard status "Unknown"; run diagnostics |
| Pending after dialer | User hasn't completed the dialer action | Complete the call in the system dialer; re-verify |
| "Unsupported" condition | Carrier/API doesn't support that condition | Use a supported condition; see carrier instructions |
| Permission denied | User denied; or carrier-controlled | Open settings; grant; or accept reduced functionality |
| No powered-off protection | Unreachable rule not pre-configured | Configure unreachable forwarding while phone is on |

## Android restrictions

- Background services cannot survive power-off; forwarding depends on carrier
  network rule, not the app.
- Battery Saver / manufacturer restrictions may delay refreshes.
- Force-stop or data clear removes local profiles but NOT carrier forwarding.

## Carrier limitations

- Forwarding support varies by operator and plan; some conditions unsupported.
- Call-forwarding fees may apply; disclosed in the app.
- MMI codes differ per operator; never assume a universal code.

## Recovery

- Boot: receiver schedules a state refresh (minimal work).
- Network/subscription change: bounded refresh with retry; stale state marked.
- Diagnostics screen: run checks, retry, open settings, view carrier instructions.
