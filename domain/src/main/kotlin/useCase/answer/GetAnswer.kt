package useCase.answer

import entity.models.Answer
import ext.CPU
import helper.map
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.map
import repository.QuestionRepository

interface GetAnswer : suspend (Int) -> Flow<Answer>

internal class GetAnswerImpl(
    private val repo: QuestionRepository
) : GetAnswer {
    @OptIn(FlowPreview::class)
    override suspend operator fun invoke(questionId: Int): Flow<Answer> =
        repo.getAllQuestions()
            .map { questions -> questions.find { it.id == questionId } }
            .flatMapMerge { question ->
                repo.getAnswer(questionId = questionId)
                    .map { it.map(isFavourites = question!!.isFavourites) }
            }
            .CPU()
}