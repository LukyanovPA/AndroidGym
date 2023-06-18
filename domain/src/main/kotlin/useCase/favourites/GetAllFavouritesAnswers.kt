package useCase.favourites

import entity.main.MainItems
import ext.CPU
import helper.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import repository.QuestionRepository

interface GetAllFavouritesAnswers : suspend (String) -> Flow<List<MainItems>>

internal class GetAllFavouritesAnswersImpl(
    private val repo: QuestionRepository
) : GetAllFavouritesAnswers {
    override suspend fun invoke(query: String): Flow<List<MainItems>> =
        repo.getAllFavouritesAnswers()
            .map { list -> list.filter { it.question.contains(query, ignoreCase = true) }.map { it.map() } }
            .map { filteredList ->
                mutableListOf<MainItems>().apply {
                    if (filteredList.isEmpty()) add(MainItems.NotFoundItem) else addAll(MainItems.QuestionItem.map(filteredList))
                }
            }
            .CPU()
}