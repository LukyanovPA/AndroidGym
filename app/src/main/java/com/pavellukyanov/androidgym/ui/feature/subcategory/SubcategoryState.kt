package com.pavellukyanov.androidgym.ui.feature.subcategory

import Constants.EMPTY_STRING
import Constants.INT_MINUS_ONE
import com.pavellukyanov.androidgym.base.Action
import com.pavellukyanov.androidgym.base.Effect
import com.pavellukyanov.androidgym.base.State
import entity.main.MainItems
import entity.models.Question

data class SubcategoryState(
    val questions: List<MainItems> = listOf(),
    val subcategoryName: String = EMPTY_STRING,
    val subcategoryId: Int = INT_MINUS_ONE,
    val searchQuery: String = EMPTY_STRING
) : State()

sealed class SubcategoryAction : Action() {
    data class SetSubcategoryValues(val subcategoryName: String, val subcategoryId: Int) : SubcategoryAction()

    data class Search(val query: String) : SubcategoryAction()

    object ClearSearch : SubcategoryAction()

    data class SetItems(val items: List<MainItems>) : SubcategoryAction()

    data class OnQuestionClick(val question: Question) : SubcategoryAction()

    object OnMenuClick : SubcategoryAction()

    object OnFavouriteClick : SubcategoryAction()

    object OnBackClick : SubcategoryAction()
}

sealed class SubcategoryEffect : Effect() {
    object GoBack : SubcategoryEffect()

    object OnMenuClicked : SubcategoryEffect()

    object GoToFavourites : SubcategoryEffect()

    data class GoToAnswer(val questionId: Int) : SubcategoryEffect()
}
