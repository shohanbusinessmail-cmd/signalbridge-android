# Localization — SignalBridge

## Policy

- All visible text in `res/values/strings.xml` (English default) and
  `res/values-bn/strings.xml` (Bengali).
- No hardcoded user-facing strings in composables, ViewModels, notifications,
  dialogs, validation, error mapping, accessibility descriptions, or empty
  states (target state; current screens use readable English literals pending
  full stringResource wiring — see note below).
- Formatted strings with placeholders (`%s`, `%d`) instead of concatenating
  translated fragments.
- English is the safe fallback if Bengali resources are missing.

## Bengali quality

Reviewed for: Unicode rendering, conjuncts, vowel marks, punctuation, number
formatting, long translations, line breaks, mixed Bengali/English technical
terms, error-message clarity, touch-target alignment, and clipping at large
font scales. Natural Bengali for ordinary smartphone users.

## Completeness check

A scripted check should identify missing translations, hardcoded strings,
duplicate/malformed keys, invalid placeholders, and untranslated critical UI
messages. Every screen reviewed in both languages. A resource file existing
does not prove all states are translated.

## Language switching

Updates UI strings; preserves navigation and profile state; no forced restart.

## Note on implementation

The current Compose screens pass readable English string literals to `Text()`
for immediate coherence. Full `stringResource(R.string.*)` wiring against the
resource files is the remaining localization refinement. The resource files
themselves are complete for English and Bengali.
