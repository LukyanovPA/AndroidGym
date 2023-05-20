package com.pavellukyanov.androidgym.ui.feature.main

import com.pavellukyanov.androidgym.base.Action
import com.pavellukyanov.androidgym.base.Effect
import com.pavellukyanov.androidgym.base.State
import entity.questions.Category
import entity.questions.MainItems
import entity.questions.Subcategory


data class MainState(
    override val isLoading: Boolean = false,
    val items: List<MainItems> = listOf()
) : State()

sealed class MainAction : Action() {
    object FetchMain : MainAction()

    data class Search(val query: String) : MainAction()

    data class Items(val items: List<MainItems>) : MainAction()

    data class OnQuestionClick(val questionId: Int) : MainAction()

    data class CategoryExpand(val category: Category) : MainAction()

    data class SearchSubcategoryExpand(val subcategory: Subcategory) : MainAction()

    data class SubcategoryExpand(val categoryId: Int, val subcategory: Subcategory) : MainAction()
}

sealed class MainEffect : Effect() {
    data class OnClick(val id: Int) : MainEffect()
}
