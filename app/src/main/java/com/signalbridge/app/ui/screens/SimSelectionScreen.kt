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
import com.signalbridge.app.ui.state.SimSelectionViewModel
import com.signalbridge.app.ui.theme.*
import com.signalbridge.app.ui.components.*

@Composable
fun SimSelectionScreen(
    viewModelFactory: ScreenViewModelFactory,
    onNavigate: (String) -> Unit
) {
    val viewModel = viewModelFactory.create(SimSelectionViewModel::class.java) as SimSelectionViewModel
    val state by viewModel.state.collectAsState()

    ScreenScaffold(modifier = Modifier.fillMaxSize()) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "sim_selection_title", style = BrandTypography.Headline, color = BrandColors.TextPrimary)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "sim_selection_subtitle", style = BrandTypography.Body, color = BrandColors.TextSecondary, textAlign = androidx.compose.ui.text.TextAlign.Center)
            Spacer(modifier = Modifier.height(24.dp))

            if (state.subscriptions.isEmpty()) {
                Text(text = "no_subscriptions", style = BrandTypography.Body, color = BrandColors.TextSecondary)
            } else {
                state.subscriptions.forEach { sub ->
                    SimSelectionCard(
                        subscription = sub,
                        selected = state.selectedIds.contains(sub.subscriptionId),
                        onClick = { viewModel.toggle(sub.subscriptionId) }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "selected_summary(${state.selectedIds.size})",
                style = BrandTypography.Supporting,
                color = BrandColors.TextSecondary
            )
            Spacer(modifier = Modifier.height(24.dp))
            PrimaryButton(
                onClick = {
                    if (state.selectedIds.isEmpty()) return@PrimaryButton
                    onNavigate("dashboard")
                },
                enabled = state.selectedIds.isNotEmpty(),
                text = "continue"
            )
        }
    }
}

@Composable
private fun SimSelectionCard(
    subscription: SimSubscription,
    selected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = if (selected) BrandColors.PrimaryContainer else BrandColors.SurfaceContainer
        )
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.size(24.dp),
                contentAlignment = Alignment.Center
            ) {
                if (selected) Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = BrandColors.OnPrimary
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(text = subscription.displayName(), style = BrandTypography.Title, color = BrandColors.TextPrimary)
                Text(text = subscription.maskedNumber(), style = BrandTypography.Supporting, color = BrandColors.TextSecondary)
            }
            Spacer(modifier = Modifier.weight(1f))
            StatusChip(status = if (subscription.isActive) "verified" else "unknown")
        }
    }
}
