package com.pavellukyanov.androidgym.ui.feature.favourites

import Constants.EMPTY_STRING
import com.pavellukyanov.androidgym.base.Action
import com.pavellukyanov.androidgym.base.Effect
import com.pavellukyanov.androidgym.base.State
import entity.main.MainItems
import entity.models.Question

data class FavouritesState(
    val favourites: List<MainItems> = listOf(),
    val searchQuery: String = EMPTY_STRING
) : State()

sealed class FavouritesAction : Action() {
    object Fetch : FavouritesAction()

    data class Search(val query: String) : FavouritesAction()

    object ClearSearch : FavouritesAction()

    data class AllFavourites(val favourites: List<MainItems>) : FavouritesAction()

    data class OnAnswerClick(val questionId: Int) : FavouritesAction()

    object GoBack : FavouritesAction()

    data class OnDeleteFromFavourites(val question: Question) : FavouritesAction()

    object OnMenuClick : FavouritesAction()

    object OnMainClick : FavouritesAction()
}

sealed class FavouritesEffect : Effect() {
    object GoBack : FavouritesEffect()

    data class GoToAnswer(val questionId: Int) : FavouritesEffect()

    object GoToMain : FavouritesEffect()

    object OnMenuClicked : FavouritesEffect()
}
