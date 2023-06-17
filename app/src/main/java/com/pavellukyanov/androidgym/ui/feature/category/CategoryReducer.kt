package com.pavellukyanov.androidgym.ui.feature.category

import Constants.EMPTY_STRING
import com.pavellukyanov.androidgym.base.Reducer
import com.pavellukyanov.androidgym.helper.AnalyticsClient
import com.pavellukyanov.androidgym.helper.AnalyticsClient.Events.CLICK_FAVOURITES
import com.pavellukyanov.androidgym.helper.AnalyticsClient.Events.CLICK_MENU
import com.pavellukyanov.androidgym.helper.AnalyticsClient.Events.CLICK_SUBCATEGORY
import com.pavellukyanov.androidgym.helper.AnalyticsClient.Events.SEARCH
import com.pavellukyanov.androidgym.helper.AnalyticsClient.ScreenNames.CATEGORY
import com.pavellukyanov.androidgym.helper.AnalyticsClient.ScreenNames.MAIN_MENU
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.map
import useCase.answer.SendId
import useCase.category.GetSubcategories
import kotlin.time.Duration.Companion.milliseconds

class CategoryReducer(
    private val getSubcategories: GetSubcategories,
    private val sendId: SendId
) : Reducer<CategoryState, CategoryAction, CategoryEffect>(CategoryState()) {
    private val searchQuery = MutableStateFlow(EMPTY_STRING)

    init {
        onSearch()
    }

    override suspend fun reduce(oldState: CategoryState, action: CategoryAction) {
        when (action) {
            is CategoryAction.SetCategoryName -> {
                saveState(oldState.copy(categoryName = action.categoryName))
            }

            is CategoryAction.Search -> {
                saveState(oldState.copy(searchQuery = searchQuery.value))
                searchQuery.emit(action.query)
                launchCPU { AnalyticsClient.trackEvent(CATEGORY, SEARCH) }
            }

            is CategoryAction.ClearSearch -> {
                saveState(oldState.copy(subcategories = listOf(), searchQuery = EMPTY_STRING))
                searchQuery.emit(EMPTY_STRING)
            }

            is CategoryAction.SetItems -> {
                saveState(oldState.copy(subcategories = action.items))
            }

            is CategoryAction.OnSubcategoryClick -> {
                onSendId(id = action.subcategory.id)
                launchCPU { AnalyticsClient.trackEvent(CATEGORY, CLICK_SUBCATEGORY + action.subcategory.name) }
            }

            is CategoryAction.OnMenuClick -> {
                AnalyticsClient.trackEvent(CATEGORY, CLICK_MENU)
                sendEffect(CategoryEffect.OnMenuClicked)
            }

            is CategoryAction.OnFavouriteClick -> {
                sendEffect(CategoryEffect.GoToFavourites)
                AnalyticsClient.trackEvent(MAIN_MENU, CLICK_FAVOURITES)
            }

            is CategoryAction.OnBackClick -> sendEffect(CategoryEffect.GoBack)
        }
    }

    @OptIn(FlowPreview::class)
    private fun onSearch() = launchIO {
        searchQuery
            .debounce(300.milliseconds)
            .flatMapMerge { query -> getSubcategories(query) }
            .map { items -> CategoryAction.SetItems(items = items) }
            .collect(::sendAction)
    }

    private fun onSendId(id: Int) = launchCPU {
        sendId(id).also {
            sendEffect(CategoryEffect.GoToSubcategory)
        }
    }
}