package com.signalbridge.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.signalbridge.app.ui.state.ScreenViewModelFactory
import com.signalbridge.app.ui.state.AboutViewModel
import com.signalbridge.app.ui.theme.*
import com.signalbridge.app.ui.components.*

@Composable
fun AboutScreen(
    viewModelFactory: ScreenViewModelFactory,
    onNavigate: (String) -> Unit
) {
    val viewModel = viewModelFactory.create(AboutViewModel::class.java) as AboutViewModel
    val state by viewModel.state.collectAsState()

    ScreenScaffold(modifier = Modifier.fillMaxSize()) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding), horizontalAlignment = Alignment.CenterHorizontally) {
            Image(imageVector = Icons.Default.Phone, contentDescription = null, tint = BrandColors.Primary, modifier = Modifier.size(48.dp))
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "SignalBridge", style = BrandTypography.Headline, color = BrandColors.TextPrimary)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "v${state.version} (build ${state.build})", style = BrandTypography.Caption, color = BrandColors.TextSecondary)
            Spacer(modifier = Modifier.height(24.dp))
            Text(text = "developed_by_${state.developer}", style = BrandTypography.Body, color = BrandColors.TextSecondary)
            Text(text = state.email, style = BrandTypography.Body, color = BrandColors.TextSecondary)
            Spacer(modifier = Modifier.height(24.dp))
            SecondaryButton(onClick = { viewModel.openEmail() }, text = "contact_email")
        }
    }
}
