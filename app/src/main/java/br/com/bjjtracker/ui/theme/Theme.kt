package br.com.bjjtracker.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = NavyDark,
    onPrimary = Color.White,
    secondary = Mint,
    onSecondary = Color.White,
    tertiary = BlueAccent,
    background = SurfaceLight,
    onBackground = NavyDark,
    surface = Color.White,
    onSurface = NavyDark,
    onSurfaceVariant = TextMuted,
)

@Composable
fun BJJTrackerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}