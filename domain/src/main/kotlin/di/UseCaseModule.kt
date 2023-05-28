package di

import org.koin.dsl.module
import useCase.answer.GetAnswer
import useCase.answer.GetAnswerImpl
import useCase.answer.SendQuestionId
import useCase.answer.SendQuestionIdImpl
import useCase.answer.UpdateFavouritesState
import useCase.answer.UpdateFavouritesStateImpl
import useCase.answerfeedback.CreateAnswerFeedback
import useCase.answerfeedback.CreateAnswerFeedbackImpl
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
    single<UpdateFavouritesState> { UpdateFavouritesStateImpl(repo = get()) }
    single<CreateAnswerFeedback> { CreateAnswerFeedbackImpl(repository = get()) }
}