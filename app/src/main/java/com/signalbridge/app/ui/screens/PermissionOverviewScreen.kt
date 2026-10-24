package com.signalbridge.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.signalbridge.app.ui.state.ScreenViewModelFactory
import com.signalbridge.app.ui.state.PermissionOverviewViewModel
import com.signalbridge.app.ui.theme.*
import com.signalbridge.app.ui.components.*

@Composable
fun PermissionOverviewScreen(
    viewModelFactory: ScreenViewModelFactory,
    onNavigate: (String) -> Unit
) {
    val viewModel = viewModelFactory.create(PermissionOverviewViewModel::class.java) as PermissionOverviewViewModel
    val state by viewModel.state.collectAsState()

    ScreenScaffold(modifier = Modifier.fillMaxSize()) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "permissions_title", style = BrandTypography.Headline, color = BrandColors.TextPrimary)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "permissions_subtitle", style = BrandTypography.Body, color = BrandColors.TextSecondary, textAlign = androidx.compose.ui.text.TextAlign.Center)
            Spacer(modifier = Modifier.height(24.dp))

            if (state.requirements.isEmpty()) {
                Text(text = "no_permissions_required", style = BrandTypography.Body, color = BrandColors.TextSecondary)
            } else {
                state.requirements.forEach { req ->
                    PermissionCard(
                        requirement = req,
                        granted = viewModel.hasPermission(req),
                        onRequest = { /* request handled at OS level; navigate onward */ },
                        onOpenSettings = { viewModel.openSettings() }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            PrimaryButton(onClick = { onNavigate("sim_discovery") }, text = "continue")
        }
    }
}

@Composable
private fun PermissionCard(
    requirement: com.signalbridge.app.domain.model.PermissionRequirement,
    granted: Boolean,
    onRequest: () -> Unit,
    onOpenSettings: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = BrandColors.SurfaceContainer)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Icon(
                    imageVector = if (granted) Icons.Default.CheckCircle else Icons.Default.ErrorOutline,
                    contentDescription = null,
                    tint = if (granted) BrandColors.Success else BrandColors.TextSecondary
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(text = requirement.rationaleKey, style = BrandTypography.Title, color = BrandColors.TextPrimary)
                    Text(text = requirement.rationaleDetailKey ?: "", style = BrandTypography.Supporting, color = BrandColors.TextSecondary)
                }
                Spacer(modifier = Modifier.weight(1f))
                StatusChip(status = if (granted) "verified" else "unknown")
            }
            if (!granted && requirement.canOpenSettings) {
                Spacer(modifier = Modifier.height(12.dp))
                Row(horizontalArrangement = Arrangement.End) {
                    SecondaryButton(onClick = onOpenSettings, text = "open_settings")
                }
            }
        }
    }
}
