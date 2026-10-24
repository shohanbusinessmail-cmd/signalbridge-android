package com.signalbridge.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.signalbridge.app.ui.state.ScreenViewModelFactory
import com.signalbridge.app.ui.state.SmartProtectionViewModel
import com.signalbridge.app.ui.theme.*
import com.signalbridge.app.ui.components.*

@Composable
fun SmartProtectionScreen(
    viewModelFactory: ScreenViewModelFactory,
    onNavigate: (String) -> Unit
) {
    val viewModel = viewModelFactory.create(SmartProtectionViewModel::class.java) as SmartProtectionViewModel
    val state by viewModel.state.collectAsState()

    ScreenScaffold(modifier = Modifier.fillMaxSize()) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            Text(text = "smart_protection_title", style = BrandTypography.Headline, color = BrandColors.TextPrimary)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "smart_protection_subtitle", style = BrandTypography.Body, color = BrandColors.TextSecondary)
            Spacer(modifier = Modifier.height(24.dp))

            Text(text = "readiness_checklist", style = BrandTypography.Title, color = BrandColors.TextPrimary)
            Spacer(modifier = Modifier.height(12.dp))
            state.checklist.forEach { item ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = item.satisfied,
                        onCheckedChange = { viewModel.toggleItem(item.id) }
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(text = item.titleKey, style = BrandTypography.Body, color = BrandColors.TextPrimary)
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = if (state.unreachableConfigured) "unreachable_configured" else "unreachable_not_configured",
                style = BrandTypography.Body,
                color = if (state.unreachableConfigured) BrandColors.Success else BrandColors.TextSecondary
            )
            Spacer(modifier = Modifier.height(24.dp))
            PrimaryButton(
                onClick = { viewModel.enableProtection() },
                enabled = viewModel.canEnable(),
                text = "enable_protection"
            )
            if (!viewModel.canEnable()) {
                Text(text = "protection_incomplete", style = BrandTypography.Caption, color = BrandColors.TextSecondary)
            }
        }
    }
}
