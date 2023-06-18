package di

import org.koin.dsl.module
import repository.FeedbackRepository
import repository.QuestionRepository
import repository.impl.FeedbackRepositoryImpl
import repository.impl.QuestionRepositoryImpl

internal val repositoryModule = module {
    single<QuestionRepository> { QuestionRepositoryImpl(localQuestions = get(), cacheHelper = get()) }
    single<FeedbackRepository> { FeedbackRepositoryImpl(networkFeedback = get()) }
}