package br.com.bjjtracker.ui.home.model

import java.time.LocalDate

data class TrainingItem(
    val date: LocalDate,
    val durationMinutes: Int,
    val partners: String,
    val focus: TechnicsEnum?,
)
