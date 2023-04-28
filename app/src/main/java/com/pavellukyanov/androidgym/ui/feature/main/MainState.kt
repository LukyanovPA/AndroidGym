package com.pavellukyanov.androidgym.ui.feature.main

import Constants.EMPTY_STRING
import com.pavellukyanov.androidgym.base.Action
import com.pavellukyanov.androidgym.base.Effect
import com.pavellukyanov.androidgym.base.State
import entity.questions.MainItems


data class MainState(
    override val isLoading: Boolean = false,
    val items: List<MainItems> = listOf(),
    val searchQuery: String = EMPTY_STRING
) : State()

sealed class MainAction : Action() {
    object FetchMain : MainAction()

    data class FetchSubcategories(val categoryId: Int) : MainAction()

    data class FetchQuestions(val subcategoryId: Int) : MainAction()

    data class Search(val query: String) : MainAction()

    data class Items(val items: List<MainItems>) : MainAction()

    data class OnCategoryClick(val categoryId: Int) : MainAction()

    data class OnSubcategoryClick(val subcategoryId: Int) : MainAction()

    data class OnQuestionClick(val questionId: Int) : MainAction()
}

sealed class MainEffect : Effect() {
    data class OnClick(val id: Int) : MainEffect()
}
