package com.pavellukyanov.androidgym.common.di

import com.pavellukyanov.androidgym.ui.feature.categories.CategoriesReducer
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val reducerModule = module {
    viewModel { CategoriesReducer(get()) }
}