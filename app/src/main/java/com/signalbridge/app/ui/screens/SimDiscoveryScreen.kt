package com.signalbridge.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.signalbridge.app.domain.model.SimSubscription
import com.signalbridge.app.ui.state.ScreenViewModelFactory
import com.signalbridge.app.ui.state.SimDiscoveryViewModel
import com.signalbridge.app.ui.theme.*
import com.signalbridge.app.ui.components.*

@Composable
fun SimDiscoveryScreen(
    viewModelFactory: ScreenViewModelFactory,
    onNavigate: (String) -> Unit
) {
    val viewModel = viewModelFactory.create(SimDiscoveryViewModel::class.java) as SimDiscoveryViewModel
    val state by viewModel.state.collectAsState()

    ScreenScaffold(modifier = Modifier.fillMaxSize()) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "sim_discovery_title", style = BrandTypography.Headline, color = BrandColors.TextPrimary)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "sim_discovery_subtitle", style = BrandTypography.Body, color = BrandColors.TextSecondary, textAlign = androidx.compose.ui.text.TextAlign.Center)
            Spacer(modifier = Modifier.height(24.dp))

            if (state.isLoading) {
                LoadingState(message = "discovering_sims")
            } else if (state.empty) {
                EmptyState(
                    title = "no_sim_found",
                    description = "no_sim_found_description",
                    action = { viewModel.refresh() },
                    actionText = "retry"
                )
            } else {
                state.subscriptions.forEach { sub ->
                    SimCardDisplay(
                        subscription = sub,
                        selected = false,
                        onClick = {}
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            SecondaryButton(onClick = { viewModel.refresh() }, text = "refresh")
            Spacer(modifier = Modifier.height(12.dp))
            PrimaryButton(onClick = { onNavigate("sim_selection") }, text = "continue")
        }
    }
}

@Composable
private fun SimCardDisplay(
    subscription: SimSubscription,
    selected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = if (selected) BrandColors.PrimaryContainer else BrandColors.SurfaceContainer
        )
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.size(40.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = Icons.Default.SimCard, contentDescription = null, tint = BrandColors.Primary)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(text = subscription.displayName(), style = BrandTypography.Title, color = BrandColors.TextPrimary)
                Text(text = subscription.displayLabel(), style = BrandTypography.Supporting, color = BrandColors.TextSecondary)
            }
            Spacer(modifier = Modifier.weight(1f))
            StatusChip(
                status = if (subscription.isActive) "verified" else "unknown",
                label = if (subscription.isActive) "active" else "inactive"
            )
        }
    }
}
