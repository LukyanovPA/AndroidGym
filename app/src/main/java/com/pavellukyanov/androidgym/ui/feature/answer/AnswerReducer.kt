package com.pavellukyanov.androidgym.ui.feature.answer

import com.pavellukyanov.androidgym.base.Reducer
import useCase.answer.GetAnswer

class AnswerReducer(
    private val getAnswer: GetAnswer
) : Reducer<AnswerState, AnswerAction, AnsweEffect>(AnswerState()) {

    override suspend fun reduce(oldState: AnswerState, action: AnswerAction) {
        when (action) {
            is AnswerAction.FetchAnswer -> {
                saveState(oldState.copy(isLoading = true))
                fetchAnswer()
            }

            is AnswerAction.Answer -> saveState(oldState.copy(isLoading = false, answer = action.answer))
            is AnswerAction.GoBack -> sendEffect(AnsweEffect.GoBack)
        }
    }

    private fun fetchAnswer() = scope.launchIO {
        getAnswer().collect { sendAction(AnswerAction.Answer(it)) }
    }
}