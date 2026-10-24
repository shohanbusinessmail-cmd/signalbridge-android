package com.signalbridge.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.signalbridge.app.ui.state.ScreenViewModelFactory
import com.signalbridge.app.ui.state.DashboardViewModel
import com.signalbridge.app.ui.theme.*
import com.signalbridge.app.ui.components.*

@Composable
fun DashboardScreen(
    viewModelFactory: ScreenViewModelFactory,
    onNavigate: (String) -> Unit
) {
    val viewModel = viewModelFactory.create(DashboardViewModel::class.java) as DashboardViewModel
    val state by viewModel.state.collectAsState()

    ScreenScaffold(modifier = Modifier.fillMaxSize()) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            // Top app bar area
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Image(imageVector = Icons.Default.Phone, contentDescription = null, tint = BrandColors.Primary, modifier = Modifier.size(24.dp))
                Spacer(modifier = Modifier.width(12.dp))
                Text(text = "SignalBridge", style = BrandTypography.Title, color = BrandColors.TextPrimary)
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { onNavigate("settings") }) {
                    Icon(imageVector = Icons.Default.Settings, contentDescription = "settings", tint = BrandColors.TextSecondary)
                }
            }
            Spacer(modifier = Modifier.height(24.dp))

            // Primary protection card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.large,
                colors = CardDefaults.cardColors(containerColor = BrandColors.SurfaceContainer)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(text = "protection_status", style = BrandTypography.Title, color = BrandColors.TextPrimary)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "protection_not_configured", style = BrandTypography.BodyLarge, color = BrandColors.TextSecondary)
                    Spacer(modifier = Modifier.height(16.dp))
                    PrimaryButton(onClick = { onNavigate("forwarding_wizard") }, text = "configure_protection")
                }
            }
            Spacer(modifier = Modifier.height(20.dp))

            // SIM status section
            Text(text = "your_sims", style = BrandTypography.Title, color = BrandColors.TextPrimary)
            Spacer(modifier = Modifier.height(12.dp))
            if (state.sims.isEmpty()) {
                Text(text = "no_sims", style = BrandTypography.Body, color = BrandColors.TextSecondary)
            } else {
                state.sims.forEach { simCard ->
                    SimDashboardCard(simCard = simCard)
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
            Spacer(modifier = Modifier.height(20.dp))

            // Quick actions
            Text(text = "quick_actions", style = BrandTypography.Title, color = BrandColors.TextPrimary)
            Spacer(modifier = Modifier.height(12.dp))
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                QuickAction(icon = Icons.Default.Checklist, label = "verify_network", onClick = { viewModel.onVerify() })
                QuickAction(icon = Icons.Default.People, label = "profiles", onClick = { onNavigate("profiles") })
                QuickAction(icon = Icons.Default.BugReport, label = "diagnostics", onClick = { onNavigate("diagnostics") })
            }
            Spacer(modifier = Modifier.height(24.dp))

            // Recent activity
            Text(text = "recent_activity", style = BrandTypography.Title, color = BrandColors.TextPrimary)
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "no_recent_activity", style = BrandTypography.Body, color = BrandColors.TextSecondary)
        }

        BottomNavigation {
            Destinations.entries.forEach { dest ->
                BottomNavigationItem(
                    selected = false,
                    onClick = { onNavigate(dest.route) },
                    icon = { Icon(imageVector = dest.icon, contentDescription = dest.label, tint = BrandColors.TextSecondary) },
                    label = { Text(text = dest.label, style = BrandTypography.Caption) }
                )
            }
        }
    }
}

@Composable
private fun SimDashboardCard(simCard: DashboardViewModel.SimCard) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = BrandColors.SurfaceContainer)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Column {
                Text(text = simCard.subscription.displayName(), style = BrandTypography.Title, color = BrandColors.TextPrimary)
                Text(text = simCard.subscription.maskedNumber(), style = BrandTypography.Supporting, color = BrandColors.TextSecondary)
            }
            Spacer(modifier = Modifier.weight(1f))
            Column(horizontalAlignment = Alignment.End) {
                StatusChip(status = simCard.status)
                Text(text = simCard.condition ?: "—", style = BrandTypography.Caption, color = BrandColors.TextSecondary)
            }
        }
    }
}

@Composable
private fun QuickAction(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier.size(56.dp).clickable(onClick = onClick),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = icon, contentDescription = label, tint = BrandColors.Primary)
        }
        Text(text = label, style = BrandTypography.Caption, color = BrandColors.TextSecondary)
    }
}

private object Destinations {
    val entries = listOf(
        Destination("dashboard", Icons.Default.Home, "home"),
        Destination("profiles", Icons.Default.People, "profiles"),
        Destination("history", Icons.Default.History, "activity"),
        Destination("settings", Icons.Default.Settings, "settings")
    )
}
data class Destination(val route: String, val icon: androidx.compose.ui.graphics.vector.ImageVector, val label: String)
