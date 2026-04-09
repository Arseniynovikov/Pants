package com.example.pants.main

import android.app.Application
import com.example.pants.service.dataModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class PantsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@PantsApplication)
            modules(viewModelsModule, dataModule)
        }
    }
}
