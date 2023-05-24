package com.pavellukyanov.androidgym.common.di

import com.pavellukyanov.androidgym.ui.feature.answer.AnswerReducer
import com.pavellukyanov.androidgym.ui.feature.main.MainReducer
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val reducerModule = module {
    viewModel { MainReducer(getAllCategories = get(), sendQuestionId = get()) }
    viewModel { AnswerReducer(getAnswer = get()) }
}