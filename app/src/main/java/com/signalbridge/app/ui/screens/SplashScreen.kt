package com.signalbridge.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.signalbridge.app.ui.state.ScreenViewModelFactory
import com.signalbridge.app.ui.state.SplashViewModel
import com.signalbridge.app.ui.theme.*
import com.signalbridge.app.ui.components.*

@Composable
fun SplashScreen(
    viewModelFactory: ScreenViewModelFactory,
    onNavigate: (String) -> Unit
) {
    val viewModel = viewModelFactory.create(SplashViewModel::class.java) as SplashViewModel
    val state by viewModel.state.collectAsState()

    ScreenScaffold(modifier = Modifier.fillMaxSize()) { padding ->
        Box(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.Center) {
                // App icon placeholder (adaptive icon drawn as vector).
                Image(
                    imageVector = Icons.Default.Phone,
                    contentDescription = null,
                    modifier = Modifier.size(64.dp),
                    color = BrandColors.Primary
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "SignalBridge",
                    style = BrandTypography.Display,
                    color = BrandColors.TextPrimary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Your calls, always connected.",
                    style = BrandTypography.BodyLarge,
                    color = BrandColors.TextSecondary
                )
            }

            when (state) {
                is SplashViewModel.UiState.Loading -> LoadingState()
                is SplashViewModel.UiState.Ready -> {
                    LaunchedEffect(Unit) {
                        if (state.setupComplete) {
                            onNavigate("dashboard")
                        } else {
                            onNavigate("welcome")
                        }
                    }
                }
                is SplashViewModel.UiState.Error -> ErrorState(
                    title = "initialization_failed",
                    message = state.message,
                    onRetry = { /* retry handled by restart */ },
                    retryText = "retry"
                )
            }
        }
    }
}
