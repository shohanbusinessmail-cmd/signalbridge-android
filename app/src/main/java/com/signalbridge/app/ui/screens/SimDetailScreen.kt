package com.signalbridge.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.signalbridge.app.ui.state.ScreenViewModelFactory
import com.signalbridge.app.ui.state.SimDetailViewModel
import com.signalbridge.app.ui.theme.*
import com.signalbridge.app.ui.components.*

@Composable
fun SimDetailScreen(
    viewModelFactory: ScreenViewModelFactory,
    subscriptionId: Int,
    onNavigate: (String) -> Unit
) {
    val viewModel = viewModelFactory.create(SimDetailViewModel::class.java) as SimDetailViewModel
    val state by viewModel.state.collectAsState()

    viewModel.setSubscription(subscriptionId)

    ScreenScaffold(modifier = Modifier.fillMaxSize()) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            if (state.subscription == null) {
                Text(text = "sim_not_found", style = BrandTypography.Body, color = BrandColors.TextSecondary)
            } else {
                Text(text = state.subscription!!.displayName(), style = BrandTypography.Headline, color = BrandColors.TextPrimary)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = state.subscription!!.maskedNumber(), style = BrandTypography.Supporting, color = BrandColors.TextSecondary)
                Spacer(modifier = Modifier.height(20.dp))

                Text(text = "current_forwarding_status", style = BrandTypography.Title, color = BrandColors.TextPrimary)
                Spacer(modifier = Modifier.height(12.dp))
                if (state.isLoading) LoadingState() else {
                    state.conditions.forEach { row ->
                        ConditionRowCard(
                            condition = row.condition.displayKey,
                            active = row.active,
                            destination = row.destination?.let { "→ $it" },
                            status = row.status
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    SecondaryButton(onClick = { viewModel.refresh() }, text = "refresh")
                    PrimaryButton(onClick = { onNavigate("forwarding_wizard") }, text = "manage")
                }
            }
        }
    }
}

@Composable
private fun ConditionRowCard(
    condition: String,
    active: Boolean,
    destination: String?,
    status: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = BrandColors.SurfaceContainer)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Column {
                Text(text = condition, style = BrandTypography.Title, color = BrandColors.TextPrimary)
                Text(text = destination ?: "no_destination", style = BrandTypography.Body, color = BrandColors.TextSecondary)
            }
            Spacer(modifier = Modifier.weight(1f))
            StatusChip(status = status)
        }
    }
}
