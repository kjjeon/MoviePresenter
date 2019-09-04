package com.kjjeon.moviepresenter

import android.app.Application
import com.kjjeon.moviepresenter.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            // declare Android context
            androidContext(this@App)
            // declare modules to use
            modules(
                listOf(
                    netModule,
                    presenterModule,
                    repositoryModule,
                    useCaseModule
                )
            )
        }
    }
}