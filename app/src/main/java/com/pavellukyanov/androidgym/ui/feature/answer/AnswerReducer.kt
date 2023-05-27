package com.pavellukyanov.androidgym.ui.feature.answer

import com.pavellukyanov.androidgym.base.Reducer
import entity.answer.Answer
import kotlinx.coroutines.flow.map
import useCase.answer.GetAnswer
import useCase.answer.UpdateFavouritesState

class AnswerReducer(
    private val getAnswer: GetAnswer,
    private val updateFavouritesState: UpdateFavouritesState
) : Reducer<AnswerState, AnswerAction, AnswerEffect>(AnswerState()) {

    override suspend fun reduce(oldState: AnswerState, action: AnswerAction) {
        when (action) {
            is AnswerAction.FetchAnswer -> fetchAnswer()
            is AnswerAction.Answer -> saveState(oldState.copy(answer = action.answer))
            is AnswerAction.GoBack -> sendEffect(AnswerEffect.GoBack)
            is AnswerAction.OnFavouritesClick -> onFavouritesUpdate(answer = oldState.answer!!.copy(isFavourites = action.state))
        }
    }

    private fun fetchAnswer() = launchIO {
        getAnswer()
            .map { AnswerAction.Answer(it) }
            .collect(::sendAction)
    }

    private fun onFavouritesUpdate(answer: Answer) = launchIO {
        updateFavouritesState(answer.id, answer.isFavourites)
    }
}