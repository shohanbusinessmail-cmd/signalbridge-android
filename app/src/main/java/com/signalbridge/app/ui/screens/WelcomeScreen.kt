package com.signalbridge.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.signalbridge.app.domain.model.AppLanguage
import com.signalbridge.app.ui.state.ScreenViewModelFactory
import com.signalbridge.app.ui.state.WelcomeViewModel
import com.signalbridge.app.ui.theme.*
import com.signalbridge.app.ui.components.*

@Composable
fun WelcomeScreen(
    viewModelFactory: ScreenViewModelFactory,
    onNavigate: (String) -> Unit
) {
    val viewModel = viewModelFactory.create(WelcomeViewModel::class.java) as WelcomeViewModel
    val state by viewModel.state.collectAsState()

    ScreenScaffold(modifier = Modifier.fillMaxSize()) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                imageVector = Icons.Default.Phone,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                color = BrandColors.Primary
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "welcome_to_signalbridge",
                style = BrandTypography.Headline,
                color = BrandColors.TextPrimary
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "welcome_explanation",
                style = BrandTypography.Body,
                color = BrandColors.TextSecondary,
                textAlign = androidx.compose.ui.text.TextAlign.Center
            )
            Spacer(modifier = Modifier.height(32.dp))

            // Language choice: Bengali / English.
            Column {
                LanguageChoice(
                    language = AppLanguage.BENGALI,
                    selected = state.selectedLanguage == AppLanguage.BENGALI,
                    onClick = { viewModel.selectLanguage(AppLanguage.BENGALI) }
                )
                LanguageChoice(
                    language = AppLanguage.ENGLISH,
                    selected = state.selectedLanguage == AppLanguage.ENGLISH,
                    onClick = { viewModel.selectLanguage(AppLanguage.ENGLISH) }
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            PrimaryButton(
                onClick = { viewModel.confirm(); onNavigate("capability_intro") },
                enabled = true,
                text = "continue"
            )
            state.error?.let {
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = it, style = BrandTypography.Caption, color = BrandColors.Error)
            }
        }
    }
}

@Composable
private fun LanguageChoice(
    language: AppLanguage,
    selected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp)
            .background(
                color = if (selected) BrandColors.PrimaryContainer else BrandColors.SurfaceContainer,
                shape = MaterialTheme.shapes.medium
            ),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = when (language) {
                    AppLanguage.BENGALI -> "বাংলা"
                    AppLanguage.ENGLISH -> "English"
                },
                style = BrandTypography.Title,
                color = BrandColors.TextPrimary
            )
            Spacer(modifier = Modifier.weight(1f))
            if (selected) Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = BrandColors.OnPrimary
            )
        }
    }
}
