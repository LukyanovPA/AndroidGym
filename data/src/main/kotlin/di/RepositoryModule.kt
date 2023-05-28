package di

import org.koin.dsl.module
import repository.AnswerFeedbackRepository
import repository.QuestionRepository
import repository.impl.AnswerFeedbackRepositoryImpl
import repository.impl.QuestionRepositoryImpl

internal val repositoryModule = module {
    single<QuestionRepository> { QuestionRepositoryImpl(localQuestions = get(), cacheHelper = get()) }
    single<AnswerFeedbackRepository> { AnswerFeedbackRepositoryImpl(networkAnswerFeedback = get()) }
}