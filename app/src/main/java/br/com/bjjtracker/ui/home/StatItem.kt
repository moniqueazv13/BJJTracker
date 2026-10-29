package br.com.bjjtracker.ui.home

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class StatItem(
    val icon: ImageVector,
    val label: String,
    val value: String,
    val detail: String,
    val iconTint: Color,
    val iconBackground: Color,
)
