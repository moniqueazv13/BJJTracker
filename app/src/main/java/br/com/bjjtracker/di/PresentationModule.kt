package br.com.bjjtracker.di

import br.com.bjjtracker.ui.home.HomeViewModel
import java.time.Clock
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    single<Clock> { Clock.systemDefaultZone() }

    viewModel { HomeViewModel(androidApplication(), get(), get(), get()) }
}
