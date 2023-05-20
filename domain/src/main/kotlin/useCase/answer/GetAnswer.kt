package useCase.answer

import entity.answer.Answer
import entity.answer.QuestionIdStorage
import ext.CPU
import helper.map
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.map
import repository.QuestionRepository

interface GetAnswer : suspend () -> Flow<Answer>

internal class GetAnswerImpl(
    private val repo: QuestionRepository,
    private val questionIdStorage: QuestionIdStorage
) : GetAnswer {
    @OptIn(FlowPreview::class)
    override suspend operator fun invoke(): Flow<Answer> =
        questionIdStorage.get()
            .flatMapMerge { questionId ->
                repo.getAnswer(questionId = questionId)
                    .map { it.map() }
                    .CPU()
            }
}