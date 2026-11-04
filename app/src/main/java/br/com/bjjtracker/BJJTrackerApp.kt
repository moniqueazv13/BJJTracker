package br.com.bjjtracker

import android.app.Application
import br.com.bjjtracker.di.dataModule
import br.com.bjjtracker.di.domainModule
import br.com.bjjtracker.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BJJTrackerApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BJJTrackerApp)
            modules(dataModule, domainModule, presentationModule)
        }
    }
}