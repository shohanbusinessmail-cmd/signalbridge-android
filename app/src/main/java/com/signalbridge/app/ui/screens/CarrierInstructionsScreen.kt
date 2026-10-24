package com.signalbridge.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.signalbridge.app.ui.state.ScreenViewModelFactory
import com.signalbridge.app.ui.state.CarrierInstructionsViewModel
import com.signalbridge.app.ui.theme.*
import com.signalbridge.app.ui.components.*

@Composable
fun CarrierInstructionsScreen(
    viewModelFactory: ScreenViewModelFactory,
    onNavigate: (String) -> Unit
) {
    val viewModel = viewModelFactory.create(CarrierInstructionsViewModel::class.java) as CarrierInstructionsViewModel
    val state by viewModel.state.collectAsState()

    ScreenScaffold(modifier = Modifier.fillMaxSize()) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            Text(text = state.carrier, style = BrandTypography.Headline, color = BrandColors.TextPrimary)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "carrier_instructions_expl", style = BrandTypography.Body, color = BrandColors.TextSecondary)
            if (state.isUnverified) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "procedure_unverified", style = BrandTypography.Caption, color = BrandColors.Pending)
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text(text = state.instructions, style = BrandTypography.Body, color = BrandColors.TextSecondary)
            Spacer(modifier = Modifier.height(24.dp))
            Text(text = state.charges, style = BrandTypography.Caption, color = BrandColors.TextSecondary)
        }
    }
}
