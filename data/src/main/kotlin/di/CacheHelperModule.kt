package di

import base.CacheHelper
import org.koin.dsl.module

internal val cacheHelperModule = module {
    single { CacheHelper(get(), get(), get(), get(), get()) }
}