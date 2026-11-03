package br.com.bjjtracker.ui.home

sealed interface HomeEvent {
    data object TrainingSaved : HomeEvent
}