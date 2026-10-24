package com.signalbridge.app.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.signalbridge.app.ui.theme.BrandColors

@Composable
fun StatusChip(
    status: String,
    modifier: Modifier = Modifier,
    label: String? = null
) {
    val (bg, fg, text) = when (status) {
        "verified" -> Pair(BrandColors.SuccessContainer, BrandColors.OnSuccess, label ?: "verified")
        "inactive" -> Pair(BrandColors.SurfaceContainerHigh, BrandColors.OnSurfaceVariant, label ?: "inactive")
        "pending" -> Pair(BrandColors.PendingContainer, BrandColors.OnPending, label ?: "pending")
        "unknown" -> Pair(BrandColors.SurfaceContainerHigh, BrandColors.TextSecondary, label ?: "unknown")
        "unsupported" -> Pair(BrandColors.SurfaceContainerHigh, BrandColors.TextSecondary, label ?: "unsupported")
        "error" -> Pair(BrandColors.ErrorContainer, BrandColors.OnError, label ?: "error")
        else -> Pair(BrandColors.SurfaceContainerHigh, BrandColors.OnSurfaceVariant, label ?: status)
    }
    Box(
        modifier = modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .background(color = bg, shape = MaterialTheme.shapes.large),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.caption,
            color = fg,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
        )
    }
}
