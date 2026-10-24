package com.signalbridge.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.signalbridge.app.ui.state.ScreenViewModelFactory
import com.signalbridge.app.ui.state.ThemeSettingsViewModel
import com.signalbridge.app.ui.theme.*
import com.signalbridge.app.ui.components.*

@Composable
fun ThemeSettingsScreen(
    viewModelFactory: ScreenViewModelFactory,
    onNavigate: (String) -> Unit
) {
    val viewModel = viewModelFactory.create(ThemeSettingsViewModel::class.java) as ThemeSettingsViewModel
    val state by viewModel.state.collectAsState()

    ScreenScaffold(modifier = Modifier.fillMaxSize()) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            Text(text = "theme_settings_title", style = BrandTypography.Headline, color = BrandColors.TextPrimary)
            Spacer(modifier = Modifier.height(24.dp))
            state.modes.forEach { mode ->
                Row(
                    modifier = Modifier.fillMaxWidth().clickable(onClick = { viewModel.select(mode) }),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = state.selected == mode,
                        onClick = { viewModel.select(mode) }
                    )
                    Text(text = mode.displayKey, style = BrandTypography.Body, color = BrandColors.TextPrimary)
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}
