package com.pavellukyanov.androidgym.common

import android.app.Application
import com.pavellukyanov.androidgym.BuildConfig
import com.pavellukyanov.androidgym.common.di.reducerModule
import com.pavellukyanov.androidgym.common.di.storageModule
import di.getModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            androidLogger()
            modules(getModules())
            modules(reducerModule)
            modules(storageModule)
        }
        initLogger()
    }

    private fun initLogger() {
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}