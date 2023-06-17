package com.pavellukyanov.androidgym.ui.feature.answer

import Constants.INT_MINUS_ONE
import com.pavellukyanov.androidgym.base.Action
import com.pavellukyanov.androidgym.base.Effect
import com.pavellukyanov.androidgym.base.State
import entity.models.Answer

data class AnswerState(
    val answer: Answer? = null,
    val answerId: Int = INT_MINUS_ONE,
    val isLoading: Boolean = true
) : State()

sealed class AnswerAction : Action() {
    data class FetchAnswer(val questionId: Int) : AnswerAction()

    data class SetAnswer(val answer: Answer) : AnswerAction()

    object GoBack : AnswerAction()

    data class OnFavouritesClick(val state: Boolean) : AnswerAction()

    data class OnCreateFeedbackClick(val comment: String) : AnswerAction()

    object OnCommentLinkClick : AnswerAction()
}

sealed class AnswerEffect : Effect() {
    object GoBack : AnswerEffect()

    object ShowSendCommentCompleteNotify : AnswerEffect()
}