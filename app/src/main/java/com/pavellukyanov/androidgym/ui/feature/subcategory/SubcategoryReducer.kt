package com.pavellukyanov.androidgym.ui.feature.subcategory

import Constants.EMPTY_STRING
import com.pavellukyanov.androidgym.base.Reducer
import com.pavellukyanov.androidgym.helper.AnalyticsClient
import com.pavellukyanov.androidgym.helper.AnalyticsClient.Events.CLICK_FAVOURITES
import com.pavellukyanov.androidgym.helper.AnalyticsClient.Events.CLICK_MENU
import com.pavellukyanov.androidgym.helper.AnalyticsClient.Events.CLICK_QUESTION
import com.pavellukyanov.androidgym.helper.AnalyticsClient.Events.SEARCH
import com.pavellukyanov.androidgym.helper.AnalyticsClient.ScreenNames.MAIN_MENU
import com.pavellukyanov.androidgym.helper.AnalyticsClient.ScreenNames.SUBCATEGORY
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.map
import useCase.answer.SendId
import useCase.questions.GetQuestions
import kotlin.time.Duration.Companion.milliseconds

class SubcategoryReducer(
    private val getQuestions: GetQuestions,
    private val sendId: SendId
) : Reducer<SubcategoryState, SubcategoryAction, SubcategoryEffect>(SubcategoryState()) {
    private val searchQuery = MutableStateFlow(EMPTY_STRING)

    init {
        onSearch()
    }

    override suspend fun reduce(oldState: SubcategoryState, action: SubcategoryAction) {
        when (action) {
            is SubcategoryAction.SetSubcategoryName -> {
                saveState(oldState.copy(subcategoryName = action.subcategoryName))
            }

            is SubcategoryAction.Search -> {
                saveState(oldState.copy(searchQuery = searchQuery.value))
                searchQuery.emit(action.query)
                AnalyticsClient.trackEvent(SUBCATEGORY, SEARCH)
            }

            is SubcategoryAction.ClearSearch -> {
                saveState(oldState.copy(questions = listOf(), searchQuery = EMPTY_STRING))
                searchQuery.emit(EMPTY_STRING)
            }

            is SubcategoryAction.SetItems -> {
                saveState(oldState.copy(questions = action.items))
            }

            is SubcategoryAction.OnQuestionClick -> {
                onSendId(id = action.question.id)
                AnalyticsClient.trackEvent(SUBCATEGORY, CLICK_QUESTION + action.question.question)
            }

            is SubcategoryAction.OnMenuClick -> {
                AnalyticsClient.trackEvent(SUBCATEGORY, CLICK_MENU)
                sendEffect(SubcategoryEffect.OnMenuClicked)
            }

            is SubcategoryAction.OnFavouriteClick -> {
                sendEffect(SubcategoryEffect.GoToFavourites)
                AnalyticsClient.trackEvent(MAIN_MENU, CLICK_FAVOURITES)
            }

            is SubcategoryAction.OnBackClick -> sendEffect(SubcategoryEffect.GoBack)
        }
    }

    @OptIn(FlowPreview::class)
    private fun onSearch() = launchIO {
        searchQuery
            .debounce(300.milliseconds)
            .flatMapMerge { query -> getQuestions(query) }
            .map { items -> SubcategoryAction.SetItems(items = items) }
            .collect(::sendAction)
    }

    private fun onSendId(id: Int) = launchCPU {
        sendId(id).also {
            sendEffect(SubcategoryEffect.GoToAnswer)
        }
    }
}