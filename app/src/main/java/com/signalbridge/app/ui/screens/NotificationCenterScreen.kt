package com.signalbridge.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.signalbridge.app.ui.state.ScreenViewModelFactory
import com.signalbridge.app.ui.state.NotificationCenterViewModel
import com.signalbridge.app.ui.theme.*
import com.signalbridge.app.ui.components.*

@Composable
fun NotificationCenterScreen(
    viewModelFactory: ScreenViewModelFactory,
    onNavigate: (String) -> Unit
) {
    val viewModel = viewModelFactory.create(NotificationCenterViewModel::class.java) as NotificationCenterViewModel
    val state by viewModel.state.collectAsState()

    viewModel.load()

    ScreenScaffold(modifier = Modifier.fillMaxSize()) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            Text(text = "notifications_title", style = BrandTypography.Headline, color = BrandColors.TextPrimary)
            Spacer(modifier = Modifier.height(20.dp))
            if (state.isLoading) LoadingState() else if (state.items.isEmpty()) {
                EmptyState(title = "no_notifications", description = "no_notifications_description")
            } else {
                state.items.forEach { item ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = MaterialTheme.shapes.large,
                        colors = CardDefaults.cardColors(containerColor = BrandColors.SurfaceContainer)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = item.titleKey, style = BrandTypography.Title, color = BrandColors.TextPrimary)
                            item.bodyKey?.let {
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(text = it, style = BrandTypography.Body, color = BrandColors.TextSecondary)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}
