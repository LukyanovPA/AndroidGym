package com.pavellukyanov.androidgym.ui.feature.favourites

import com.pavellukyanov.androidgym.base.Reducer
import entity.models.Answer
import kotlinx.coroutines.flow.map
import useCase.answer.SendId
import useCase.favourites.GetAllFavouritesAnswers
import useCase.favourites.UpdateFavouritesState

class FavouritesReducer(
    private val getAllFavouritesAnswers: GetAllFavouritesAnswers,
    private val sendId: SendId,
    private val updateFavouritesState: UpdateFavouritesState,
) : Reducer<FavouritesState, FavouritesAction, FavouritesEffect>(FavouritesState()) {

    override suspend fun reduce(oldState: FavouritesState, action: FavouritesAction) {
        when (action) {
            is FavouritesAction.Fetch -> getAll()
            is FavouritesAction.AllFavourites -> saveState(oldState.copy(favourites = action.favourites))
            is FavouritesAction.OnAnswerClick -> sendQuestion(questionId = action.questionId)
            is FavouritesAction.GoBack -> sendEffect(FavouritesEffect.GoBack)
            is FavouritesAction.OnDeleteFromFavourites -> onFavouritesUpdate(answer = action.answer)
        }
    }

    private fun getAll() = launchIO {
        getAllFavouritesAnswers()
            .map { answers -> FavouritesAction.AllFavourites(favourites = answers) }
            .collect(::sendAction)
    }

    private fun sendQuestion(questionId: Int) = launchCPU {
        sendId(questionId).also {
            sendEffect(FavouritesEffect.GoToAnswer)
        }
    }

    private fun onFavouritesUpdate(answer: Answer) = launchIO {
        updateFavouritesState(answer.id, !answer.isFavourites)
    }
}