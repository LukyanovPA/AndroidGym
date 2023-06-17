package com.pavellukyanov.androidgym.ui.feature.favourites

import com.pavellukyanov.androidgym.base.Action
import com.pavellukyanov.androidgym.base.Effect
import com.pavellukyanov.androidgym.base.State
import entity.models.Answer

data class FavouritesState(
    val favourites: List<Answer> = listOf(),
    val isLoading: Boolean = true
) : State()

sealed class FavouritesAction : Action() {
    object Fetch : FavouritesAction()
    data class AllFavourites(val favourites: List<Answer>) : FavouritesAction()
    data class OnAnswerClick(val questionId: Int) : FavouritesAction()
    object GoBack : FavouritesAction()
    data class OnDeleteFromFavourites(val answer: Answer) : FavouritesAction()
}

sealed class FavouritesEffect : Effect() {
    object GoBack : FavouritesEffect()
    data class GoToAnswer(val questionId: Int) : FavouritesEffect()
}
