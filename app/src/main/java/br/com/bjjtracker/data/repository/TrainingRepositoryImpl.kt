package br.com.bjjtracker.data.repository

import br.com.bjjtracker.data.local.TrainingDao
import br.com.bjjtracker.data.mapper.toDomain
import br.com.bjjtracker.data.mapper.toEntity
import br.com.bjjtracker.domain.model.TrainingItem
import br.com.bjjtracker.domain.repository.TrainingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TrainingRepositoryImpl(private val dao: TrainingDao) : TrainingRepository {

    override suspend fun insert(item: TrainingItem) = dao.insert(item.toEntity())

    override fun observeAll(): Flow<List<TrainingItem>> =
        dao.observeAll().map { entities -> entities.map { it.toDomain() } }
}