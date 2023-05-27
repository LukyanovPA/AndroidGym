package di

import base.CacheHelper
import org.koin.dsl.module

internal val cacheHelperModule = module {
    single {
        CacheHelper(
            networkMonitor = get(),
            localCache = get(),
            networkTimestamp = get(),
            localQuestions = get(),
            networkQuestions = get()
        )
    }
}