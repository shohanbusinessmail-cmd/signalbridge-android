package com.signalbridge.app.domain.model

/** Supported application languages. English is the safe fallback. */
enum class AppLanguage(val code: String, val displayName: String, val bnDisplayName: String) {
    ENGLISH("en", "English", "English"),
    BENGALI("bn", "বাংলা", "বাংলা");

    companion object {
        fun fromCode(code: String?): AppLanguage =
            values().firstOrNull { it.code == code } ?? ENGLISH
    }
}
