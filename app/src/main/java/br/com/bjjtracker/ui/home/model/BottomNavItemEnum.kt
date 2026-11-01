package br.com.bjjtracker.ui.home.model

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import br.com.bjjtracker.R

enum class BottomNavItemEnum(@StringRes val labelRes: Int, val icon: ImageVector) {
    HOME(R.string.nav_home, Icons.Filled.Home),
    TRAININGS(R.string.nav_trainings, Icons.Filled.Event),
    TECHNIQUES(R.string.nav_techniques, Icons.Filled.MenuBook),
    PROFILE(R.string.nav_profile, Icons.Filled.Person),
}
