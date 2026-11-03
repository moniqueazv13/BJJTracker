package br.com.bjjtracker.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.bjjtracker.domain.model.TrainingItem
import br.com.bjjtracker.domain.usecase.GetTrainingsUseCase
import br.com.bjjtracker.domain.usecase.RegisterTrainingUseCase
import br.com.bjjtracker.ui.home.utils.formatDuration
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import java.time.Clock
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val TRAININGS_GOAL = 25

class HomeViewModel(
    private val getTrainings: GetTrainingsUseCase,
    private val registerTraining: RegisterTrainingUseCase,
    private val clock: Clock,
) : ViewModel() {

    private val previousMonthName: String = LocalDate.now(clock)
        .minusMonths(1)
        .month
        .getDisplayName(TextStyle.FULL, Locale.forLanguageTag("pt-BR"))

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    private val _events = Channel<HomeEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    fun onAction(action: HomeAction) {
        when (action) {
            HomeAction.LoadHomeInfo -> loadHomeInfo()
            is HomeAction.SaveTraining -> saveTraining(action.item)
        }
    }

    private fun saveTraining(item: TrainingItem) {
        viewModelScope.launch {
            registerTraining(item)
            _events.send(HomeEvent.TrainingSaved)
        }
    }

    private fun loadHomeInfo() {
        viewModelScope.launch {
            getTrainings.invoke().collect { trainings ->
                val currentMonth = YearMonth.from(LocalDate.now(clock))
                val thisMonth = trainings.count { YearMonth.from(it.date) == currentMonth }
                val lastMonth =
                    trainings.count { YearMonth.from(it.date) == currentMonth.minusMonths(1) }
                val technicsSaved = trainings.count { it.technics != null }
                val totalMinutes = trainings.sumOf { it.durationMinutes }
                val averageMinutes = if (trainings.isEmpty()) 0 else totalMinutes / trainings.size

                _uiState.update {
                    it.copy(
                        trainingsDone = trainings.size,
                        trainingsGoal = TRAININGS_GOAL,
                        trainingsThisMonth = thisMonth,
                        trainingsDeltaVsLastMonth = thisMonth - lastMonth,
                        technicsSaved = technicsSaved,
                        timeOnMat = formatDuration(totalMinutes),
                        averageSession = formatDuration(averageMinutes),
                        previousMonthName = previousMonthName,
                    )
                }
            }
        }
    }
}
