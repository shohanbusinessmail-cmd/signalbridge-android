package com.signalbridge.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.signalbridge.app.ui.state.ScreenViewModelFactory
import com.signalbridge.app.ui.state.SetupCompletionViewModel
import com.signalbridge.app.ui.theme.*
import com.signalbridge.app.ui.components.*

@Composable
fun SetupCompletionScreen(
    viewModelFactory: ScreenViewModelFactory,
    onNavigate: (String) -> Unit
) {
    val viewModel = viewModelFactory.create(SetupCompletionViewModel::class.java) as SetupCompletionViewModel
    val state by viewModel.state.collectAsState()

    ScreenScaffold(modifier = Modifier.fillMaxSize()) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(imageVector = Icons.Default.TaskAlt, contentDescription = null, tint = BrandColors.Success, modifier = Modifier.size(48.dp))
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "setup_complete_title", style = BrandTypography.Headline, color = BrandColors.TextPrimary)
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "setup_complete_summary", style = BrandTypography.Body, color = BrandColors.TextSecondary, textAlign = androidx.compose.ui.text.TextAlign.Center)
            Spacer(modifier = Modifier.height(24.dp))
            if (!state.allComplete) {
                state.pendingTask?.let {
                    Text(text = it, style = BrandTypography.BodyLarge, color = BrandColors.Pending)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
            PrimaryButton(onClick = { onNavigate("dashboard") }, text = "go_to_dashboard")
        }
    }
}
