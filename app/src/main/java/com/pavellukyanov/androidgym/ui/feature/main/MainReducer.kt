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
import useCase.questions.GetAllCategories
import kotlin.time.Duration.Companion.milliseconds

class MainReducer(
    private val getAllCategories: GetAllCategories,
    private val sendQuestionId: SendQuestionId
) : Reducer<MainState, MainAction, MainEffect>(MainState()) {
    private val searchQuery = MutableStateFlow(EMPTY_STRING)

    override suspend fun reduce(oldState: MainState, action: MainAction) {
        when (action) {
            is MainAction.FetchMain -> {
                saveState(oldState.copy(items = listOf(MainItems.Loading)))
                onFetchCategories()
            }

            is MainAction.Search -> {
                searchQuery.emit(action.query)
                saveState(oldState.copy(searchQuery = searchQuery.value))
            }

            is MainAction.ClearSearch -> {
                saveState(oldState.copy(searchQuery = EMPTY_STRING, items = listOf(MainItems.Loading)))
                searchQuery.emit(EMPTY_STRING)
            }

            is MainAction.Items -> saveState(oldState.copy(items = action.items))

            is MainAction.OnQuestionClick -> sendQuestion(questionId = action.questionId)

            is MainAction.OnExpandClick -> {
                val oldExpendState = oldState.expendMap[action.name] ?: false
                val newMap = oldState.expendMap
                newMap[action.name] = !oldExpendState
                saveState(oldState.copy(expendMap = newMap))
            }
        }
    }

    @OptIn(FlowPreview::class)
    private fun onFetchCategories() = launchIO {
        searchQuery
            .debounce(300.milliseconds)
            .flatMapMerge { query -> getAllCategories(query) }
            .map { MainAction.Items(items = it) }
            .collect(::sendAction)
    }

    private fun sendQuestion(questionId: Int) = launchCPU {
        sendQuestionId(questionId).also {
            sendEffect(MainEffect.GoToAnswer)
        }
    }
}