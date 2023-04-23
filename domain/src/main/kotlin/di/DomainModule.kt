package di

import org.koin.core.module.Module

fun getModules() = mutableListOf<Module>().apply {
    addAll(dataModule())
    addAll(domainModule())
}

internal fun domainModule() = listOf(
    useCaseModule
)