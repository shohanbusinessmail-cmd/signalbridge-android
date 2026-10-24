package com.signalbridge.app.ui.navigation

/** Navigation routes for every screen in the application. */
object NavRoutes {
    const val SPLASH = "splash"
    const val WELCOME = "welcome"
    const val CAPABILITY_INTRO = "capability_intro"
    const val PERMISSION_OVERVIEW = "permission_overview"
    const val SIM_DISCOVERY = "sim_discovery"
    const val SIM_SELECTION = "sim_selection"
    const val DASHBOARD = "dashboard"
    const val SIM_DETAIL = "sim_detail"
    const val FORWARDING_WIZARD = "forwarding_wizard"
    const val DESTINATION_PICKER = "destination_picker"
    const val CONDITION_DETAILS = "condition_details"
    const val QUICK_CONTROL = "quick_control"
    const val SMART_PROTECTION = "smart_protection"
    const val PROFILES = "profiles"
    const val PROFILE_EDITOR = "profile_editor"
    const val DIAGNOSTICS = "diagnostics"
    const val HISTORY = "history"
    const val NOTIFICATIONS = "notifications"
    const val SETTINGS = "settings"
    const val LANGUAGE_SETTINGS = "language_settings"
    const val THEME_SETTINGS = "theme_settings"
    const val PERMISSION_RECOVERY = "permission_recovery"
    const val CARRIER_INSTRUCTIONS = "carrier_instructions"
    const val TROUBLESHOOTING = "troubleshooting"
    const val ABOUT = "about"
    const val PRIVACY = "privacy"
    const val SETUP_COMPLETION = "setup_completion"
}

/** Arguments carried between screens. */
object NavArgs {
    const val SUBSCRIPTION_ID = "subscriptionId"
    const val PROFILE_ID = "profileId"
    const val CONDITION = "condition"
    const val SCREEN = "screen"
}
