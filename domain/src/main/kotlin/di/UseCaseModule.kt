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
import useCase.questions.Search
import useCase.questions.SearchImpl

internal val useCaseModule = module {
    //Questions
    single<Search> { SearchImpl(repo = get()) }

    //Answer
    single<SendId> { SendIdImpl(idStorage = get()) }
    single<GetAnswer> { GetAnswerImpl(repo = get(), idStorage = get()) }
    single<UpdateFavouritesState> { UpdateFavouritesStateImpl(repo = get()) }
    single<CreateAnswerFeedback> { CreateAnswerFeedbackImpl(repository = get()) }
    single<GetAllFavouritesAnswers> { GetAllFavouritesAnswersImpl(repo = get()) }
}