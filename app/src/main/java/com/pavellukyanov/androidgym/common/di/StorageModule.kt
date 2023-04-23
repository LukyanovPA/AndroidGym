package com.pavellukyanov.androidgym.common.di

import com.pavellukyanov.androidgym.base.ErrorStorage
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val storageModule = module {
    singleOf(::ErrorStorage)
}