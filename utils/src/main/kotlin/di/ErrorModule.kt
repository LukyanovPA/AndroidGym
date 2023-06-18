package di

import SecretValues
import error.ErrorStorage
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val storageModule = module {
    singleOf(::ErrorStorage)
    single { SecretValues(context = androidContext()) }
}