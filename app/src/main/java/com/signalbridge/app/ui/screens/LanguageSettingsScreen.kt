package com.signalbridge.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.signalbridge.app.ui.state.ScreenViewModelFactory
import com.signalbridge.app.ui.state.LanguageSettingsViewModel
import com.signalbridge.app.ui.theme.*
import com.signalbridge.app.ui.components.*

@Composable
fun LanguageSettingsScreen(
    viewModelFactory: ScreenViewModelFactory,
    onNavigate: (String) -> Unit
) {
    val viewModel = viewModelFactory.create(LanguageSettingsViewModel::class.java) as LanguageSettingsViewModel
    val state by viewModel.state.collectAsState()

    ScreenScaffold(modifier = Modifier.fillMaxSize()) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            Text(text = "language_settings_title", style = BrandTypography.Headline, color = BrandColors.TextPrimary)
            Spacer(modifier = Modifier.height(24.dp))
            state.languages.forEach { language ->
                Row(
                    modifier = Modifier.fillMaxWidth().clickable(onClick = { viewModel.select(language) }),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = state.selected == language,
                        onClick = { viewModel.select(language) }
                    )
                    Text(
                        text = when (language) {
                            com.signalbridge.app.domain.model.AppLanguage.BENGALI -> "বাংলা"
                            com.signalbridge.app.domain.model.AppLanguage.ENGLISH -> "English"
                        },
                        style = BrandTypography.Body,
                        color = BrandColors.TextPrimary
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}
