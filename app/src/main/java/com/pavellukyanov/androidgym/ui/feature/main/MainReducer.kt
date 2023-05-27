package com.pavellukyanov.androidgym.ui.feature.main

import Constants.EMPTY_STRING
import com.pavellukyanov.androidgym.base.Reducer
import entity.questions.MainItems
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.map
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
            is MainAction.FetchMain -> {
                saveState(oldState.copy(items = listOf(MainItems.Loading)))
                fetchCategories(isFirstLoad = true)
            }

            is MainAction.Search -> {
                searchQuery.emit(action.query)
                saveState(oldState.copy(categoriesVisibility = false, searchQuery = searchQuery.value))
            }

            is MainAction.ClearSearch -> {
                saveState(oldState.copy(categoriesVisibility = true, items = listOf(MainItems.Loading), searchQuery = EMPTY_STRING))
                searchQuery.emit(EMPTY_STRING)
                fetchCategories(isFirstLoad = false)
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
                val oldExpendState = oldState.expendMap[action.name] ?: false
                val newMap = oldState.expendMap
                newMap[action.name] = !oldExpendState

                if (action.isCategory) {
                    val newCategories = oldState.categories.map { it.copy(isExpand = it.name == action.name) }
                    sendAction(MainAction.Categories(categories = newCategories, isFirstLoad = true))
                } else {
                    saveState(oldState.copy(expendMap = newMap))
                }
            }

            is MainAction.AddQuestion -> {}
            is MainAction.OpenMenu -> {}
        }
    }

    private fun fetchCategories(isFirstLoad: Boolean) = launchIO {
        getCategories()
            .map { MainAction.Categories(categories = it, isFirstLoad = isFirstLoad) }
            .collect(::sendAction)
    }

    @OptIn(FlowPreview::class)
    private fun onSearch() = launchIO {
        searchQuery
            .debounce(300.milliseconds)
            .flatMapMerge { query -> search(query) }
            .map { MainAction.Items(items = it) }
            .collect(::sendAction)
    }

    private fun sendQuestion(questionId: Int) = launchCPU {
        sendQuestionId(questionId).also {
            sendEffect(MainEffect.GoToAnswer)
        }
    }
}