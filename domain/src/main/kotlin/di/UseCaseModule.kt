package di

import org.koin.dsl.module
import useCase.questions.GetAllCategories
import useCase.questions.GetAllCategoriesImpl
import useCase.questions.GetAllQuestions
import useCase.questions.GetAllQuestionsImpl
import useCase.questions.GetAllSubcategories
import useCase.questions.GetAllSubcategoriesImpl

internal val useCaseModule = module {
    //Questions
    single<GetAllCategories> { GetAllCategoriesImpl(get()) }
    single<GetAllSubcategories> { GetAllSubcategoriesImpl(get()) }
    single<GetAllQuestions> { GetAllQuestionsImpl(get()) }
}