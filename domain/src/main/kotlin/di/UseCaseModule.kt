package di

import org.koin.dsl.module
import useCase.answer.GetAnswer
import useCase.answer.GetAnswerImpl
import useCase.answer.SendId
import useCase.answer.SendIdImpl
import useCase.category.GetSubcategories
import useCase.category.GetSubcategoriesImpl
import useCase.favourites.GetAllFavouritesAnswers
import useCase.favourites.GetAllFavouritesAnswersImpl
import useCase.favourites.UpdateFavouritesState
import useCase.favourites.UpdateFavouritesStateImpl
import useCase.feedback.CreateAnswerFeedback
import useCase.feedback.CreateAnswerFeedbackImpl
import useCase.questions.GetQuestions
import useCase.questions.GetQuestionsImpl
import useCase.search.GlobalSearch
import useCase.search.GlobalSearchImpl

internal val useCaseModule = module {
    //Questions
    single<GlobalSearch> { GlobalSearchImpl(repo = get()) }

    //Answer
    single<SendId> { SendIdImpl(idStorage = get()) }
    single<GetAnswer> { GetAnswerImpl(repo = get(), idStorage = get()) }
    single<UpdateFavouritesState> { UpdateFavouritesStateImpl(repo = get()) }
    single<CreateAnswerFeedback> { CreateAnswerFeedbackImpl(repository = get()) }
    single<GetAllFavouritesAnswers> { GetAllFavouritesAnswersImpl(repo = get()) }

    //Category
    single<GetSubcategories> { GetSubcategoriesImpl(repo = get(), idStorage = get()) }

    //Subcategory
    single<GetQuestions> { GetQuestionsImpl(repo = get(), idStorage = get()) }
}