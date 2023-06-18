package useCase.feedback

import repository.FeedbackRepository

interface CreateAnswerFeedback : suspend (Int, String) -> Unit

internal class CreateAnswerFeedbackImpl(
    private val repository: FeedbackRepository
) : CreateAnswerFeedback {
    override suspend operator fun invoke(answerId: Int, comment: String) =
        repository.createAnswerComment(answerId = answerId, comment = comment)
}