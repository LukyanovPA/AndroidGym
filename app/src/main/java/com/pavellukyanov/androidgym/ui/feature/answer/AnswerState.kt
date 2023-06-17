package com.pavellukyanov.androidgym.ui.feature.answer

import com.pavellukyanov.androidgym.base.Action
import com.pavellukyanov.androidgym.base.Effect
import com.pavellukyanov.androidgym.base.State
import entity.models.Answer

data class AnswerState(
    val answer: Answer? = null
) : State()

sealed class AnswerAction : Action() {
    object FetchAnswer : AnswerAction()

    data class Answer(val answer: entity.models.Answer) : AnswerAction()

    object GoBack : AnswerAction()

    data class OnFavouritesClick(val state: Boolean) : AnswerAction()

    data class OnCreateFeedbackClick(val comment: String) : AnswerAction()

    object OnCommentLinkClick : AnswerAction()
}

sealed class AnswerEffect : Effect() {
    object GoBack : AnswerEffect()

    object ShowSendCommentCompleteNotify : AnswerEffect()
}