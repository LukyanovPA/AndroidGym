package com.pavellukyanov.androidgym.ui.feature.categories

import com.pavellukyanov.androidgym.base.Reducer
import useCase.questions.GetAllCategories

class CategoriesReducer(
    private val getAllCategories: GetAllCategories
) : Reducer<CategoriesState, CategoriesAction, CategoriesEffect>(CategoriesState()) {

    override suspend fun reduce(oldState: CategoriesState, action: CategoriesAction) {
        when (action) {
            is CategoriesAction.StartFetch -> {
                saveState(oldState.copy(isLoading = true))
                fetchCategories()
            }

            is CategoriesAction.Categories -> saveState(oldState.copy(isLoading = false, categories = action.categories))

            is CategoriesAction.GoToCategory -> {}
        }
    }

    private fun fetchCategories() = launchIO {
        getAllCategories()
            .collect { categories ->
                sendAction(CategoriesAction.Categories(categories = categories))
            }
    }
}