package com.pavellukyanov.androidgym.ui.feature.main

import Constants.EMPTY_STRING
import com.pavellukyanov.androidgym.base.Reducer
import com.pavellukyanov.androidgym.helper.AnalyticsClient
import com.pavellukyanov.androidgym.helper.AnalyticsClient.Events.CLICK_CATEGORY
import com.pavellukyanov.androidgym.helper.AnalyticsClient.Events.CLICK_FAVOURITES
import com.pavellukyanov.androidgym.helper.AnalyticsClient.Events.CLICK_MENU
import com.pavellukyanov.androidgym.helper.AnalyticsClient.Events.CLICK_QUESTION
import com.pavellukyanov.androidgym.helper.AnalyticsClient.Events.CLICK_SUBCATEGORY
import com.pavellukyanov.androidgym.helper.AnalyticsClient.Events.SEARCH
import com.pavellukyanov.androidgym.helper.AnalyticsClient.ScreenNames.MAIN
import com.pavellukyanov.androidgym.helper.AnalyticsClient.ScreenNames.MAIN_MENU
import entity.main.MainItems
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import useCase.answer.SendId
import useCase.search.GlobalSearch
import kotlin.time.Duration.Companion.milliseconds

class MainReducer(
    private val globalSearch: GlobalSearch,
    private val sendId: SendId
) : Reducer<MainState, MainAction, MainEffect>(MainState()) {
    private val searchQuery = MutableStateFlow(EMPTY_STRING)

    init {
        onSearch()
    }

    override suspend fun reduce(oldState: MainState, action: MainAction) {
        when (action) {
            is MainAction.Search -> {
                searchQuery.emit(action.query)
                saveState(oldState.copy(searchQuery = searchQuery.value))
                AnalyticsClient.trackEvent(MAIN, SEARCH)
            }

            is MainAction.ClearSearch -> {
                searchQuery.emit(EMPTY_STRING)
                saveState(oldState.copy(items = listOf(MainItems.Loading), searchQuery = EMPTY_STRING))
            }

            is MainAction.Items -> saveState(oldState.copy(items = action.items))

            is MainAction.OnQuestionClick -> {
                onSendId(id = action.question.id, action = action)
                AnalyticsClient.trackEvent(MAIN, CLICK_QUESTION + action.question.question)
            }

            is MainAction.OnSubcategoryClick -> {
                onSendId(id = action.subcategory.id, action = action)
                AnalyticsClient.trackEvent(MAIN, CLICK_SUBCATEGORY + action.subcategory.name)
            }

            is MainAction.OnCategoryClick -> {
                onSendId(id = action.category.id, action = action)
                AnalyticsClient.trackEvent(MAIN, CLICK_CATEGORY + action.category.name)
            }

            is MainAction.OnMenuClick -> {
                sendEffect(MainEffect.OnMenuClicked)
                AnalyticsClient.trackEvent(MAIN, CLICK_MENU)
            }

            is MainAction.OnFavouriteClick -> {
                sendEffect(MainEffect.GoToFavourites)
                AnalyticsClient.trackEvent(MAIN_MENU, CLICK_FAVOURITES)
            }
        }
    }

    @OptIn(FlowPreview::class)
    private fun onSearch() = launchIO {
        searchQuery
            .onStart { listOf(MainItems.Loading) }
            .debounce(300.milliseconds)
            .flatMapMerge { query -> globalSearch(query) }
            .map { items -> MainAction.Items(items = items) }
            .collect(::sendAction)
    }

    private fun onSendId(id: Int, action: MainAction) = launchCPU {
        sendId(id).also {
            sendEffect(
                when (action) {
                    is MainAction.OnQuestionClick -> MainEffect.GoToAnswer
                    is MainAction.OnCategoryClick -> MainEffect.GoToCategory(categoryName = action.category.name)
                    is MainAction.OnSubcategoryClick -> MainEffect.GoToSubcategory(subcategoryName = action.subcategory.name)
                    else -> return@also
                }
            )
        }
    }
}