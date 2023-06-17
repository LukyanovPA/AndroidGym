package di

import helper.IdStorage
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun getModules() = mutableListOf<Module>().apply {
    addAll(dataModule())
    addAll(domainModule())
}

internal fun domainModule() = listOf(
    storageModule,
    useCaseModule
)

internal val storageModule = module {
    singleOf(::IdStorage)
}