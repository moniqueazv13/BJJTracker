package br.com.bjjtracker.domain.usecase

import br.com.bjjtracker.domain.model.TrainingItem
import br.com.bjjtracker.domain.repository.TrainingRepository
import kotlinx.coroutines.flow.Flow

class GetTrainingsUseCase(private val repository: TrainingRepository) {

    operator fun invoke(): Flow<List<TrainingItem>> = repository.observeAll()
}