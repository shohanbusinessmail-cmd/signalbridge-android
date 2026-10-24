package com.signalbridge.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.signalbridge.app.domain.model.ForwardingCondition
import com.signalbridge.app.domain.model.ForwardingOperationResult
import com.signalbridge.app.ui.state.ScreenViewModelFactory
import com.signalbridge.app.ui.state.QuickControlViewModel
import com.signalbridge.app.ui.theme.*
import com.signalbridge.app.ui.components.*

@Composable
fun QuickControlScreen(
    viewModelFactory: ScreenViewModelFactory,
    onNavigate: (String) -> Unit
) {
    val viewModel = viewModelFactory.create(QuickControlViewModel::class.java) as QuickControlViewModel
    val state by viewModel.state.collectAsState()

    ScreenScaffold(modifier = Modifier.fillMaxSize()) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            Text(text = "quick_control_title", style = BrandTypography.Headline, color = BrandColors.TextPrimary)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "quick_control_warning", style = BrandTypography.Body, color = BrandColors.TextSecondary)
            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = state.destination,
                onValueChange = { viewModel.setDestination(it) },
                label = { Text(text = "destination_number") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            ForwardingCondition.values().forEach { condition ->
                Row(
                    modifier = Modifier.fillMaxWidth().clickable(onClick = { viewModel.setCondition(condition) }),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = state.condition == condition,
                        onClick = { viewModel.setCondition(condition) }
                    )
                    Text(text = condition.displayKey, style = BrandTypography.Body, color = BrandColors.TextPrimary)
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
            Spacer(modifier = Modifier.height(24.dp))
            PrimaryButton(
                onClick = { viewModel.toggle() },
                enabled = state.destination.isNotBlank() && !state.isOperating,
                text = if (state.isActive) "deactivate" else "activate"
            )
            if (state.isOperating) LoadingState(message = "operation_in_progress")
            state.result?.let { result ->
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = result::class.simpleName ?: "result",
                    style = BrandTypography.Body,
                    color = BrandColors.TextSecondary
                )
            }
            SecondaryButton(onClick = { onNavigate("diagnostics") }, text = "run_diagnostics")
        }
    }
}
