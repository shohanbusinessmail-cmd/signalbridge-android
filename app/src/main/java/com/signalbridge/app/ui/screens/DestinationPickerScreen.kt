package com.signalbridge.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.signalbridge.app.domain.validation.NumberValidator
import com.signalbridge.app.ui.state.ScreenViewModelFactory
import com.signalbridge.app.ui.state.DestinationPickerViewModel
import com.signalbridge.app.ui.theme.*
import com.signalbridge.app.ui.components.*

@Composable
fun DestinationPickerScreen(
    viewModelFactory: ScreenViewModelFactory,
    onNavigate: (String) -> Unit
) {
    val viewModel = viewModelFactory.create(DestinationPickerViewModel::class.java) as DestinationPickerViewModel
    val state by viewModel.state.collectAsState()

    ScreenScaffold(modifier = Modifier.fillMaxSize()) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            Text(text = "destination_picker_title", style = BrandTypography.Headline, color = BrandColors.TextPrimary)
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = state.number,
                onValueChange = { viewModel.onNumberChanged(it) },
                label = { Text(text = "phone_number") },
                placeholder = { Text(text = "e.g. +8801XXXXXXXXX") },
                modifier = Modifier.fillMaxWidth()
            )
            state.error?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = it, style = BrandTypography.Caption, color = BrandColors.Error)
            }
            state.normalized?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "formatted_${NumberValidator.mask(it)}", style = BrandTypography.Supporting, color = BrandColors.TextSecondary)
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text(text = "saved_destinations", style = BrandTypography.Title, color = BrandColors.TextPrimary)
            if (state.savedDestinations.isEmpty()) {
                Text(text = "no_saved_destinations", style = BrandTypography.Body, color = BrandColors.TextSecondary)
            } else {
                state.savedDestinations.forEach { dest ->
                    SecondaryButton(onClick = { viewModel.selectSaved(dest) }, text = NumberValidator.mask(dest))
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                SecondaryButton(onClick = { onNavigate("forwarding_wizard") }, text = "cancel")
                PrimaryButton(onClick = { onNavigate("forwarding_wizard") }, enabled = state.isValid, text = "save")
            }
        }
    }
}
