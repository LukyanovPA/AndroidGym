package com.pavellukyanov.androidgym.ui.feature.category

import Constants.EMPTY_STRING
import com.pavellukyanov.androidgym.base.Action
import com.pavellukyanov.androidgym.base.Effect
import com.pavellukyanov.androidgym.base.State
import entity.main.MainItems
import entity.models.Subcategory

data class CategoryState(
    val subcategories: List<MainItems> = listOf(),
    val categoryName: String = EMPTY_STRING,
    val searchQuery: String = EMPTY_STRING
) : State()

sealed class CategoryAction : Action() {
    data class SetCategoryName(val categoryName: String) : CategoryAction()

    data class Search(val query: String) : CategoryAction()

    object ClearSearch : CategoryAction()

    data class SetItems(val items: List<MainItems>) : CategoryAction()

    data class OnSubcategoryClick(val subcategory: Subcategory) : CategoryAction()

    object OnMenuClick : CategoryAction()

    object OnFavouriteClick : CategoryAction()

    object OnBackClick : CategoryAction()
}

sealed class CategoryEffect : Effect() {
    object GoBack : CategoryEffect()

    object OnMenuClicked : CategoryEffect()

    object GoToFavourites : CategoryEffect()

    object GoToSubcategory : CategoryEffect()
}