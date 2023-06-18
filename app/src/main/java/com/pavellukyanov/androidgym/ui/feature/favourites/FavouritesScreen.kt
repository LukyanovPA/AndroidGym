package com.pavellukyanov.androidgym.ui.feature.favourites

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.pavellukyanov.androidgym.app.R
import com.pavellukyanov.androidgym.helper.Destinations
import com.pavellukyanov.androidgym.helper.ext.asUiState
import com.pavellukyanov.androidgym.helper.ext.receive
import com.pavellukyanov.androidgym.ui.theme.ColorLightGreen
import com.pavellukyanov.androidgym.ui.wiget.HeaderContent
import com.pavellukyanov.androidgym.ui.wiget.LoadingScreen
import com.pavellukyanov.androidgym.ui.wiget.MenuActions
import com.pavellukyanov.androidgym.ui.wiget.MenuContent
import com.pavellukyanov.androidgym.ui.wiget.NotFoundContent
import entity.main.MainItems
import kotlinx.coroutines.flow.receiveAsFlow
import org.koin.androidx.compose.koinViewModel

private const val FAV_TITLE = "Избранное"

@Composable
fun FavouritesScreen(
    navController: NavController,
    reducer: FavouritesReducer = koinViewModel()
) {
    val state by reducer.state.asUiState()
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        reducer.sendAction(FavouritesAction.Fetch)
        reducer.effect.receiveAsFlow().collect { effect ->
            when (effect) {
                is FavouritesEffect.GoBack -> navController.popBackStack()
                is FavouritesEffect.GoToAnswer -> navController.navigate(Destinations.Answer.nav(questionId = effect.questionId))
                is FavouritesEffect.GoToMain -> {
                    scaffoldState.drawerState.close()
                    navController.navigate(Destinations.Main.MAIN)
                }

                is FavouritesEffect.OnMenuClicked -> scaffoldState.drawerState.open()
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = {
            MenuContent(favouritesVisibility = false) { action ->
                when (action) {
                    is MenuActions.Favourites -> reducer.sendAction(FavouritesAction.Fetch)
                    is MenuActions.Main -> reducer.sendAction(FavouritesAction.OnMainClick)
                }
            }
        }
    ) { padding ->
        state.receive<FavouritesState> { currentState ->
            FavouritesContent(state = currentState, padding = padding, onAction = { reducer.sendAction(it) })
        }
    }
}

@Composable
private fun FavouritesContent(
    state: FavouritesState,
    padding: PaddingValues,
    onAction: (FavouritesAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        HeaderContent(
            title = FAV_TITLE,
            searchQuery = state.searchQuery,
            placeholderText = R.string.favourites_search_placeholder,
            onBackClick = { onAction(FavouritesAction.GoBack) },
            onMenuClick = { onAction(FavouritesAction.OnMenuClick) },
            onSearchClick = { onAction(FavouritesAction.Search(query = it)) },
            onClearClick = { onAction(FavouritesAction.ClearSearch) }
        )
        FavouriteAnswerContent(
            state = state,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .wrapContentHeight(),
            onAction = onAction
        )
    }
}

@Composable
fun FavouriteAnswerContent(
    state: FavouritesState,
    modifier: Modifier = Modifier,
    onAction: (FavouritesAction) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
            .fillMaxSize()
    ) {
        items(
            items = state.favourites,
            key = { it.id }
        ) { item ->
            when (item) {
                is MainItems.QuestionItem -> {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 4.dp, end = 4.dp, top = 6.dp, bottom = 6.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(color = ColorLightGreen)
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(3f)
                                .padding(10.dp)
                                .clickable { onAction(FavouritesAction.OnAnswerClick(questionId = item.question.id)) }
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = item.question.categoryName,
                                    fontSize = 14.sp,
                                    color = Color.DarkGray
                                )
                                Icon(
                                    modifier = Modifier
                                        .padding(start = 2.dp, end = 2.dp)
                                        .size(14.dp),
                                    imageVector = Icons.Default.ArrowForward,
                                    contentDescription = stringResource(id = R.string.any_image),
                                    tint = Color.DarkGray
                                )
                                Text(
                                    text = item.question.subcategoryName,
                                    fontSize = 14.sp,
                                    color = Color.DarkGray
                                )
                            }

                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = item.question.question,
                                fontSize = 18.sp,
                                color = Color.Black
                            )
                        }
                        Button(
                            modifier = Modifier
                                .padding(10.dp)
                                .size(30.dp),
                            onClick = { onAction(FavouritesAction.OnDeleteFromFavourites(question = item.question)) },
                            shape = CircleShape,
                            contentPadding = PaddingValues(0.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Yellow)
                        ) {
                            Icon(
                                modifier = Modifier
                                    .alpha(0.7f),
                                imageVector = Icons.Default.Delete,
                                contentDescription = stringResource(id = R.string.any_image),
                                tint = Color.Black
                            )
                        }
                    }
                }

                is MainItems.NotFoundItem -> NotFoundContent()
                else -> LoadingScreen()
            }
        }
    }
}