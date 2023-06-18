package useCase.favourites

import repository.QuestionRepository

interface UpdateFavouritesState : suspend (Int) -> Unit

internal class UpdateFavouritesStateImpl(
    private val repo: QuestionRepository
) : UpdateFavouritesState {
    override suspend operator fun invoke(questionId: Int) {
        repo.setFavouritesState(questionId = questionId)
    }
}