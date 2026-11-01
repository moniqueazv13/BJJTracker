package br.com.bjjtracker.ui.home

import androidx.lifecycle.ViewModel
import java.time.Clock
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel(clock: Clock = Clock.systemDefaultZone()) : ViewModel() {

    private val previousMonthName: String = LocalDate.now(clock)
        .minusMonths(1)
        .month
        .getDisplayName(TextStyle.FULL, Locale.forLanguageTag("pt-BR"))

    private val _uiState = MutableStateFlow(HomeUiState(previousMonthName = previousMonthName))
    val uiState: StateFlow<HomeUiState> = _uiState

}
