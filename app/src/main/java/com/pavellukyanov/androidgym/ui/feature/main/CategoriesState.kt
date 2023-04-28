package com.pavellukyanov.androidgym.ui.feature.main

import Constants.EMPTY_STRING
import com.pavellukyanov.androidgym.base.Action
import com.pavellukyanov.androidgym.base.Effect
import com.pavellukyanov.androidgym.base.State
import entity.questions.MainItems


data class CategoriesState(
    override val isLoading: Boolean = false,
    val items: List<MainItems> = listOf(),
    val searchQuery: String = EMPTY_STRING
) : State()

sealed class CategoriesAction : Action() {
    object FetchCategories : CategoriesAction()

    data class FetchSubcategories(val categoryId: Int) : CategoriesAction()

    data class FetchQuestions(val subcategoryId: Int) : CategoriesAction()

    data class Search(val query: String) : CategoriesAction()

    data class Items(val items: List<MainItems>) : CategoriesAction()

    data class OnCategoryClick(val categoryId: Int) : CategoriesAction()

    data class OnSubcategoryClick(val subcategoryId: Int) : CategoriesAction()

    data class OnQuestionClick(val questionId: Int) : CategoriesAction()
}

sealed class CategoriesEffect : Effect() {
    data class OnClick(val id: Int) : CategoriesEffect()
}
