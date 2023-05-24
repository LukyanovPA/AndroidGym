package com.pavellukyanov.androidgym.ui.feature.main

import Constants.EMPTY_STRING
import com.pavellukyanov.androidgym.base.Action
import com.pavellukyanov.androidgym.base.Effect
import com.pavellukyanov.androidgym.base.State
import entity.questions.MainItems


data class MainState(
    val items: List<MainItems> = listOf(),
    val searchQuery: String = EMPTY_STRING,
    val expendMap: HashMap<Int, Boolean> = hashMapOf()
) : State()

sealed class MainAction : Action() {
    object FetchMain : MainAction()

    data class Search(val query: String) : MainAction()

    object ClearSearch : MainAction()

    data class Items(val items: List<MainItems>) : MainAction()

    data class OnQuestionClick(val questionId: Int) : MainAction()

    data class OnExpandClick(val id: Int) : MainAction()
}

sealed class MainEffect : Effect() {
    object GoToAnswer : MainEffect()
}
