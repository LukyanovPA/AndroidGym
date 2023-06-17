package di

import org.koin.dsl.module
import useCase.answer.GetAnswer
import useCase.answer.GetAnswerImpl
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
    //Main
    single<GlobalSearch> { GlobalSearchImpl(repo = get()) }

    //Answer
    single<GetAnswer> { GetAnswerImpl(repo = get()) }

    //Category
    single<GetSubcategories> { GetSubcategoriesImpl(repo = get()) }

    //Subcategory
    single<GetQuestions> { GetQuestionsImpl(repo = get()) }

    //Favourites
    single<UpdateFavouritesState> { UpdateFavouritesStateImpl(repo = get()) }
    single<GetAllFavouritesAnswers> { GetAllFavouritesAnswersImpl(repo = get()) }

    //Feedback
    single<CreateAnswerFeedback> { CreateAnswerFeedbackImpl(repository = get()) }
}