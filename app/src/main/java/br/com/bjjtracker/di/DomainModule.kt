package br.com.bjjtracker.di

import br.com.bjjtracker.domain.usecase.GetTrainingsUseCase
import br.com.bjjtracker.domain.usecase.RegisterTrainingUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { RegisterTrainingUseCase(get()) }
    factory { GetTrainingsUseCase(get()) }
}
