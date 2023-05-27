package di

import error.ErrorStorage
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val storageModule = module {
    singleOf(::ErrorStorage)
}