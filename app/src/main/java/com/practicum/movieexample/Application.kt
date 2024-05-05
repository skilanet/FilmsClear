package com.practicum.movieexample

import android.app.Application
import com.practicum.movieexample.di.dataModule
import com.practicum.movieexample.di.domainModule
import com.practicum.movieexample.di.presenterModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Application: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@Application)
            modules(dataModule, domainModule, presenterModule)
        }
    }
}