package com.pavellukyanov.androidgym.common

import android.app.Application
import com.pavellukyanov.androidgym.app.BuildConfig
import com.pavellukyanov.androidgym.common.di.reducerModule
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig
import di.getModules
import di.storageModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()

//        this.applicationContext.deleteDatabase("AndroidGymDatabase.db")

        startKoin {
            androidContext(this@App)
            androidLogger()
            modules(getModules())
            modules(reducerModule)
            modules(storageModule)
        }
        initLogger()
        initAppMetrica()
    }

    private fun initLogger() {
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

    private fun initAppMetrica() {
        val config = YandexMetricaConfig.newConfigBuilder("685b17a8-a791-4e49-8033-b3669fae7272").build()
        YandexMetrica.activate(this, config)
        YandexMetrica.enableActivityAutoTracking(this)
    }
}