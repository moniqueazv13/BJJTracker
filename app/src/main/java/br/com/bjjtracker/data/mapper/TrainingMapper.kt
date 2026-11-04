package br.com.bjjtracker.data.mapper

import br.com.bjjtracker.data.local.TrainingEntity
import br.com.bjjtracker.domain.model.TrainingItem

fun TrainingEntity.toDomain(): TrainingItem = TrainingItem(
    id = id,
    date = date,
    durationMinutes = durationMinutes,
    partners = partners,
    technicItem = technicItem,
)

fun TrainingItem.toEntity(): TrainingEntity = TrainingEntity(
    id = id,
    date = date,
    durationMinutes = durationMinutes,
    partners = partners,
    technicItem = technicItem,
)