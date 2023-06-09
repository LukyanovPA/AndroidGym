package com.pavellukyanov.androidgym.ui.feature.answer

import com.pavellukyanov.androidgym.base.Reducer
import com.pavellukyanov.androidgym.helper.AnalyticsClient
import com.pavellukyanov.androidgym.helper.AnalyticsClient.Events.COMMENT
import com.pavellukyanov.androidgym.helper.AnalyticsClient.Events.FAVOURITES
import com.pavellukyanov.androidgym.helper.AnalyticsClient.Events.QUESTION
import com.pavellukyanov.androidgym.helper.AnalyticsClient.Events.SEND_COMMENT
import com.pavellukyanov.androidgym.helper.AnalyticsClient.ScreenNames.ANSWER
import kotlinx.coroutines.flow.map
import useCase.answer.GetAnswer
import useCase.favourites.UpdateFavouritesState
import useCase.feedback.CreateAnswerFeedback

class AnswerReducer(
    private val getAnswer: GetAnswer,
    private val updateFavouritesState: UpdateFavouritesState,
    private val createAnswerFeedback: CreateAnswerFeedback
) : Reducer<AnswerState, AnswerAction, AnswerEffect>(AnswerState()) {

    override suspend fun reduce(oldState: AnswerState, action: AnswerAction) {
        when (action) {
            is AnswerAction.FetchAnswer -> {
                saveState(oldState.copy(answerId = action.questionId))
                fetchAnswer(questionId = action.questionId)
            }

            is AnswerAction.SetAnswer -> {
                saveState(oldState.copy(answer = action.answer, isLoading = false))
                AnalyticsClient.trackEvent(screen = ANSWER, event = QUESTION + action.answer.question)
            }

            is AnswerAction.OnCommentLinkClick -> AnalyticsClient.trackEvent(screen = ANSWER, event = COMMENT)
            is AnswerAction.GoBack -> sendEffect(AnswerEffect.GoBack)
            is AnswerAction.OnFavouritesClick -> {
                onFavouritesUpdate(questionId = action.questionId)
                AnalyticsClient.trackEvent(screen = ANSWER, event = FAVOURITES)
            }

            is AnswerAction.OnCreateFeedbackClick -> {
                onCreateAnswerFeedback(answerId = oldState.answer!!.id, comment = action.comment)
                AnalyticsClient.trackEvent(screen = ANSWER, event = SEND_COMMENT)
            }
        }
    }

    private fun fetchAnswer(questionId: Int) = launchIO {
        getAnswer(questionId)
            .map { answer -> AnswerAction.SetAnswer(answer = answer) }
            .collect(::sendAction)
    }

    private fun onFavouritesUpdate(questionId: Int) = launchIO {
        updateFavouritesState(questionId)
    }

    private fun onCreateAnswerFeedback(answerId: Int, comment: String) = launchIO {
        createAnswerFeedback(answerId, comment).also {
            sendEffect(AnswerEffect.ShowSendCommentCompleteNotify)
        }
    }
}