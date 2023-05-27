package di

import dataSources.local.LocalCache
import dataSources.local.LocalCacheImpl
import dataSources.local.LocalQuestions
import dataSources.local.LocalQuestionsDataSource
import dataSources.network.NetworkQuestions
import dataSources.network.NetworkQuestionsDataSource
import dataSources.network.NetworkTimestamp
import dataSources.network.NetworkTimestampImpl
import org.koin.dsl.module

internal val dataSourceModule = module {
    //Local
    single<LocalQuestions> { LocalQuestionsDataSource(db = get()) }
    single<LocalCache> { LocalCacheImpl(dao = get()) }

    //Network
    single<NetworkQuestions> { NetworkQuestionsDataSource(httpClient = get()) }
    single<NetworkTimestamp> { NetworkTimestampImpl(httpClient = get()) }
}