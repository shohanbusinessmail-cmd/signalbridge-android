package com.signalbridge.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.signalbridge.app.ui.state.ScreenViewModelFactory
import com.signalbridge.app.ui.state.TroubleshootingViewModel
import com.signalbridge.app.ui.theme.*
import com.signalbridge.app.ui.components.*

@Composable
fun TroubleshootingScreen(
    viewModelFactory: ScreenViewModelFactory,
    onNavigate: (String) -> Unit
) {
    val viewModel = viewModelFactory.create(TroubleshootingViewModel::class.java) as TroubleshootingViewModel
    val state by viewModel.state.collectAsState()

    ScreenScaffold(modifier = Modifier.fillMaxSize()) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            Text(text = "troubleshooting_title", style = BrandTypography.Headline, color = BrandColors.TextPrimary)
            Spacer(modifier = Modifier.height(24.dp))
            state.message?.let {
                ErrorState(title = state.errorCategory ?: "error", message = it, onRetry = { viewModel.retry() }, retryText = "retry")
            }
            SecondaryButton(onClick = { onNavigate("diagnostics") }, text = "run_diagnostics")
            SecondaryButton(onClick = { onNavigate("dashboard") }, text = "back_to_dashboard")
        }
    }
}
