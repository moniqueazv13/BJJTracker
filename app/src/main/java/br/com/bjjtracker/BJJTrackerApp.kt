package br.com.bjjtracker

import android.app.Application
import br.com.bjjtracker.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BJJTrackerApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BJJTrackerApp)
            modules(appModule)
        }
    }
}