package com.pavellukyanov.androidgym.ui.feature.favourites

import Constants.EMPTY_STRING
import com.pavellukyanov.androidgym.base.Reducer
import com.pavellukyanov.androidgym.helper.AnalyticsClient
import com.pavellukyanov.androidgym.helper.AnalyticsClient.Events.CLICK_MENU
import com.pavellukyanov.androidgym.helper.AnalyticsClient.Events.SEARCH
import com.pavellukyanov.androidgym.helper.AnalyticsClient.ScreenNames.FAVOURITES
import entity.main.MainItems
import entity.models.Question
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.map
import useCase.favourites.GetAllFavouritesAnswers
import useCase.favourites.UpdateFavouritesState
import kotlin.time.Duration.Companion.milliseconds

class FavouritesReducer(
    private val getAllFavouritesAnswers: GetAllFavouritesAnswers,
    private val updateFavouritesState: UpdateFavouritesState,
) : Reducer<FavouritesState, FavouritesAction, FavouritesEffect>(FavouritesState()) {
    private val searchQuery = MutableStateFlow(EMPTY_STRING)

    override suspend fun reduce(oldState: FavouritesState, action: FavouritesAction) {
        when (action) {
            is FavouritesAction.Fetch -> {
                saveState(oldState.copy(favourites = listOf(MainItems.Loading)))
                getAll()
            }

            is FavouritesAction.Search -> {
                saveState(oldState.copy(searchQuery = searchQuery.value))
                searchQuery.emit(action.query)
                AnalyticsClient.trackEvent(FAVOURITES, SEARCH)
            }

            is FavouritesAction.ClearSearch -> {
                saveState(oldState.copy(favourites = listOf(MainItems.Loading), searchQuery = EMPTY_STRING))
                searchQuery.emit(EMPTY_STRING)
            }

            is FavouritesAction.OnMenuClick -> {
                sendEffect(FavouritesEffect.OnMenuClicked)
                AnalyticsClient.trackEvent(FAVOURITES, CLICK_MENU)
            }

            is FavouritesAction.AllFavourites -> saveState(oldState.copy(favourites = action.favourites))
            is FavouritesAction.OnAnswerClick -> sendEffect(FavouritesEffect.GoToAnswer(questionId = action.questionId))
            is FavouritesAction.GoBack -> sendEffect(FavouritesEffect.GoBack)
            is FavouritesAction.OnDeleteFromFavourites -> onFavouritesUpdate(question = action.question)
            is FavouritesAction.OnMainClick -> sendEffect(FavouritesEffect.GoToMain)
        }
    }

    @OptIn(FlowPreview::class)
    private fun getAll() = launchIO {
        searchQuery
            .debounce(300.milliseconds)
            .flatMapMerge { query -> getAllFavouritesAnswers(query) }
            .map { answers -> FavouritesAction.AllFavourites(favourites = answers) }
            .collect(::sendAction)
    }

    private fun onFavouritesUpdate(question: Question) = launchIO {
        updateFavouritesState(question.id)
    }
}