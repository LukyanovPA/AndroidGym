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
) : Reducer<CategoriesState, CategoriesAction, CategoriesEffect>(CategoriesState()) {
    private val searchQuery = MutableStateFlow(EMPTY_STRING)

    init {
        onSearch()
    }

    override suspend fun reduce(oldState: CategoriesState, action: CategoriesAction) {
        when (action) {
            is CategoriesAction.FetchCategories -> {
                saveState(oldState.copy(isLoading = true))
                onFetchCategories()
            }

            is CategoriesAction.FetchSubcategories -> {
                saveState(oldState.copy(isLoading = true))
                onFetchSubcategories(categoryId = action.categoryId)
            }

            is CategoriesAction.FetchQuestions -> {
                saveState(oldState.copy(isLoading = true))
                onFetchQuestions(subcategoryId = action.subcategoryId)
            }

            is CategoriesAction.Search -> {
                saveState(oldState.copy(isLoading = true, searchQuery = action.query))
                searchQuery.emit(action.query)
            }

            is CategoriesAction.Items -> saveState(oldState.copy(isLoading = false, items = action.items))
            is CategoriesAction.OnCategoryClick -> {}
            is CategoriesAction.OnSubcategoryClick -> {}
            is CategoriesAction.OnQuestionClick -> {}
        }
    }

    private fun onFetchCategories() = coroutineHelper.launchIO {
        getAllCategories()
            .collect { categories ->
                sendAction(CategoriesAction.Items(items = categories))
            }
    }

    private fun onFetchSubcategories(categoryId: Int) = coroutineHelper.launchIO {
        getSubcategoriesByCategoryId(categoryId)
            .collect { subcategories ->
                sendAction(CategoriesAction.Items(items = subcategories))
            }
    }

    private fun onFetchQuestions(subcategoryId: Int) = coroutineHelper.launchIO {
        getQuestionsBySubcategoryId(subcategoryId)
            .collect { questions ->
                sendAction(CategoriesAction.Items(items = questions))
            }
    }

    @OptIn(FlowPreview::class)
    private fun onSearch() = coroutineHelper.launchIO {
        searchQuery
            .debounce(300L)
            .flatMapMerge { query -> search(query) }
            .collect { result ->
                sendAction(CategoriesAction.Items(items = result))
            }
    }
}