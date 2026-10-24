package com.signalbridge.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.signalbridge.app.ui.state.ScreenViewModelFactory
import com.signalbridge.app.ui.state.SettingsViewModel
import com.signalbridge.app.ui.theme.*
import com.signalbridge.app.ui.components.*

@Composable
fun SettingsScreen(
    viewModelFactory: ScreenViewModelFactory,
    onNavigate: (String) -> Unit
) {
    val viewModel = viewModelFactory.create(SettingsViewModel::class.java) as SettingsViewModel
    val state by viewModel.state.collectAsState()

    viewModel.load()

    ScreenScaffold(modifier = Modifier.fillMaxSize()) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            Text(text = "settings_title", style = BrandTypography.Headline, color = BrandColors.TextPrimary)
            Spacer(modifier = Modifier.height(24.dp))

            SettingsSection(title = "appearance") {
                LanguageRow(selected = state.language, onClick = { onNavigate("language_settings") })
                ThemeRow(selected = state.themeMode, onClick = { onNavigate("theme_settings") })
            }
            SettingsSection(title = "forwarding") {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(text = "default_recovery_policy", style = BrandTypography.Body, color = BrandColors.TextPrimary)
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = state.defaultRecoveryPolicy.displayKey, style = BrandTypography.Body, color = BrandColors.TextSecondary)
                }
            }
            SettingsSection(title = "privacy") {
                SettingsRow(icon = Icons.Default.Delete, label = "clear_history", onClick = {})
                SettingsRow(icon = Icons.Default.DeleteForever, label = "delete_profiles", onClick = {})
            }
            SettingsSection(title = "support") {
                SettingsRow(icon = Icons.Default.BugReport, label = "diagnostics", onClick = { onNavigate("diagnostics") })
                SettingsRow(icon = Icons.Default.Info, label = "about", onClick = { onNavigate("about") })
            }
        }
    }
}

@Composable
private fun SettingsSection(title: String, content: @Composable () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = title, style = BrandTypography.Title, color = BrandColors.TextSecondary)
        Spacer(modifier = Modifier.height(8.dp))
        content()
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
private fun LanguageRow(selected: com.signalbridge.app.domain.model.AppLanguage, onClick: () -> Unit) {
    SettingsRow(icon = Icons.Default.Language, label = "language_${selected.code}", onClick = onClick)
}

@Composable
private fun ThemeRow(selected: com.signalbridge.app.domain.model.ThemeMode, onClick: () -> Unit) {
    SettingsRow(icon = Icons.Default.BrightnessAuto, label = "theme_${selected.displayKey}", onClick = onClick)
}

@Composable
private fun SettingsRow(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = BrandColors.TextSecondary)
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = label, style = BrandTypography.Body, color = BrandColors.TextPrimary)
    }
}
