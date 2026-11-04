package br.com.bjjtracker.ui.home.utils

import android.content.Context
import br.com.bjjtracker.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

private val dateFormatter = DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy", Locale.forLanguageTag("pt-BR"))

fun formatDate(date: LocalDate): String = date.format(dateFormatter)

fun formatDuration(context: Context, minutes: Int): String {
    val hours = minutes / 60
    val remainingMinutes = minutes % 60
    return when {
        hours == 0 -> context.getString(R.string.duration_format_minutes, remainingMinutes)
        remainingMinutes == 0 -> context.getString(R.string.duration_format_hours, hours)
        else -> context.getString(R.string.duration_format_hours_minutes, hours, remainingMinutes)
    }
}

fun Int.decreaseDuration(): Int = (this - 15).coerceAtLeast(15)

fun Int.increaseDuration(): Int = (this + 15).coerceAtMost(8 * 60)
