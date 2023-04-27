package useCase.questions

import entity.questions.Answer
import helper.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import repository.QuestionRepository

interface GetAnswer : suspend (Int) -> Flow<Answer>

internal class GetAnswerImpl(
    private val repo: QuestionRepository
) : GetAnswer {
    override suspend fun invoke(questionId: Int): Flow<Answer> =
        repo.getAnswer(questionId = questionId).map { it.map() }
}