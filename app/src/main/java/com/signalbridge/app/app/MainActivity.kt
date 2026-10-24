package com.signalbridge.app.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.MaterialTheme
import com.signalbridge.app.ui.theme.SignalBridgeTheme
import com.signalbridge.app.ui.navigation.SignalBridgeNavGraph

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Edge-to-edge: let content draw behind system bars, insets handled by
        // the navigation/scaffold layer.
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val context = LocalContext.current
            val app = context.applicationContext as SignalBridgeApplication
            SignalBridgeTheme {
                // System bar styling handled per-screen by the scaffold.
                SignalBridgeNavGraph(app = app)
            }
        }
    }
}
