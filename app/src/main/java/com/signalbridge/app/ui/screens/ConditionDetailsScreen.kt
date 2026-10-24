package com.signalbridge.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.signalbridge.app.ui.state.ScreenViewModelFactory
import com.signalbridge.app.ui.state.ConditionDetailsViewModel
import com.signalbridge.app.ui.theme.*
import com.signalbridge.app.ui.components.*

@Composable
fun ConditionDetailsScreen(
    viewModelFactory: ScreenViewModelFactory,
    onNavigate: (String) -> Unit
) {
    val viewModel = viewModelFactory.create(ConditionDetailsViewModel::class.java) as ConditionDetailsViewModel
    val state by viewModel.state.collectAsState()

    ScreenScaffold(modifier = Modifier.fillMaxSize()) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            Text(text = "condition_details_title", style = BrandTypography.Headline, color = BrandColors.TextPrimary)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = state.condition.descriptionKey, style = BrandTypography.Body, color = BrandColors.TextSecondary)
            Spacer(modifier = Modifier.height(24.dp))

            ConditionToggleCard(
                condition = state.condition.displayKey,
                active = state.active,
                destination = state.destination,
                onToggle = { viewModel.toggle() }
            )
            Spacer(modifier = Modifier.height(24.dp))
            SecondaryButton(onClick = { onNavigate("dashboard") }, text = "done")
        }
    }
}

@Composable
private fun ConditionToggleCard(
    condition: String,
    active: Boolean,
    destination: String?,
    onToggle: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = BrandColors.SurfaceContainer)
    ) {
        Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
            Column {
                Text(text = condition, style = BrandTypography.Title, color = BrandColors.TextPrimary)
                Text(
                    text = if (active) "enabled" else "disabled",
                    style = BrandTypography.Body,
                    color = BrandColors.TextSecondary
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = active,
                onCheckedChange = { onToggle() },
                colors = SwitchDefaults.colors(thumbColor = BrandColors.Primary, trackColor = BrandColors.Outline)
            )
        }
    }
}
