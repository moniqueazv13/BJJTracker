package br.com.bjjtracker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [TrainingEntity::class],
    version = 2,
    exportSchema = false,
)
@TypeConverters(Converters::class)
abstract class BJJTrackerDatabase : RoomDatabase() {
    abstract fun trainingDao(): TrainingDao
}