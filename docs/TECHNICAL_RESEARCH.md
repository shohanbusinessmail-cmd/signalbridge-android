# Technical Research — SignalBridge

## Method

Researched official Android documentation and operator guidance before
implementation. Sources, verification dates, evidence, and unresolved questions
are recorded here. Nothing is inferred: a capability is "Unknown" until backed
by evidence (see docs/ARCHITECTURE.md and the CarrierRegistry).

## Android APIs investigated

| API / concept | Finding |
|---|---|
| TelephonyManager | Public API exposes call state, phone type, IMEI (privileged), but NOT call-forwarding provisioning or query for 3rd-party apps. |
| SubscriptionManager | activeSubscriptionInfoList gives subscriptionId, carrier, countryIso, number (requires READ_PHONE_STATE). |
| TelecomManager / PhoneAccount | Relevant to calling/routing, not call-forwarding supplementary services for 3rd-party apps. |
| Supplementary services | Call forwarding (SS) is carrier-managed; programmed via MMI/USSD or privileged ITelephony (not accessible to 3rd-party apps). |
| Default dialer | Only the default dialer can use certain restricted APIs; ordinary apps must use Intent.ACTION_DIAL (user confirms). |
| Carrier privileges | Privileged system permissions (e.g. MODIFY_PHONE_STATE) are not available to Play/GitHub-distributed apps. |
| ACTION_BOOT_COMPLETED | Available via manifest receiver (RECEIVE_BOOT_COMPLETED). Minimal work only; no telephony commands directly. |
| Android 12+ background restrictions | Background services heavily restricted; forwarding must not depend on a service surviving shutdown. |
| Android 13+ notifications | POST_NOTIFICATIONS runtime permission required. |
| Android 14+ background-service/receiver restrictions | Foreground service types and background broadcast limits observed; app avoids unnecessary background components. |

## Carrier research (Bangladesh)

Operators: Grameenphone, Robi, Banglalink, Teletalk.

Forwarding conditions: unconditional, unreachable (primary for powered-off
protection), busy, unanswered.

MMI/USSD: documented generically (e.g. **21*, **62*, **67*, **61*) but
**operator-specific codes are NOT confirmed** for Bangladeshi operators. All
capabilities in CarrierRegistry are "Unknown" with no evidence source.

## Key limitations (recorded)

1. No public Android API to query or set call forwarding for 3rd-party apps.
2. MMI activation requires the system dialer and user confirmation (Intent.ACTION_DIAL).
3. Network state often unqueryable → results reported as Unknown, never faked.
4. Powered-off protection requires unreachable forwarding pre-configured on the
   network; if the carrier doesn't support it, protection cannot be guaranteed.

## Unresolved questions

- Exact MMI/USSD codes and confirmation behavior for Grameenphone/Robi/Banglalink/Teletalk.
- Whether Bangladeshi operators support per-condition query via USSD.
- Applicable call-forwarding fees per operator.
- Verification mechanism for each carrier (network query vs. USSD vs. none).

## Evidence

No carrier tests performed (no physical device/SIM in this environment). All
carrier entries are "Unknown" / confidence "none". See docs/DEVICE_TEST_PLAN.md
for the test matrix to execute when hardware/operator access is available.
