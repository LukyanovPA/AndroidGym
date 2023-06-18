package com.pavellukyanov.androidgym.common.di

import com.pavellukyanov.androidgym.ui.feature.answer.AnswerReducer
import com.pavellukyanov.androidgym.ui.feature.category.CategoryReducer
import com.pavellukyanov.androidgym.ui.feature.favourites.FavouritesReducer
import com.pavellukyanov.androidgym.ui.feature.main.MainReducer
import com.pavellukyanov.androidgym.ui.feature.subcategory.SubcategoryReducer
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val reducerModule = module {
    viewModel { MainReducer(globalSearch = get()) }
    viewModel { AnswerReducer(getAnswer = get(), updateFavouritesState = get(), createAnswerFeedback = get()) }
    viewModel { FavouritesReducer(getAllFavouritesAnswers = get(), updateFavouritesState = get()) }
    viewModel { CategoryReducer(getSubcategories = get()) }
    viewModel { SubcategoryReducer(getQuestions = get()) }
}