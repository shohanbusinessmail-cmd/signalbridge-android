package com.signalbridge.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.signalbridge.app.ui.state.ScreenViewModelFactory
import com.signalbridge.app.ui.state.CapabilityIntroViewModel
import com.signalbridge.app.ui.theme.*
import com.signalbridge.app.ui.components.*

@Composable
fun CapabilityIntroScreen(
    viewModelFactory: ScreenViewModelFactory,
    onNavigate: (String) -> Unit
) {
    val viewModel = viewModelFactory.create(CapabilityIntroViewModel::class.java) as CapabilityIntroViewModel
    val state by viewModel.state.collectAsState()

    ScreenScaffold(modifier = Modifier.fillMaxSize()) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "capability_intro_title",
                style = BrandTypography.Headline,
                color = BrandColors.TextPrimary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "capability_intro_subtitle",
                style = BrandTypography.Body,
                color = BrandColors.TextSecondary,
                textAlign = androidx.compose.ui.text.TextAlign.Center
            )
            Spacer(modifier = Modifier.height(24.dp))

            CapabilitySection(
                titleKey = "smart_call_forwarding",
                bodyKey = "smart_call_forwarding_body",
                icon = Icons.Default.Call
            )
            CapabilitySection(
                titleKey = "dual_sim_control",
                bodyKey = "dual_sim_control_body",
                icon = Icons.Default.SimCard
            )
            CapabilitySection(
                titleKey = "powered_off_protection",
                bodyKey = "powered_off_protection_body",
                icon = Icons.Default.Shield
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "powered_off_note",
                style = BrandTypography.Supporting,
                color = BrandColors.TextSecondary,
                textAlign = androidx.compose.ui.text.TextAlign.Center
            )
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                SecondaryButton(onClick = { /* learn more -> carrier instructions */ onNavigate("carrier_instructions") }, text = "learn_how_it_works")
                Spacer(modifier = Modifier.width(12.dp))
                PrimaryButton(
                    onClick = { onNavigate("permission_overview") },
                    enabled = state.chargesAcknowledged,
                    text = "get_started"
                )
            }
            CheckboxRow(
                checked = state.chargesAcknowledged,
                onCheckedChange = { viewModel.toggleChargesAcknowledged(it) },
                textKey = "charges_acknowledged"
            )
        }
    }
}

@Composable
private fun CapabilitySection(
    titleKey: String,
    bodyKey: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.size(56.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = BrandColors.Primary)
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = titleKey, style = BrandTypography.Title, color = BrandColors.TextPrimary)
        Spacer(modifier = Modifier.height(6.dp))
        Text(text = bodyKey, style = BrandTypography.Body, color = BrandColors.TextSecondary, textAlign = androidx.compose.ui.text.TextAlign.Center)
    }
}

@Composable
private fun CheckboxRow(checked: Boolean, onCheckedChange: (Boolean) -> Unit, textKey: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(checkColor = BrandColors.OnPrimary, uncheckedColor = BrandColors.Outline)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = textKey, style = BrandTypography.Body, color = BrandColors.TextSecondary)
    }
}
