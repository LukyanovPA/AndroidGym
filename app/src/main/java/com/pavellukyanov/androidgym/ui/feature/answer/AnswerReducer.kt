package com.pavellukyanov.androidgym.ui.feature.answer

import com.pavellukyanov.androidgym.base.Reducer
import com.pavellukyanov.androidgym.helper.AnalyticsClient
import com.pavellukyanov.androidgym.helper.AnalyticsClient.Events.COMMENT
import com.pavellukyanov.androidgym.helper.AnalyticsClient.Events.FAVOURITES
import com.pavellukyanov.androidgym.helper.AnalyticsClient.Events.QUESTION
import com.pavellukyanov.androidgym.helper.AnalyticsClient.Events.SEND_COMMENT
import com.pavellukyanov.androidgym.helper.AnalyticsClient.ScreenNames.ANSWER
import entity.models.Answer
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
            is AnswerAction.FetchAnswer -> fetchAnswer()

            is AnswerAction.Answer -> {
                launchCPU { AnalyticsClient.trackEvent(screen = ANSWER, event = QUESTION + action.answer.question) }
                saveState(oldState.copy(answer = action.answer))
            }

            is AnswerAction.OnCommentLinkClick -> AnalyticsClient.trackEvent(screen = ANSWER, event = COMMENT)
            is AnswerAction.GoBack -> sendEffect(AnswerEffect.GoBack)
            is AnswerAction.OnFavouritesClick -> {
                launchCPU { AnalyticsClient.trackEvent(screen = ANSWER, event = FAVOURITES) }
                onFavouritesUpdate(answer = oldState.answer!!.copy(isFavourites = action.state))
            }

            is AnswerAction.OnCreateFeedbackClick -> {
                launchCPU { AnalyticsClient.trackEvent(screen = ANSWER, event = SEND_COMMENT) }
                onCreateAnswerFeedback(answerId = oldState.answer!!.id, comment = action.comment)
            }
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
        createAnswerFeedback(answerId, comment).also {
            sendEffect(AnswerEffect.ShowSendCommentCompleteNotify)
        }
    }
}