package di

import org.koin.dsl.module
import useCase.answer.GetAllFavouritesAnswers
import useCase.answer.GetAllFavouritesAnswersImpl
import useCase.answer.GetAnswer
import useCase.answer.GetAnswerImpl
import useCase.answer.SendId
import useCase.answer.SendIdImpl
import useCase.answer.UpdateFavouritesState
import useCase.answer.UpdateFavouritesStateImpl
import useCase.answerfeedback.CreateAnswerFeedback
import useCase.answerfeedback.CreateAnswerFeedbackImpl
import useCase.category.GetSubcategories
import useCase.category.GetSubcategoriesImpl
import useCase.questions.GlobalSearch
import useCase.questions.GlobalSearchImpl

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
}