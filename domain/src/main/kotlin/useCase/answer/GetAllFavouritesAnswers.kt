package useCase.answer

import entity.models.Answer
import ext.CPU
import helper.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import repository.QuestionRepository

interface GetAllFavouritesAnswers : suspend () -> Flow<List<Answer>>

internal class GetAllFavouritesAnswersImpl(
    private val repo: QuestionRepository
) : GetAllFavouritesAnswers {
    override suspend fun invoke(): Flow<List<Answer>> =
        repo.getAllFavouritesAnswers()
            .map { answers -> answers.map { it.map() } }
            .CPU()
}