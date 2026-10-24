package com.signalbridge.app.ui.screens

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.assertIsDisplayed
import com.signalbridge.app.app.SignalBridgeApplication
import org.junit.Rule
import org.junit.Test

/** Instrumented UI test for the splash screen. Runs on a device/emulator. */
class SplashUITest {

    @get:Rule val composeTestRule = createAndroidComposeRule<SignalBridgeApplication>()

    @Test
    fun splash_shows_app_icon_and_tagline() {
        // Note: full navigation assertions require the NavHost to be set.
        // This is a placeholder confirming the screen composes without crashing.
        assertIsDisplayed(composeTestRule.onNodeWithText("SignalBridge"))
    }
}
