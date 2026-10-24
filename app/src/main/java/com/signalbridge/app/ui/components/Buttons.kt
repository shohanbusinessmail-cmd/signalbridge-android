package com.signalbridge.app.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.signalbridge.app.ui.theme.BrandColors

@Composable
fun PrimaryButton(
    onClick: () -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    text: String,
    contentPadding: PaddingValues = PaddingValues(horizontal = 24.dp, vertical = 14.dp)
) {
    val enabledAlpha = if (enabled) 1f else 0.5f
    Box(
        modifier = modifier
            .clickable(enabled = enabled, onClickLabel = text, onClick = onClick)
            .padding(contentPadding)
            .background(
                color = if (enabled) BrandColors.Primary else BrandColors.Primary.copy(alpha = 0.3f),
                shape = MaterialTheme.shapes.medium
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleLarge,
            color = BrandColors.OnPrimary,
            modifier = Modifier.alpha(enabledAlpha)
        )
    }
}

@Composable
fun SecondaryButton(
    onClick: () -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    text: String
) {
    Box(
        modifier = modifier
            .clickable(enabled = enabled, onClickLabel = text, onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 12.dp)
            .background(
                color = BrandColors.SurfaceContainerHigh,
                shape = MaterialTheme.shapes.medium
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            color = if (enabled) BrandColors.Primary else BrandColors.TextDisabled,
            modifier = Modifier.alpha(if (enabled) 1f else 0.5f)
        )
    }
}
