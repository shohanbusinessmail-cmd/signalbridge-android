package com.signalbridge.app.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.signalbridge.app.ui.theme.SignalBridgeTheme

@Composable
fun ScreenScaffold(
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.background,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        modifier = modifier,
        containerColor = containerColor
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            content(padding)
        }
    }
}
