package useCase.favourites

import repository.QuestionRepository

interface UpdateFavouritesState : suspend (Int, Boolean) -> Unit

internal class UpdateFavouritesStateImpl(
    private val repo: QuestionRepository
) : UpdateFavouritesState {
    override suspend operator fun invoke(answerId: Int, state: Boolean) {
        repo.setFavouritesState(answerId = answerId, state = state)
    }
}