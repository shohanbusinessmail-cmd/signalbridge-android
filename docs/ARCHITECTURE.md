# Architecture — SignalBridge

## Stack

- Language: Kotlin (JVM target 17)
- UI: Jetpack Compose + Material 3, Navigation Compose
- Async: Kotlin Coroutines + Flow
- Persistence: DataStore (preferences), Room (profiles + operation history)
- Work: WorkManager (deferrable refreshes)
- Min SDK 24 / Target SDK 35 / Compile SDK 35
- Application ID: `com.signalbridge.app`

## Module layout (single app module)

```
app/src/main/java/com/signalbridge/app/
  app/             Application + composition root + MainActivity
  core/            (reserved for shared core)
  data/
    local/         Room database, DAOs, entities
    prefs/         DataStore preferences
    repository/    Repository implementations
    telephony/     CallForwardingGateway impl + MMI codes
  domain/
    model/         Typed domain models
    repository/    Repository interfaces
    validation/    NumberValidator
    reconciliation/ ConfigurationReconciler
  permissions/     (reserved)
  profiles/        (reserved)
  diagnostics/     (reserved)
  notifications/   (reserved)
  navigation/      Nav routes + nav graph
  ui/
    theme/         Color, Type, Theme tokens
    components/    Shared composables
    screens/       One composable per screen
    state/         ViewModels + UI states
```

## Domain models

SimSubscription, ForwardingCondition, ForwardingRule, SubscriptionForwardingState,
ForwardingProfile, DesiredForwardingConfiguration, ObservedForwardingConfiguration,
ForwardingOperation, ForwardingOperationResult (sealed), CarrierCapability,
CarrierRegistry, PermissionRequirement, RecoveryPolicy, DiagnosticFinding,
AppPreferences, AppLanguage, ThemeMode.

## Telephony abstraction

`CallForwardingGateway` is the platform-independent interface:
isAvailable, capability, observe, enable, disable, applyConfiguration,
openConfirmationWorkflow, requiredPermissions, isConfirmationPending.

Honest platform fact: the public Android SDK does not expose call-forwarding
provisioning or querying to ordinary third-party apps. The implementation reads
subscription info via TelephonyManager/SubscriptionManager and exposes MMI/USSD
activation through the system dialer (Intent.ACTION_DIAL) with explicit user
confirmation. Any state it cannot actually query is reported as StateUnknown.

## State management

Unidirectional state flow per screen: ViewModel exposes a StateFlow<UiState>;
screens collect with collectAsState(); telephony operations run in a
ViewModel-scoped coroutine; duplicate operations are guarded by an isOperating
flag; UI state survives configuration changes via ViewModel.

## Persistence

DataStore: language, theme, default profile, recovery policy, setup state,
charges acknowledged.
Room: ForwardingProfile and ForwardingOperation entities/DAOs.
Phone numbers and subscription identifiers treated as sensitive; masked before
display/logging; option to delete local profiles and history.

## Recovery

BootRecoveryReceiver (minimal, no direct telephony) schedules follow-up work.
SubscriptionChangeReceiver refreshes on subscription changes. Bounded retries,
no continuous polling, stale state timestamped.
