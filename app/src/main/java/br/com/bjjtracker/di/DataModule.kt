package br.com.bjjtracker.di

import androidx.room.Room
import br.com.bjjtracker.data.local.BJJTrackerDatabase
import br.com.bjjtracker.data.repository.TrainingRepositoryImpl
import br.com.bjjtracker.domain.repository.TrainingRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
    single {
        Room.databaseBuilder(androidContext(), BJJTrackerDatabase::class.java, "bjj_tracker.db")
            .fallbackToDestructiveMigration(dropAllTables = true)
            .build()
    }
    single { get<BJJTrackerDatabase>().trainingDao() }

    single<TrainingRepository> { TrainingRepositoryImpl(get()) }
}
