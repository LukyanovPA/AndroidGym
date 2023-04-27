package useCase.questions

import entity.questions.Question
import helper.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import repository.QuestionRepository

interface GetAllQuestions : suspend (Int) -> Flow<List<Question>>

internal class GetAllQuestionsImpl(
    private val repository: QuestionRepository
) : GetAllQuestions {
    override suspend fun invoke(subcategoryId: Int): Flow<List<Question>> =
        repository.getAllQuestions(subcategoryId = subcategoryId)
            .map { list -> list.map { it.map() } }
}