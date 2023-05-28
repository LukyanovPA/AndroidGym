package useCase.answerfeedback

import repository.AnswerFeedbackRepository

interface CreateAnswerFeedback : suspend (Int, String) -> Unit

internal class CreateAnswerFeedbackImpl(
    private val repository: AnswerFeedbackRepository
) : CreateAnswerFeedback {
    override suspend operator fun invoke(answerId: Int, comment: String) =
        repository.invoke(answerId, comment)
}