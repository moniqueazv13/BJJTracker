package br.com.bjjtracker.di

import androidx.room.Room
import br.com.bjjtracker.data.local.BJJTrackerDatabase
import br.com.bjjtracker.data.repository.TrainingRepositoryImpl
import br.com.bjjtracker.domain.repository.TrainingRepository
import br.com.bjjtracker.domain.usecase.GetTrainingsUseCase
import br.com.bjjtracker.domain.usecase.RegisterTrainingUseCase
import br.com.bjjtracker.ui.home.HomeViewModel
import java.time.Clock
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<Clock> { Clock.systemDefaultZone() }

    single {
        Room.databaseBuilder(androidContext(), BJJTrackerDatabase::class.java, "bjj_tracker.db")
            .fallbackToDestructiveMigration(dropAllTables = true)
            .build()
    }
    single { get<BJJTrackerDatabase>().trainingDao() }

    single<TrainingRepository> { TrainingRepositoryImpl(get()) }

    factory { RegisterTrainingUseCase(get()) }
    factory { GetTrainingsUseCase(get()) }

    viewModel { HomeViewModel(androidApplication(), get(), get(), get()) }
}