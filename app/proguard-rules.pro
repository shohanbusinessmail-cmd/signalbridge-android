# SignalBridge ProGuard rules.
# Add project-specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.

-keep class androidx.activity.ComponentActivity { *; }

# Keep Compose compiler generated code.
-keep class androidx.compose.** { *; }

# Keep data classes used for settings/history (serialization).
-keepclassmembers class com.signalbridge.app.domain.model.** {
    <fields>;
}

# Keep reflection-friendly model classes.
-keepnames class com.signalbridge.app.domain.model.* { *; }

# Do not obfuscate the gateway implementations (runtime telephony).
-keep class com.signalbridge.app.data.telephony.** { *; }
