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
import entity.main.MainItems
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.map
import useCase.category.GetSubcategories
import kotlin.time.Duration.Companion.milliseconds

class CategoryReducer(
    private val getSubcategories: GetSubcategories
) : Reducer<CategoryState, CategoryAction, CategoryEffect>(CategoryState()) {
    private val searchQuery = MutableStateFlow(EMPTY_STRING)

    override suspend fun reduce(oldState: CategoryState, action: CategoryAction) {
        when (action) {
            is CategoryAction.SetCategoryValues -> {
                saveState(
                    oldState.copy(
                        subcategories = listOf(MainItems.Loading),
                        categoryName = action.categoryName,
                        categoryId = action.categoryId
                    )
                )
                onSearch(categoryId = action.categoryId)
            }

            is CategoryAction.Search -> {
                saveState(oldState.copy(searchQuery = searchQuery.value))
                searchQuery.emit(action.query)
                AnalyticsClient.trackEvent(CATEGORY, SEARCH)
            }

            is CategoryAction.ClearSearch -> {
                saveState(oldState.copy(subcategories = listOf(MainItems.Loading), searchQuery = EMPTY_STRING))
                searchQuery.emit(EMPTY_STRING)
            }

            is CategoryAction.SetItems -> {
                saveState(oldState.copy(subcategories = action.items))
            }

            is CategoryAction.OnSubcategoryClick -> {
                sendEffect(CategoryEffect.GoToSubcategory(subcategory = action.subcategory))
                AnalyticsClient.trackEvent(CATEGORY, CLICK_SUBCATEGORY + action.subcategory.name)
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
            is CategoryAction.OnMainClick -> sendEffect(CategoryEffect.GoToMain)
        }
    }

    @OptIn(FlowPreview::class)
    private fun onSearch(categoryId: Int) = launchIO {
        searchQuery
            .debounce(300.milliseconds)
            .flatMapMerge { query -> getSubcategories(query, categoryId) }
            .map { items -> CategoryAction.SetItems(items = items) }
            .collect(::sendAction)
    }
}