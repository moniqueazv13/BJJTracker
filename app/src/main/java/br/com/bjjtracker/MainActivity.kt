package br.com.bjjtracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import br.com.bjjtracker.ui.navigation.AppNavHost
import br.com.bjjtracker.ui.theme.BJJTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BJJTrackerTheme {
                AppNavHost()
            }
        }
    }
}
