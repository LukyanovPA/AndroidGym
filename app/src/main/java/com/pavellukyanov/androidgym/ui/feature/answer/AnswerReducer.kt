package com.pavellukyanov.androidgym.ui.feature.answer

import com.pavellukyanov.androidgym.base.Reducer
import kotlinx.coroutines.flow.map
import useCase.answer.GetAnswer

class AnswerReducer(
    private val getAnswer: GetAnswer
) : Reducer<AnswerState, AnswerAction, AnsweEffect>(AnswerState()) {

    override suspend fun reduce(oldState: AnswerState, action: AnswerAction) {
        when (action) {
            is AnswerAction.FetchAnswer -> fetchAnswer()
            is AnswerAction.Answer -> saveState(oldState.copy(answer = action.answer))
            is AnswerAction.GoBack -> sendEffect(AnsweEffect.GoBack)
        }
    }

    private fun fetchAnswer() = launchIO {
        getAnswer()
            .map { AnswerAction.Answer(it) }
            .collect(::sendAction)
    }
}