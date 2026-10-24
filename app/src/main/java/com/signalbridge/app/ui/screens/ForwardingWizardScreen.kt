package com.signalbridge.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.signalbridge.app.domain.model.ForwardingCondition
import com.signalbridge.app.ui.state.ScreenViewModelFactory
import com.signalbridge.app.ui.state.ForwardingWizardViewModel
import com.signalbridge.app.ui.theme.*
import com.signalbridge.app.ui.components.*

@Composable
fun ForwardingWizardScreen(
    viewModelFactory: ScreenViewModelFactory,
    onNavigate: (String) -> Unit
) {
    val viewModel = viewModelFactory.create(ForwardingWizardViewModel::class.java) as ForwardingWizardViewModel
    val state by viewModel.state.collectAsState()

    ScreenScaffold(modifier = Modifier.fillMaxSize()) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            // Progress
            val stepIndex = when (state.step) {
                ForwardingWizardViewModel.Step.SELECT_SIM -> 0
                ForwardingWizardViewModel.Step.CHOOSE_DESTINATION -> 1
                ForwardingWizardViewModel.Step.CHOOSE_CONDITION -> 2
                ForwardingWizardViewModel.Step.CHOOSE_POLICY -> 3
                ForwardingWizardViewModel.Step.CAPABILITY_CHECK -> 4
                ForwardingWizardViewModel.Step.REVIEW -> 5
                ForwardingWizardViewModel.Step.APPLY -> 6
                ForwardingWizardViewModel.Step.COMPLETE -> 7
            }
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth(),
                progress = (stepIndex + 1).toFloat() / 7f
            )
            Spacer(modifier = Modifier.height(24.dp))

            when (state.step) {
                ForwardingWizardViewModel.Step.SELECT_SIM -> WizardSelectSim(viewModel, onNavigate)
                ForwardingWizardViewModel.Step.CHOOSE_DESTINATION -> WizardDestination(viewModel, onNavigate)
                ForwardingWizardViewModel.Step.CHOOSE_CONDITION -> WizardCondition(viewModel, onNavigate)
                ForwardingWizardViewModel.Step.CHOOSE_POLICY -> WizardPolicy(viewModel, onNavigate)
                ForwardingWizardViewModel.Step.CAPABILITY_CHECK -> WizardCapability(viewModel, onNavigate)
                ForwardingWizardViewModel.Step.REVIEW -> WizardReview(viewModel, onNavigate)
                ForwardingWizardViewModel.Step.APPLY -> WizardApply(viewModel, onNavigate)
                ForwardingWizardViewModel.Step.COMPLETE -> WizardComplete(viewModel, onNavigate)
            }

            Spacer(modifier = Modifier.height(20.dp))
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                if (state.step != ForwardingWizardViewModel.Step.SELECT_SIM) {
                    SecondaryButton(onClick = { viewModel.previousStep() }, text = "back")
                }
                Spacer(modifier = Modifier.weight(1f))
                if (state.step != ForwardingWizardViewModel.Step.COMPLETE) {
                    PrimaryButton(onClick = { viewModel.nextStep() }, enabled = true, text = "next")
                } else {
                    PrimaryButton(onClick = { onNavigate("dashboard") }, text = "done")
                }
            }
        }
    }
}

@Composable
private fun WizardSelectSim(viewModel: ForwardingWizardViewModel, onNavigate: (String) -> Unit) {
    Text(text = "wizard_select_sim", style = BrandTypography.Title, color = BrandColors.TextPrimary)
    Spacer(modifier = Modifier.height(16.dp))
    Text(text = "wizard_select_sim_expl", style = BrandTypography.Body, color = BrandColors.TextSecondary)
}

@Composable
private fun WizardDestination(viewModel: ForwardingWizardViewModel, onNavigate: (String) -> Unit) {
    Text(text = "wizard_choose_destination", style = BrandTypography.Title, color = BrandColors.TextPrimary)
    Spacer(modifier = Modifier.height(16.dp))
    Text(text = "wizard_destination_expl", style = BrandTypography.Body, color = BrandColors.TextSecondary)
    onNavigate("destination_picker")
}

@Composable
private fun WizardCondition(viewModel: ForwardingWizardViewModel, onNavigate: (String) -> Unit) {
    Text(text = "wizard_choose_condition", style = BrandTypography.Title, color = BrandColors.TextPrimary)
    Spacer(modifier = Modifier.height(16.dp))
    ForwardingCondition.values().forEach { condition ->
        SelectableCard(
            selected = false,
            onClick = { viewModel.setCondition(condition); viewModel.nextStep() },
            title = condition.displayKey,
            body = condition.descriptionKey
        )
        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
private fun WizardPolicy(viewModel: ForwardingWizardViewModel, onNavigate: (String) -> Unit) {
    Text(text = "wizard_choose_policy", style = BrandTypography.Title, color = BrandColors.TextPrimary)
    Spacer(modifier = Modifier.height(16.dp))
    com.signalbridge.app.domain.model.RecoveryPolicy.values().forEach { policy ->
        SelectableCard(
            selected = false,
            onClick = { viewModel.setRecoveryPolicy(policy); viewModel.nextStep() },
            title = policy.displayKey,
            body = policy.descriptionKey
        )
        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
private fun WizardCapability(viewModel: ForwardingWizardViewModel, onNavigate: (String) -> Unit) {
    Text(text = "wizard_capability_check", style = BrandTypography.Title, color = BrandColors.TextPrimary)
    Spacer(modifier = Modifier.height(16.dp))
    Text(text = "wizard_capability_expl", style = BrandTypography.Body, color = BrandColors.TextSecondary)
}

@Composable
private fun WizardReview(viewModel: ForwardingWizardViewModel, onNavigate: (String) -> Unit) {
    Text(text = "wizard_review", style = BrandTypography.Title, color = BrandColors.TextPrimary)
    Spacer(modifier = Modifier.height(16.dp))
    Text(text = "destination: ${state.config.destination}", style = BrandTypography.Body, color = BrandColors.TextSecondary)
    Text(text = "condition: ${state.config.condition.displayKey}", style = BrandTypography.Body, color = BrandColors.TextSecondary)
}

@Composable
private fun WizardApply(viewModel: ForwardingWizardViewModel, onNavigate: (String) -> Unit) {
    Text(text = "wizard_apply", style = BrandTypography.Title, color = BrandColors.TextPrimary)
    if (state.isApplying) LoadingState(message = "applying_configuration")
    state.result?.let { result ->
        Text(text = "result: ${result::class.simpleName}", style = BrandTypography.Body, color = BrandColors.TextSecondary)
    }
    viewModel.apply()
}

@Composable
private fun WizardComplete(viewModel: ForwardingWizardViewModel, onNavigate: (String) -> Unit) {
    Text(text = "wizard_complete", style = BrandTypography.Title, color = BrandColors.TextPrimary)
    state.result?.let {
        Text(text = it::class.simpleName ?: "done", style = BrandTypography.Body, color = BrandColors.TextSecondary)
    }
}

@Composable
private fun SelectableCard(selected: Boolean, onClick: () -> Unit, title: String, body: String) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = if (selected) BrandColors.PrimaryContainer else BrandColors.SurfaceContainer
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, style = BrandTypography.Title, color = BrandColors.TextPrimary)
            Text(text = body, style = BrandTypography.Supporting, color = BrandColors.TextSecondary)
        }
    }
}
