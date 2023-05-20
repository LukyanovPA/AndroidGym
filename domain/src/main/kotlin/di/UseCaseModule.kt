package di

import org.koin.dsl.module
import useCase.answer.SendQuestionId
import useCase.answer.SendQuestionIdImpl
import useCase.questions.GetAllCategories
import useCase.questions.GetAllCategoriesImpl
import useCase.answer.GetAnswer
import useCase.answer.GetAnswerImpl
import useCase.questions.Search
import useCase.questions.SearchImpl

internal val useCaseModule = module {
    //Questions
    single<GetAllCategories> { GetAllCategoriesImpl(repo = get()) }

    //Search
    single<Search> { SearchImpl(repo = get()) }

    //Answer
    single<SendQuestionId> { SendQuestionIdImpl(questionIdStorage = get()) }
    single<GetAnswer> { GetAnswerImpl(repo = get(), questionIdStorage = get()) }
}