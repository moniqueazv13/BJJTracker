package br.com.bjjtracker.ui.home.model

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import br.com.bjjtracker.R
import br.com.bjjtracker.ui.theme.BlueAccent
import br.com.bjjtracker.ui.theme.Mint
import br.com.bjjtracker.ui.theme.OrangeAccent

enum class StatType(
    @StringRes val labelRes: Int,
    @StringRes val detailRes: Int?,
    val icon: ImageVector,
    val iconTint: Color,
    val iconBackground: Color,
) {
    TRAININGS_THIS_MONTH(
        labelRes = R.string.home_stat_trainings_month,
        detailRes = R.string.home_stat_trainings_delta,
        icon = Icons.Filled.CalendarToday,
        iconTint = Mint,
        iconBackground = Mint.copy(alpha = 0.15f),
    ),
    TIME_ON_MAT(
        labelRes = R.string.home_stat_time_on_mat,
        detailRes = R.string.home_stat_average_session,
        icon = Icons.Filled.Schedule,
        iconTint = BlueAccent,
        iconBackground = BlueAccent.copy(alpha = 0.12f),
    ),
    SAVED_TECHNIQUES(
        labelRes = R.string.home_stat_saved_techniques,
        detailRes = R.string.home_stat_focus_saved,
        icon = Icons.Filled.MenuBook,
        iconTint = OrangeAccent,
        iconBackground = OrangeAccent.copy(alpha = 0.15f),
    ),
}
