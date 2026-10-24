package com.signalbridge.app.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * SignalBridge design tokens.
 *
 * Surfaces: deep midnight navy / charcoal.
 * Brand: refined indigo-blue.
 * Status: mint (verified), amber (pending), red (failure/disruptive).
 */
object BrandColors {
    // Surfaces
    val SurfaceBackground = Color(0xFF0B1020) // deep midnight navy
    val SurfaceContainer = Color(0xFF121933) // charcoal-navy container
    val SurfaceContainerHigh = Color(0xFF1C2544) // elevated container
    val SurfaceInverse = Color(0xFFE8EAF2)

    // Brand
    val Primary = Color(0xFF4F66FF) // refined indigo-blue
    val PrimaryContainer = Color(0xFF3A4ED8)
    val OnPrimary = Color(0xFFFFFFFF)
    val PrimaryFixed = Color(0xFFD9DEFF)

    // Status semantics
    val Success = Color(0xFF2E9E7B) // restrained mint/teal
    val SuccessContainer = Color(0xFF1F4B3B)
    val OnSuccess = Color(0xFFFFFFFF)
    val Pending = Color(0xFFF2A13A) // amber
    val PendingContainer = Color(0xFF7A4D0E)
    val OnPending = Color(0xFFFFFFFF)
    val Error = Color(0xFFD94242) // red
    val ErrorContainer = Color(0xFF6B2A2A)
    val OnError = Color(0xFFFFFFFF)
    val Warning = Color(0xFFF2A13A)

    // Neutral / typography
    val OnSurface = Color(0xFFF4F5F9)
    val OnSurfaceVariant = Color(0xFFC7CBD9)
    val Outline = Color(0xFF333C5F)
    val OutlineVariant = Color(0xFF242B48)
    val Scrim = Color(0x80000000)

    // Text
    val TextPrimary = Color(0xFFE8EAF2)
    val TextSecondary = Color(0xFFB9BFD4)
    val TextDisabled = Color(0xFF6E7593)
    val TextInverse = Color(0xFF0B1020)
}
