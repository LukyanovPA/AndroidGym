package di

import helper.NetworkMonitor
import helper.NetworkMonitorImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

internal val networkMonitorModule = module {
    single<NetworkMonitor> { NetworkMonitorImpl(androidContext()) }
}