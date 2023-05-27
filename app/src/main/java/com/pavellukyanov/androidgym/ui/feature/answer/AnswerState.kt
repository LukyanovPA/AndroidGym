package com.pavellukyanov.androidgym.ui.feature.answer

import com.pavellukyanov.androidgym.base.Action
import com.pavellukyanov.androidgym.base.Effect
import com.pavellukyanov.androidgym.base.State
import entity.answer.Answer

data class AnswerState(
    val answer: Answer? = null
) : State()

sealed class AnswerAction : Action() {
    object FetchAnswer : AnswerAction()

    data class Answer(val answer: entity.answer.Answer) : AnswerAction()

    object GoBack : AnswerAction()

    data class OnFavouritesClick(val state: Boolean) : AnswerAction()
}

sealed class AnswerEffect : Effect() {
    object GoBack : AnswerEffect()
}