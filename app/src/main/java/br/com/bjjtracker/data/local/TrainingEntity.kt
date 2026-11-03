package br.com.bjjtracker.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.bjjtracker.domain.model.TechnicsEnum
import java.time.LocalDate

@Entity(tableName = "trainings")
data class TrainingEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val date: LocalDate,
    val durationMinutes: Int,
    val partners: String,
    val technicItem: TechnicsEnum?
)