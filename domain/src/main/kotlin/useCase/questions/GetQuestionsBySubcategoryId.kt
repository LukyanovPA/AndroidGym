package useCase.questions

import entity.questions.MainItems
import helper.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import repository.QuestionRepository

interface GetQuestionsBySubcategoryId : suspend (Int) -> Flow<List<MainItems>>

internal class GetQuestionsBySubcategoryIdImpl(
    private val repository: QuestionRepository
) : GetQuestionsBySubcategoryId {
    override suspend fun invoke(subcategoryId: Int): Flow<List<MainItems>> =
        repository.getAllQuestions(subcategoryId = subcategoryId)
            .map { list -> MainItems.QuestionItem.map(list.map { it.map() }) }
}