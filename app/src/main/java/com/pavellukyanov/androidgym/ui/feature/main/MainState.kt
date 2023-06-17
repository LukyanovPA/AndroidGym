package com.pavellukyanov.androidgym.ui.feature.main

import Constants.EMPTY_STRING
import com.pavellukyanov.androidgym.base.Action
import com.pavellukyanov.androidgym.base.Effect
import com.pavellukyanov.androidgym.base.State
import entity.models.Category
import entity.main.MainItems
import entity.models.Question
import entity.models.Subcategory


data class MainState(
    val items: List<MainItems> = listOf(),
    val searchQuery: String = EMPTY_STRING,
) : State()

sealed class MainAction : Action() {
    data class Search(val query: String) : MainAction()

    object ClearSearch : MainAction()

    data class Items(val items: List<MainItems>) : MainAction()

    data class OnCategoryClick(val category: Category) : MainAction()

    data class OnSubcategoryClick(val subcategory: Subcategory) : MainAction()

    data class OnQuestionClick(val question: Question) : MainAction()

    object OnMenuClick : MainAction()

    object OnFavouriteClick : MainAction()
}

sealed class MainEffect : Effect() {
    object GoToAnswer : MainEffect()

    object OnMenuClicked : MainEffect()

    object GoToFavourites : MainEffect()

    data class GoToCategory(val categoryName: String) : MainEffect()

    object GoToSubcategory : MainEffect()
}
