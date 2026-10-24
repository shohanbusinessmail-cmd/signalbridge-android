package com.signalbridge.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.signalbridge.app.domain.model.ForwardingOperation
import com.signalbridge.app.ui.state.ScreenViewModelFactory
import com.signalbridge.app.ui.state.HistoryViewModel
import com.signalbridge.app.ui.theme.*
import com.signalbridge.app.ui.components.*

@Composable
fun HistoryScreen(
    viewModelFactory: ScreenViewModelFactory,
    onNavigate: (String) -> Unit
) {
    val viewModel = viewModelFactory.create(HistoryViewModel::class.java) as HistoryViewModel
    val state by viewModel.state.collectAsState()

    viewModel.load()

    ScreenScaffold(modifier = Modifier.fillMaxSize()) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            Text(text = "history_title", style = BrandTypography.Headline, color = BrandColors.TextPrimary)
            Spacer(modifier = Modifier.height(20.dp))
            if (state.isLoading) LoadingState() else if (state.records.isEmpty()) {
                EmptyState(
                    title = "no_history",
                    description = "no_history_description"
                )
            } else {
                state.records.forEach { record ->
                    HistoryRow(record = record)
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            SecondaryButton(onClick = { viewModel.clear() }, text = "clear_history")
        }
    }
}

@Composable
private fun HistoryRow(record: ForwardingOperation) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = BrandColors.SurfaceContainer)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Column {
                Text(text = record.resultSummaryKey, style = BrandTypography.Title, color = BrandColors.TextPrimary)
                Text(text = "sub_${record.subscriptionId}  |  ${record.condition}", style = BrandTypography.Caption, color = BrandColors.TextSecondary)
            }
            Spacer(modifier = Modifier.weight(1f))
            StatusChip(status = if (record.carrierConfirmed) "verified" else "pending")
        }
    }
}
