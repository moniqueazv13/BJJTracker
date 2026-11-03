package br.com.bjjtracker.domain.repository

import br.com.bjjtracker.domain.model.TrainingItem
import kotlinx.coroutines.flow.Flow

interface TrainingRepository {
    suspend fun insert(item: TrainingItem)
    fun observeAll(): Flow<List<TrainingItem>>
}