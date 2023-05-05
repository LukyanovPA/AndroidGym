package com.pavellukyanov.androidgym.ui.feature.main

import Constants.EMPTY_STRING
import com.pavellukyanov.androidgym.base.Reducer
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapMerge
import useCase.questions.GetAllCategories
import useCase.questions.GetQuestionsBySubcategoryId
import useCase.questions.GetSubcategoriesByCategoryId
import useCase.questions.Search

class MainReducer(
    private val getAllCategories: GetAllCategories,
    private val getSubcategoriesByCategoryId: GetSubcategoriesByCategoryId,
    private val getQuestionsBySubcategoryId: GetQuestionsBySubcategoryId,
    private val search: Search
) : Reducer<MainState, MainAction, MainEffect>(MainState()) {
    private val searchQuery = MutableStateFlow(EMPTY_STRING)

    override suspend fun reduce(oldState: MainState, action: MainAction) {
        when (action) {
            is MainAction.FetchMain -> {
                saveState(oldState.copy(isLoading = true))
                onFetchCategories()
            }

            is MainAction.FetchSubcategories -> {
                saveState(oldState.copy(isLoading = true))
                onFetchSubcategories(categoryId = action.categoryId)
            }

            is MainAction.FetchQuestions -> {
                saveState(oldState.copy(isLoading = true))
                onFetchQuestions(subcategoryId = action.subcategoryId)
            }

            is MainAction.Search -> {
                saveState(oldState.copy(isLoading = true))
                searchQuery.emit(action.query)
                onSearch()
            }

            is MainAction.Items -> saveState(oldState.copy(isLoading = false, items = action.items))
            is MainAction.OnCategoryClick -> {}
            is MainAction.OnSubcategoryClick -> {}
            is MainAction.OnQuestionClick -> {}
        }
    }

    private fun onFetchCategories() = coroutineHelper.launchIO {
        getAllCategories()
            .collect { categories ->
                sendAction(MainAction.Items(items = categories))
            }
    }

    private fun onFetchSubcategories(categoryId: Int) = coroutineHelper.launchIO {
        getSubcategoriesByCategoryId(categoryId)
            .collect { subcategories ->
                sendAction(MainAction.Items(items = subcategories))
            }
    }

    private fun onFetchQuestions(subcategoryId: Int) = coroutineHelper.launchIO {
        getQuestionsBySubcategoryId(subcategoryId)
            .collect { questions ->
                sendAction(MainAction.Items(items = questions))
            }
    }

    @OptIn(FlowPreview::class)
    private fun onSearch() = coroutineHelper.launchIO {
        searchQuery
            .debounce(300L)
            .flatMapMerge { query -> search(query) }
            .collect { result ->
                sendAction(MainAction.Items(items = result))
            }
    }
}