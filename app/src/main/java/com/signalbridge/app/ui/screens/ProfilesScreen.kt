package com.signalbridge.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.signalbridge.app.domain.model.ForwardingProfile
import com.signalbridge.app.ui.state.ScreenViewModelFactory
import com.signalbridge.app.ui.state.ProfilesViewModel
import com.signalbridge.app.ui.theme.*
import com.signalbridge.app.ui.components.*

@Composable
fun ProfilesScreen(
    viewModelFactory: ScreenViewModelFactory,
    onNavigate: (String) -> Unit
) {
    val viewModel = viewModelFactory.create(ProfilesViewModel::class.java) as ProfilesViewModel
    val state by viewModel.state.collectAsState()

    ScreenScaffold(modifier = Modifier.fillMaxSize()) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text(text = "profiles_title", style = BrandTypography.Headline, color = BrandColors.TextPrimary)
                Spacer(modifier = Modifier.weight(1f))
                PrimaryButton(onClick = { onNavigate("profile_editor") }, text = "create_profile")
            }
            Spacer(modifier = Modifier.height(20.dp))
            if (state.isLoading) {
                LoadingState()
            } else if (state.profiles.isEmpty()) {
                EmptyState(
                    title = "no_profiles",
                    description = "no_profiles_description",
                    action = { onNavigate("profile_editor") },
                    actionText = "create_profile"
                )
            } else {
                state.profiles.forEach { profile ->
                    ProfileCard(profile = profile, viewModel = viewModel)
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
private fun ProfileCard(profile: ForwardingProfile, viewModel: ProfilesViewModel) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = BrandColors.SurfaceContainer)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Column {
                Text(text = profile.name, style = BrandTypography.Title, color = BrandColors.TextPrimary)
                Text(text = profile.description ?: "", style = BrandTypography.Supporting, color = BrandColors.TextSecondary)
            }
            Spacer(modifier = Modifier.weight(1f))
            Column(horizontalAlignment = Alignment.End) {
                StatusChip(status = if (profile.isDefault) "verified" else "inactive")
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    SecondaryButton(onClick = { viewModel.setDefault(profile) }, text = "set_default")
                    Spacer(modifier = Modifier.width(8.dp))
                    SecondaryButton(onClick = { viewModel.delete(profile) }, text = "delete")
                }
            }
        }
    }
}
