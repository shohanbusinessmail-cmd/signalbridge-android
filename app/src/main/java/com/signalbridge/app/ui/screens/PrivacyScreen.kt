package com.signalbridge.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.signalbridge.app.ui.state.ScreenViewModelFactory
import com.signalbridge.app.ui.state.PrivacyViewModel
import com.signalbridge.app.ui.theme.*
import com.signalbridge.app.ui.components.*

@Composable
fun PrivacyScreen(
    viewModelFactory: ScreenViewModelFactory,
    onNavigate: (String) -> Unit
) {
    val viewModel = viewModelFactory.create(PrivacyViewModel::class.java) as PrivacyViewModel
    val state by viewModel.state.collectAsState()

    ScreenScaffold(modifier = Modifier.fillMaxSize()) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            Text(text = "privacy_title", style = BrandTypography.Headline, color = BrandColors.TextPrimary)
            Spacer(modifier = Modifier.height(24.dp))
            PrivacySection(title = "data_collected") {
                Text(text = "no_analytics", style = BrandTypography.Body, color = BrandColors.TextSecondary)
            }
            PrivacySection(title = "data_stored_locally") {
                Text(text = "profiles_and_history_local", style = BrandTypography.Body, color = BrandColors.TextSecondary)
            }
            PrivacySection(title = "carrier_operations") {
                Text(text = "carrier_manages_forwarding", style = BrandTypography.Body, color = BrandColors.TextSecondary)
            }
            SecondaryButton(onClick = { viewModel.clearLocalData() }, text = "clear_local_data")
        }
    }
}

@Composable
private fun PrivacySection(title: String, content: @Composable () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = title, style = BrandTypography.Title, color = BrandColors.TextPrimary)
        Spacer(modifier = Modifier.height(8.dp))
        content()
        Spacer(modifier = Modifier.height(20.dp))
    }
}
