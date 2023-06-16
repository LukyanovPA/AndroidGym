package com.pavellukyanov.androidgym.ui.feature.main

import Constants.EMPTY_STRING
import com.pavellukyanov.androidgym.base.Reducer
import com.pavellukyanov.androidgym.helper.AnalyticsClient
import com.pavellukyanov.androidgym.helper.AnalyticsClient.Events.CLICK_CATEGORY
import com.pavellukyanov.androidgym.helper.AnalyticsClient.Events.CLICK_FAVOURITES
import com.pavellukyanov.androidgym.helper.AnalyticsClient.Events.CLICK_MENU
import com.pavellukyanov.androidgym.helper.AnalyticsClient.Events.CLICK_SUBCATEGORY
import com.pavellukyanov.androidgym.helper.AnalyticsClient.Events.SEARCH
import com.pavellukyanov.androidgym.helper.AnalyticsClient.ScreenNames.MAIN
import com.pavellukyanov.androidgym.helper.AnalyticsClient.ScreenNames.MAIN_MENU
import entity.questions.MainItems
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import useCase.answer.SendQuestionId
import useCase.questions.GetCategories
import useCase.questions.Search
import kotlin.time.Duration.Companion.milliseconds

class MainReducer(
    private val getCategories: GetCategories,
    private val search: Search,
    private val sendQuestionId: SendQuestionId
) : Reducer<MainState, MainAction, MainEffect>(MainState()) {
    private val searchQuery = MutableStateFlow(EMPTY_STRING)

    init {
        onSearch()
    }

    override suspend fun reduce(oldState: MainState, action: MainAction) {
        when (action) {
            is MainAction.FetchMain -> fetchCategories()
            is MainAction.Search -> {
                launchCPU { AnalyticsClient.trackEvent(MAIN, SEARCH) }
                searchQuery.emit(action.query)
                saveState(oldState.copy(categoriesVisibility = false, searchQuery = searchQuery.value))
            }

            is MainAction.ClearSearch -> {
                saveState(oldState.copy(categoriesVisibility = true, items = listOf(MainItems.Loading), searchQuery = EMPTY_STRING))
                searchQuery.emit(EMPTY_STRING)
                fetchCategories()
            }

            is MainAction.Items -> {
                val newItems = if (searchQuery.value.isEmpty()) {
                    action.items
                        .filterIsInstance<MainItems.SubcategoryItem>()
                        .filter { item ->
                            item.subcategory.categoryName.contains(oldState.categories.find { it.isExpand }?.name.orEmpty(), true)
                        }
                } else {
                    action.items
                }
                saveState(oldState.copy(items = newItems))
            }

            is MainAction.Categories -> {
                val newItems = action.categories.find { it.isExpand }
                    ?.let { category ->
                        if (category.subcategories.isNotEmpty()) MainItems.SubcategoryItem.map(category.subcategories) else listOf(MainItems.NotFoundItem)
                    }
                saveState(oldState.copy(categories = action.categories, items = (newItems as List<MainItems>)))
            }

            is MainAction.OnQuestionClick -> sendQuestion(questionId = action.questionId)

            is MainAction.OnExpandClick -> {
                if (action.isCategory) {
                    launchCPU { AnalyticsClient.trackEvent(MAIN, CLICK_CATEGORY + action.name) }
                    val newCategories = oldState.categories.map { it.copy(isExpand = it.name == action.name) }
                    sendAction(MainAction.Categories(categories = newCategories))
                } else {
                    launchCPU { AnalyticsClient.trackEvent(MAIN, CLICK_SUBCATEGORY + action.name) }
                    val oldExpendState = oldState.expendMap[action.name] ?: false
                    val newMap = oldState.expendMap
                    newMap[action.name] = !oldExpendState
                    saveState(oldState.copy(expendMap = newMap))
                }
            }

            is MainAction.OnMenuClick -> {
                AnalyticsClient.trackEvent(MAIN, CLICK_MENU)
                sendEffect(MainEffect.OnMenuClicked)
            }

            is MainAction.OnFavouriteClick -> {
                AnalyticsClient.trackEvent(MAIN_MENU, CLICK_FAVOURITES)
                sendEffect(MainEffect.GoToFavourites)
            }
        }
    }

    private fun fetchCategories() = launchIO {
        getCategories()
            .map { categories -> MainAction.Categories(categories = categories) }
            .collect(::sendAction)
    }

    @OptIn(FlowPreview::class)
    private fun onSearch() = launchIO {
        searchQuery
            .onStart { listOf(MainItems.Loading) }
            .debounce(300.milliseconds)
            .flatMapMerge { query -> search(query) }
            .map { items -> MainAction.Items(items = items) }
            .collect(::sendAction)
    }

    private fun sendQuestion(questionId: Int) = launchCPU {
        sendQuestionId(questionId).also {
            sendEffect(MainEffect.GoToAnswer)
        }
    }
}