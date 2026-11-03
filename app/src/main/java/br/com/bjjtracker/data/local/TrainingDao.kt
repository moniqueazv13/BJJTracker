package br.com.bjjtracker.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TrainingDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(training: TrainingEntity)

    @Query("SELECT * FROM trainings ORDER BY date DESC, id DESC")
    fun observeAll(): Flow<List<TrainingEntity>>
}