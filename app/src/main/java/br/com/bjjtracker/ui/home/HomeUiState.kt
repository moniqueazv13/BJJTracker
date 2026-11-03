package br.com.bjjtracker.ui.home

import br.com.bjjtracker.ui.home.model.StatItem
import br.com.bjjtracker.ui.home.model.StatType

data class HomeUiState(
    val streakWeeks: Int = 0,
    val trainingsDone: Int = 0,
    val trainingsGoal: Int = 0,
    val trainingsThisMonth: Int = 0,
    val trainingsDeltaVsLastMonth: Int = 0,
    val timeOnMat: String = "",
    val averageSession: String = "",
    val savedTechniques: Int = 0,
    val technicsSaved: Int = 0,
    val favoriteTechnics: Int = 0,
    val previousMonthName: String = "",
) {
    val progress: Float
        get() = if (trainingsGoal <= 0) 0f
        else (trainingsDone.toFloat() / trainingsGoal).coerceIn(0f, 1f)
    val remaining: Int get() = (trainingsGoal - trainingsDone).coerceAtLeast(0)
    val progressPercent: Int get() = (progress * 100).toInt()

    val stats: List<StatItem>
        get() = listOf(
            StatItem(
                type = StatType.TRAININGS_THIS_MONTH,
                value = trainingsThisMonth.toString(),
                detailArgs = listOf(trainingsDeltaVsLastMonth, previousMonthName),
            ),
            StatItem(
                type = StatType.TIME_ON_MAT,
                value = timeOnMat,
                detailArgs = listOf(averageSession),
            ),
            StatItem(
                type = StatType.SAVED_TECHNIQUES,
                value = savedTechniques.toString(),
                detailArgs = listOf(favoriteTechnics),
            ),
        )
}