package di

import org.koin.dsl.module
import useCase.questions.GetAllCategories
import useCase.questions.GetAllCategoriesImpl

internal val useCaseModule = module {
    //Questions
    single<GetAllCategories> { GetAllCategoriesImpl(get()) }
}