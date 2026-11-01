package br.com.bjjtracker.ui.home.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

private val dateFormatter = DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy", Locale.forLanguageTag("pt-BR"))

fun formatDate(date: LocalDate): String = date.format(dateFormatter)

fun formatDuration(minutes: Int): String {
    val hours = minutes / 60
    val remainingMinutes = minutes % 60
    return when {
        hours == 0 -> "${remainingMinutes}min"
        remainingMinutes == 0 -> "${hours}h"
        else -> "${hours}h ${remainingMinutes}min"
    }
}

fun Int.decreaseDuration(): Int = (this - 15).coerceAtLeast(15)

fun Int.increaseDuration(): Int = (this + 15).coerceAtMost(8 * 60)
