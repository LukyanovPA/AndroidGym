package com.pavellukyanov.androidgym.ui.feature.subcategory

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.pavellukyanov.androidgym.app.R
import com.pavellukyanov.androidgym.helper.Destinations
import com.pavellukyanov.androidgym.helper.ext.asUiState
import com.pavellukyanov.androidgym.helper.ext.receive
import com.pavellukyanov.androidgym.ui.wiget.HeaderContent
import com.pavellukyanov.androidgym.ui.wiget.LoadingScreen
import com.pavellukyanov.androidgym.ui.wiget.MenuContent
import com.pavellukyanov.androidgym.ui.wiget.NotFoundContent
import com.pavellukyanov.androidgym.ui.wiget.QuestionItemContent
import entity.main.MainItems
import kotlinx.coroutines.flow.receiveAsFlow
import org.koin.androidx.compose.koinViewModel

@Composable
fun SubcategoryScreen(
    subcategoryName: String,
    subcategoryId: Int,
    navController: NavController,
    reducer: SubcategoryReducer = koinViewModel()
) {
    val state by reducer.state.asUiState()
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        reducer.sendAction(SubcategoryAction.SetSubcategoryValues(subcategoryName = subcategoryName, subcategoryId = subcategoryId))
        reducer.effect.receiveAsFlow().collect { effect ->
            when (effect) {
                is SubcategoryEffect.GoToAnswer -> navController.navigate(Destinations.Answer.nav(questionId = effect.questionId))
                is SubcategoryEffect.GoBack -> navController.popBackStack()
                is SubcategoryEffect.OnMenuClicked -> scaffoldState.drawerState.open()
                is SubcategoryEffect.GoToFavourites -> {
                    scaffoldState.drawerState.close()
                    navController.navigate(Destinations.Favourites.FAVOURITES)
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = {
            MenuContent(
                favouriteAction = SubcategoryAction.OnFavouriteClick,
                onAction = { reducer.sendAction(it as SubcategoryAction.OnFavouriteClick) })
        }
    ) { padding ->
        state.receive<SubcategoryState> { currentState ->
            SubcategoryContent(state = currentState, paddingValues = padding, onAction = { reducer.sendAction(it) })
        }
    }
}

@Composable
private fun SubcategoryContent(
    state: SubcategoryState,
    paddingValues: PaddingValues,
    onAction: (SubcategoryAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        HeaderContent(
            title = state.subcategoryName,
            searchQuery = state.searchQuery,
            placeholderText = R.string.subcategory_search_placeholder,
            onBackClick = { onAction(SubcategoryAction.OnBackClick) },
            onMenuClick = { onAction(SubcategoryAction.OnMenuClick) },
            onSearchClick = { onAction(SubcategoryAction.Search(query = it)) },
            onClearClick = { onAction(SubcategoryAction.ClearSearch) }
        )
        SubcategoryListContent(
            state = state,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .wrapContentHeight()
        ) { onAction(it) }
    }
}

@Composable
private fun SubcategoryListContent(
    state: SubcategoryState,
    modifier: Modifier = Modifier,
    onAction: (SubcategoryAction) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
            .fillMaxSize()
    ) {
        items(
            items = state.questions,
            key = { it.id }
        ) { item ->
            when (item) {
                is MainItems.QuestionItem -> {
                    QuestionItemContent(
                        question = item.question,
                        onAction = { onAction(SubcategoryAction.OnQuestionClick(it)) }
                    )
                }

                is MainItems.NotFoundItem -> NotFoundContent()
                else -> LoadingScreen()
            }
        }
    }
}