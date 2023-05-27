package useCase.answer

import entity.answer.QuestionIdStorage

interface SendQuestionId : suspend (Int) -> Unit

internal class SendQuestionIdImpl(
    private val questionIdStorage: QuestionIdStorage
) : SendQuestionId {
    override suspend operator fun invoke(questionId: Int) {
        questionIdStorage.set(value = questionId)
    }
}