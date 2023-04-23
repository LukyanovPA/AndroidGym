package di

import dataSources.local.LocalQuestions
import dataSources.local.LocalQuestionsDataSource
import dataSources.network.NetworkQuestions
import dataSources.network.NetworkQuestionsDataSource
import org.koin.dsl.module

internal val dataSourceModule = module {
    single<LocalQuestions> { LocalQuestionsDataSource(get()) }
    single<NetworkQuestions> { NetworkQuestionsDataSource(get()) }
}