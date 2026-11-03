package br.com.bjjtracker.domain.model

import java.time.LocalDate

data class TrainingItem(
    val id: Long = 0,
    val date: LocalDate,
    val durationMinutes: Int,
    val partners: String,
    val technics: TechnicsEnum?,
)