# Security — SignalBridge

## Vulnerability reporting

Report security concerns to helloiamshohan@gmail.com. Do not open public issues
for security vulnerabilities.

## Scope

- Local-only app; no backend, no analytics.
- Sensitive data: phone numbers, SIM IDs, forwarding destinations (masked,
  not logged).
- No secrets in repository.

## Disclaimer

Call forwarding is carrier-managed. The app makes no guarantee of carrier
compatibility and never silently executes destructive telephony commands.
