package br.com.bjjtracker.ui.home

import br.com.bjjtracker.domain.model.TrainingItem

sealed interface HomeAction {
    data object LoadHomeInfo : HomeAction
    data class SaveTraining(val item: TrainingItem) : HomeAction
}
