package di

import org.koin.dsl.module
import useCase.answer.GetAnswer
import useCase.answer.GetAnswerImpl
import useCase.answer.SendQuestionId
import useCase.answer.SendQuestionIdImpl
import useCase.questions.GetCategories
import useCase.questions.GetCategoriesImpl
import useCase.questions.Search
import useCase.questions.SearchImpl

internal val useCaseModule = module {
    //Questions
    single<Search> { SearchImpl(repo = get()) }
    single<GetCategories> { GetCategoriesImpl(repo = get()) }

    //Answer
    single<SendQuestionId> { SendQuestionIdImpl(questionIdStorage = get()) }
    single<GetAnswer> { GetAnswerImpl(repo = get(), questionIdStorage = get()) }
}