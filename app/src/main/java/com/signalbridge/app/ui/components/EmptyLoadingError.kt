package com.signalbridge.app.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.signalbridge.app.ui.theme.BrandColors
import com.signalbridge.app.ui.theme.BrandTypography

@Composable
fun EmptyState(
    icon: @Composable () -> Unit = {},
    title: String,
    description: String?,
    action: (() -> Unit)? = null,
    actionText: String? = null,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.size(80.dp), contentAlignment = Alignment.Center) { icon() }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = title, style = BrandTypography.Title, color = BrandColors.TextPrimary)
        description?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = it, style = BrandTypography.Supporting, color = BrandColors.TextSecondary)
                .let { itModifier -> }
        }
        if (action != null && actionText != null) {
            Spacer(modifier = Modifier.height(16.dp))
            SecondaryButton(onClick = action, text = actionText)
        }
    }
}

@Composable
fun LoadingState(message: String? = null, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        message?.let {
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = it, style = BrandTypography.Supporting, color = BrandColors.TextSecondary)
        }
    }
}

@Composable
fun ErrorState(
    title: String,
    message: String?,
    onRetry: (() -> Unit)? = null,
    retryText: String? = null,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = title, style = BrandTypography.Title, color = BrandColors.Error)
        message?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = it, style = BrandTypography.Supporting, color = BrandColors.TextSecondary)
        }
        if (onRetry != null && retryText != null) {
            Spacer(modifier = Modifier.height(16.dp))
            PrimaryButton(onClick = onRetry, text = retryText)
        }
    }
}
