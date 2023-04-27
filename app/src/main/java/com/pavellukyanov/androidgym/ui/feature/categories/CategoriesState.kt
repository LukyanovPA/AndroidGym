package com.pavellukyanov.androidgym.ui.feature.categories

import com.pavellukyanov.androidgym.base.Action
import com.pavellukyanov.androidgym.base.Effect
import com.pavellukyanov.androidgym.base.State
import entity.questions.Category


data class CategoriesState(
    override val isLoading: Boolean = false,
    val categories: List<Category> = listOf()
) : State()

sealed class CategoriesAction : Action() {
    object StartFetch : CategoriesAction()

    data class Categories(val categories: List<Category>) : CategoriesAction()

    data class GoToCategory(val id: Int) : CategoriesAction()
}

sealed class CategoriesEffect : Effect() {
    data class OnClick(val id: Int) : CategoriesEffect()
}
