package di

import dataSources.local.LocalCache
import dataSources.local.LocalCacheImpl
import dataSources.local.LocalQuestions
import dataSources.local.LocalQuestionsDataSource
import dataSources.network.NetworkTimestamp
import dataSources.network.NetworkTimestampImpl
import dataSources.network.NetworkQuestions
import dataSources.network.NetworkQuestionsDataSource
import org.koin.dsl.module

internal val dataSourceModule = module {
    //Local
    single<LocalQuestions> { LocalQuestionsDataSource(get()) }
    single<LocalCache> { LocalCacheImpl(get()) }

    //Network
    single<NetworkQuestions> { NetworkQuestionsDataSource(get()) }
    single<NetworkTimestamp> { NetworkTimestampImpl(get()) }
}