package br.com.bjjtracker.domain.usecase

import br.com.bjjtracker.domain.model.TrainingItem
import br.com.bjjtracker.domain.repository.TrainingRepository

class RegisterTrainingUseCase(private val repository: TrainingRepository) {

    suspend operator fun invoke(item: TrainingItem) {
        return repository.insert(item)
    }
}