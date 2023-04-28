package di

import org.koin.dsl.module
import useCase.questions.GetAllCategories
import useCase.questions.GetAllCategoriesImpl
import useCase.questions.GetQuestionsBySubcategoryId
import useCase.questions.GetQuestionsBySubcategoryIdImpl
import useCase.questions.GetSubcategoriesByCategoryId
import useCase.questions.GetSubcategoriesByCategoryIdImpl
import useCase.questions.GetAnswer
import useCase.questions.GetAnswerImpl
import useCase.questions.Search
import useCase.questions.SearchImpl

internal val useCaseModule = module {
    //Questions
    single<GetAllCategories> { GetAllCategoriesImpl(get()) }
    single<GetSubcategoriesByCategoryId> { GetSubcategoriesByCategoryIdImpl(get()) }
    single<GetQuestionsBySubcategoryId> { GetQuestionsBySubcategoryIdImpl(get()) }
    single<GetAnswer> { GetAnswerImpl(get()) }

    //Search
    single<Search> { SearchImpl(get()) }
}