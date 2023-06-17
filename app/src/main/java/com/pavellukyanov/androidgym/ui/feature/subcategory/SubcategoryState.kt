package com.pavellukyanov.androidgym.ui.feature.subcategory

import Constants.EMPTY_STRING
import com.pavellukyanov.androidgym.base.Action
import com.pavellukyanov.androidgym.base.Effect
import com.pavellukyanov.androidgym.base.State
import entity.main.MainItems
import entity.models.Question

data class SubcategoryState(
    val questions: List<MainItems> = listOf(),
    val subcategoryName: String = EMPTY_STRING,
    val searchQuery: String = EMPTY_STRING
) : State()

sealed class SubcategoryAction : Action() {
    data class SetSubcategoryName(val subcategoryName: String) : SubcategoryAction()

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

    object GoToAnswer : SubcategoryEffect()
}
