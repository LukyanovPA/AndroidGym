package com.pavellukyanov.androidgym.ui.feature.main

import Constants.EMPTY_STRING
import com.pavellukyanov.androidgym.base.Reducer
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapMerge
import useCase.questions.GetAllCategories
import useCase.questions.Search
import kotlin.time.Duration.Companion.milliseconds

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

    private fun onFetchCategories() = scope.launchIO {
        getAllCategories()
            .collect { categories ->
                sendAction(MainAction.Items(items = categories))
            }
    }

    @OptIn(FlowPreview::class)
    private fun onSearch() = scope.launchIO {
        searchQuery
            .debounce(300.milliseconds)
            .flatMapMerge { query -> search(query) }
            .collect { result ->
                sendAction(MainAction.Items(items = result))
            }
    }
}