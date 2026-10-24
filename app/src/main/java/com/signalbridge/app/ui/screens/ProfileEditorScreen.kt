package com.signalbridge.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.signalbridge.app.ui.state.ScreenViewModelFactory
import com.signalbridge.app.ui.state.ProfileEditorViewModel
import com.signalbridge.app.ui.theme.*
import com.signalbridge.app.ui.components.*

@Composable
fun ProfileEditorScreen(
    viewModelFactory: ScreenViewModelFactory,
    profileId: String,
    onNavigate: (String) -> Unit
) {
    val viewModel = viewModelFactory.create(ProfileEditorViewModel::class.java) as ProfileEditorViewModel
    val state by viewModel.state.collectAsState()

    viewModel.open(profileId)

    ScreenScaffold(modifier = Modifier.fillMaxSize()) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            Text(text = if (state.isNew) "create_profile" else "edit_profile", style = BrandTypography.Headline, color = BrandColors.TextPrimary)
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = state.name,
                onValueChange = { /* name handled via save */ },
                label = { Text(text = "profile_name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = state.description ?: "",
                onValueChange = { /* description handled via save */ },
                label = { Text(text = "profile_description_optional") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "recovery_policy", style = BrandTypography.Title, color = BrandColors.TextPrimary)
            Spacer(modifier = Modifier.height(8.dp))
            state.error?.let {
                Text(text = it, style = BrandTypography.Caption, color = BrandColors.Error)
                Spacer(modifier = Modifier.height(8.dp))
            }
            Spacer(modifier = Modifier.height(24.dp))
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                SecondaryButton(onClick = { onNavigate("profiles") }, text = "cancel")
                PrimaryButton(onClick = { viewModel.save(); onNavigate("profiles") }, text = "save")
            }
        }
    }
}
