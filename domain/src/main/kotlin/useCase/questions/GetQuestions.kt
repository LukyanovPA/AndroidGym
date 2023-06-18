package useCase.questions

import entity.main.MainItems
import ext.CPU
import helper.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import repository.QuestionRepository

interface GetQuestions : suspend (String, Int) -> Flow<List<MainItems>>

internal class GetQuestionsImpl(
    private val repo: QuestionRepository
) : GetQuestions {
    override suspend fun invoke(query: String, subcategoryId: Int): Flow<List<MainItems>> =
        repo.getQuestionsBySubcategoryId(subcategoryId = subcategoryId)
            .map { list -> list.filter { it.question.contains(query, ignoreCase = true) }.map { it.map() } }
            .map { filteredList ->
                mutableListOf<MainItems>().apply {
                    if (filteredList.isEmpty()) add(MainItems.NotFoundItem) else addAll(MainItems.QuestionItem.map(filteredList))
                }
            }
            .CPU()
}