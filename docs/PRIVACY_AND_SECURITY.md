# Privacy & Security — SignalBridge

## Data handling

- No analytics by default. No accounts, cloud sync, contact uploads, ad IDs,
  remote tracking, paid APIs, or mandatory backend.
- Internet permission is NOT declared (core operation is local).
- Phone numbers, SIM identifiers, and forwarding destinations treated as
  sensitive: masked before display/logging, excluded from crash reports,
  sanitized in diagnostic exports.

## Local storage

- DataStore: language, theme, preferences (no phone numbers).
- Room: profiles and operation history on-device only.
- Users can delete local profiles and history. Deleting local data does NOT
  disable carrier-managed forwarding.

## Permissions

- READ_PHONE_STATE: discover subscriptions.
- READ_PHONE_NUMBERS: access subscription numbers.
- POST_NOTIFICATIONS: forwarding state events.
- RECEIVE_BOOT_COMPLETED: minimal boot recovery (no direct telephony).

## Carrier operations

Call forwarding is managed by the carrier network; the app may open the system
dialer with a carrier MMI code for your confirmation. The app does not silently
dial and does not log full numbers.

## Threat considerations

- Local data is not encrypted at rest (Android app-private storage).
- No secrets in source control; signing keystore never committed.
- MMI codes shown to the user are operator-specific and marked unverified
  until evidenced.
