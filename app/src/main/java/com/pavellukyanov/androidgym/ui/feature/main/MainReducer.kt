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
                onFetchCategories()
            }

            is MainAction.Items -> saveState(oldState.copy(items = action.items))

            is MainAction.OnQuestionClick -> sendQuestion(questionId = action.questionId)

            is MainAction.OnExpandClick -> {
                val oldExpendState = oldState.expendMap[action.id]
                val oldMap = oldState.expendMap
                oldMap[action.id] = if (oldExpendState != null) !oldExpendState else false
                saveState(oldState.copy(expendMap = oldMap))
            }
        }
    }

    @OptIn(FlowPreview::class)
    private fun onFetchCategories() = launchIO {
        searchQuery
            .debounce(300.milliseconds)
            .flatMapMerge { query -> getAllCategories(query) }
            .collect { sendAction(MainAction.Items(items = it)) }
    }

    private fun sendQuestion(questionId: Int) = launchCPU {
        sendQuestionId(questionId).also {
            sendEffect(MainEffect.GoToAnswer)
        }
    }
}