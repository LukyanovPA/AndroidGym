package com.pavellukyanov.androidgym.ui.feature.main

import Constants.EMPTY_STRING
import com.pavellukyanov.androidgym.base.Reducer
import entity.questions.MainItems
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapMerge
import useCase.answer.SendQuestionId
import useCase.questions.GetAllCategories
import useCase.questions.Search
import kotlin.time.Duration.Companion.milliseconds

class MainReducer(
    private val getAllCategories: GetAllCategories,
    private val search: Search,
    private val sendQuestionId: SendQuestionId
) : Reducer<MainState, MainAction, MainEffect>(MainState()) {
    private val searchQuery = MutableStateFlow(EMPTY_STRING)

    //TODO переделать поиск
    override suspend fun reduce(oldState: MainState, action: MainAction) {
        when (action) {
            is MainAction.FetchMain -> {
                saveState(oldState.copy(isLoading = true))
                onFetchCategories()
            }

            is MainAction.Search -> {
                searchQuery.emit(action.query)
                onSearch()
            }

            is MainAction.ClearSearch -> {
                saveState(oldState.copy(items = listOf()))
                searchQuery.emit(EMPTY_STRING)
                onFetchCategories()
            }

            is MainAction.Items -> saveState(oldState.copy(isLoading = false, items = action.items))
            is MainAction.OnQuestionClick -> sendQuestion(questionId = action.questionId)

            is MainAction.CategoryExpand -> {
                val newItems = oldState.items.toMutableList()
                val item = newItems.filterIsInstance<MainItems.CategoryItem>().find { it.category.id == action.category.id }!!
                newItems[newItems.indexOf(item)] = item.copy(category = action.category)

                saveState(oldState.copy(items = newItems))
            }

            is MainAction.SubcategoryExpand -> {
                val newItems = oldState.items.toMutableList()
                val item = newItems.filterIsInstance<MainItems.CategoryItem>().find { it.category.id == action.categoryId }!!
                val newList = item.category.subcategories.toMutableList()
                newList[newList.indexOf(newList.find { it.id == action.subcategory.id }!!)] = action.subcategory
                newItems[newItems.indexOf(item)] = item.copy(category = item.category.copy(subcategories = newList))

                saveState(oldState.copy(items = newItems))
            }

            is MainAction.SearchSubcategoryExpand -> {
                val newItems = oldState.items.toMutableList()
                val item = newItems.filterIsInstance<MainItems.SubcategoryItem>().find { it.subcategory.id == action.subcategory.id }!!
                newItems[newItems.indexOf(item)] = item.copy(subcategory = action.subcategory)

                saveState(oldState.copy(items = newItems))
            }
        }
    }

    private fun onFetchCategories() = launchIO {
        getAllCategories()
            .collect { categories ->
                sendAction(MainAction.Items(items = categories))
            }
    }

    @OptIn(FlowPreview::class)
    private fun onSearch() = launchIO {
        searchQuery
            .debounce(300.milliseconds)
            .flatMapMerge { query -> search(query) }
            .collect { result ->
                sendAction(MainAction.Items(items = result))
            }
    }

    private fun sendQuestion(questionId: Int) = launchCPU {
        sendQuestionId(questionId).also {
            sendEffect(MainEffect.GoToAnswer)
        }
    }
}