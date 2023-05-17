package com.pavellukyanov.androidgym.ui.feature.main

import Constants.EMPTY_STRING
import com.pavellukyanov.androidgym.base.Reducer
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapMerge
import useCase.questions.GetAllCategories
import useCase.questions.Search

class MainReducer(
    private val getAllCategories: GetAllCategories,
    private val search: Search
) : Reducer<MainState, MainAction, MainEffect>(MainState()) {
    private val searchQuery = MutableStateFlow(EMPTY_STRING)

    override suspend fun reduce(oldState: MainState, action: MainAction) {
        when (action) {
            is MainAction.FetchMain -> {
                saveState(oldState.copy(isLoading = true))
                onFetchCategories()
            }

            is MainAction.Search -> {
                saveState(oldState.copy(isLoading = true))
                searchQuery.emit(action.query)
                onSearch()
            }

            is MainAction.Items -> saveState(oldState.copy(isLoading = false, items = action.items))
            is MainAction.OnQuestionClick -> {}
        }
    }

    private fun onFetchCategories() = coroutineHelper.launchIO {
        getAllCategories()
            .collect { categories ->
                sendAction(MainAction.Items(items = categories))
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