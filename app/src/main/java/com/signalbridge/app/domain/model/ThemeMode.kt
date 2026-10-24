package com.signalbridge.app.domain.model

enum class ThemeMode(
    val displayKey: String
) {
    SYSTEM("theme_system"),
    LIGHT("theme_light"),
    DARK("theme_dark");

    companion object {
        fun fromKey(key: String?): ThemeMode =
            values().firstOrNull { it.displayKey == key } ?? SYSTEM
    }
}
