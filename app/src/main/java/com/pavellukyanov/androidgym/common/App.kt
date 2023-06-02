package com.pavellukyanov.androidgym.common

import Constants.METRICA_KEY
import SecretValues
import android.app.Application
import com.pavellukyanov.androidgym.app.BuildConfig
import com.pavellukyanov.androidgym.common.di.reducerModule
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig
import di.getModules
import di.storageModule
import org.koin.android.ext.android.getKoin
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
        val secret: SecretValues = getKoin().get()
        val config = YandexMetricaConfig.newConfigBuilder(secret.getValue(METRICA_KEY)).build()
        YandexMetrica.activate(this, config)
        YandexMetrica.enableActivityAutoTracking(this)
    }
}