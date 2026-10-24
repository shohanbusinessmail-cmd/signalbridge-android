package com.signalbridge.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.signalbridge.app.domain.model.DiagnosticStatus
import com.signalbridge.app.ui.state.ScreenViewModelFactory
import com.signalbridge.app.ui.state.DiagnosticsViewModel
import com.signalbridge.app.ui.theme.*
import com.signalbridge.app.ui.components.*

@Composable
fun DiagnosticsScreen(
    viewModelFactory: ScreenViewModelFactory,
    onNavigate: (String) -> Unit
) {
    val viewModel = viewModelFactory.create(DiagnosticsViewModel::class.java) as DiagnosticsViewModel
    val state by viewModel.state.collectAsState()

    ScreenScaffold(modifier = Modifier.fillMaxSize()) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            Text(text = "diagnostics_title", style = BrandTypography.Headline, color = BrandColors.TextPrimary)
            Spacer(modifier = Modifier.height(16.dp))
            if (state.isRunning) {
                LoadingState(message = "running_checks")
            } else {
                PrimaryButton(onClick = { viewModel.runChecks() }, text = "run_diagnostics")
            }
            Spacer(modifier = Modifier.height(20.dp))
            if (state.findings.isEmpty() && !state.isRunning) {
                Text(text = "run_diagnostics_to_begin", style = BrandTypography.Body, color = BrandColors.TextSecondary)
            } else {
                state.findings.forEach { finding ->
                    DiagnosticRow(finding = finding)
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
private fun DiagnosticRow(finding: com.signalbridge.app.domain.model.DiagnosticFinding) {
    val color = when (finding.status) {
        DiagnosticStatus.PASS -> BrandColors.Success
        DiagnosticStatus.WARN -> BrandColors.Pending
        DiagnosticStatus.FAIL -> BrandColors.Error
        else -> BrandColors.TextSecondary
    }
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = BrandColors.SurfaceContainer)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Column {
                Text(text = finding.titleKey, style = BrandTypography.Title, color = BrandColors.TextPrimary)
                Text(text = finding.explanationKey, style = BrandTypography.Body, color = BrandColors.TextSecondary)
            }
            Spacer(modifier = Modifier.weight(1f))
            StatusChip(
                status = when (finding.status) {
                    DiagnosticStatus.PASS -> "verified"
                    DiagnosticStatus.WARN -> "pending"
                    DiagnosticStatus.FAIL -> "error"
                    else -> "unknown"
                }
            )
        }
    }
}
