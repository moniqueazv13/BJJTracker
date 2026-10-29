package br.com.bjjtracker.ui.home

data class HomeUiState(
    val userInitials: String = "RM",
    val streakWeeks: Int = 3,
    val trainingsDone: Int = 18,
    val trainingsGoal: Int = 25,
    val trainingsThisMonth: Int = 8,
    val trainingsDeltaVsLastMonth: Int = 2,
    val timeOnMat: String = "11h 40m",
    val averageSession: String = "1h 27m",
    val savedTechniques: Int = 6,
) {
    val progress: Float get() = trainingsDone.toFloat() / trainingsGoal
    val remaining: Int get() = trainingsGoal - trainingsDone
    val progressPercent: Int get() = (progress * 100).toInt()
}