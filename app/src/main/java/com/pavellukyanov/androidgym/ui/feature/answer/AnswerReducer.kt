package com.pavellukyanov.androidgym.ui.feature.answer

import com.pavellukyanov.androidgym.base.Reducer
import entity.answer.Answer
import kotlinx.coroutines.flow.map
import useCase.answer.GetAnswer
import useCase.answer.UpdateFavouritesState
import useCase.answerfeedback.CreateAnswerFeedback

class AnswerReducer(
    private val getAnswer: GetAnswer,
    private val updateFavouritesState: UpdateFavouritesState,
    private val createAnswerFeedback: CreateAnswerFeedback
) : Reducer<AnswerState, AnswerAction, AnswerEffect>(AnswerState()) {

    override suspend fun reduce(oldState: AnswerState, action: AnswerAction) {
        when (action) {
            is AnswerAction.FetchAnswer -> fetchAnswer()
            is AnswerAction.Answer -> saveState(oldState.copy(answer = action.answer))
            is AnswerAction.GoBack -> sendEffect(AnswerEffect.GoBack)
            is AnswerAction.OnFavouritesClick -> onFavouritesUpdate(answer = oldState.answer!!.copy(isFavourites = action.state))
            is AnswerAction.OnCreateFeedbackClick -> onCreateAnswerFeedback(answerId = oldState.answer!!.id, comment = action.comment)
        }
    }

    private fun fetchAnswer() = launchIO {
        getAnswer()
            .map { answer -> AnswerAction.Answer(answer = answer) }
            .collect(::sendAction)
    }

    private fun onFavouritesUpdate(answer: Answer) = launchIO {
        updateFavouritesState(answer.id, answer.isFavourites)
    }

    private fun onCreateAnswerFeedback(answerId: Int, comment: String) = launchIO {
        //TODO добавить тост
        createAnswerFeedback(answerId, comment)
    }
}