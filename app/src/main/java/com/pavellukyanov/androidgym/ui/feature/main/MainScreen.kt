package com.pavellukyanov.androidgym.ui.feature.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.pavellukyanov.androidgym.app.R
import com.pavellukyanov.androidgym.helper.Destinations
import com.pavellukyanov.androidgym.helper.ext.asUiState
import com.pavellukyanov.androidgym.helper.ext.receive
import com.pavellukyanov.androidgym.ui.wiget.CategoryItemContent
import com.pavellukyanov.androidgym.ui.wiget.LoadingScreen
import com.pavellukyanov.androidgym.ui.wiget.MenuContent
import com.pavellukyanov.androidgym.ui.wiget.QuestionItemContent
import com.pavellukyanov.androidgym.ui.wiget.SearchTextField
import com.pavellukyanov.androidgym.ui.wiget.SubcategoryItemContent
import entity.main.MainItems
import kotlinx.coroutines.flow.receiveAsFlow
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen(
    navController: NavController,
    reducer: MainReducer = koinViewModel()
) {
    val state by reducer.state.asUiState()
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        reducer.effect.receiveAsFlow().collect { effect ->
            when (effect) {
                is MainEffect.GoToAnswer -> navController.navigate(Destinations.Answer.ANSWER)
                is MainEffect.OnMenuClicked -> scaffoldState.drawerState.open()
                is MainEffect.GoToFavourites -> {
                    scaffoldState.drawerState.close()
                    navController.navigate(Destinations.Favourites.FAVOURITES)
                }

                is MainEffect.GoToCategory -> navController.navigate(Destinations.Category.CATEGORY + effect.categoryName)
                is MainEffect.GoToSubcategory -> {}
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = {
            MenuContent(favouriteAction = MainAction.OnFavouriteClick, onAction = { reducer.sendAction(it as MainAction.OnFavouriteClick) })
        }
    ) { padding ->
        state.receive<MainState> { currentState ->
            MainScreenContent(state = currentState, padding = padding, onAction = { reducer.sendAction(it) })
        }
    }
}

@Composable
private fun MainScreenContent(
    state: MainState,
    padding: PaddingValues,
    onAction: (MainAction) -> Unit
) {
    Box(
        modifier = Modifier
            .padding(padding)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Spacer(modifier = Modifier.height(26.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.app_name),
                    color = Color.Black,
                    fontSize = 18.sp
                )
                Button(
                    modifier = Modifier
                        .size(40.dp),
                    onClick = { onAction(MainAction.OnMenuClick) },
                    shape = CircleShape,
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Yellow)
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = stringResource(id = R.string.decs_button_menu)
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(id = R.string.main_title),
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontSize = 28.sp,
                letterSpacing = 1.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp)
            )
            Spacer(modifier = Modifier.height(26.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                SearchTextField(
                    searchQuery = state.searchQuery,
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp)
                        .clip(RoundedCornerShape(40.dp)),
                    placeholderText = R.string.global_search_placeholder,
                    onSearchClick = { onAction(MainAction.Search(query = it)) },
                    onClearClick = { onAction(MainAction.ClearSearch) }
                )
            }
            Spacer(modifier = Modifier.height(26.dp))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .fillMaxSize()
                    .background(color = Color.White)
            ) {
                ItemsList(
                    state = state,
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .wrapContentHeight()
                ) { onAction(it) }
            }
        }
    }
}

@Composable
private fun ItemsList(
    state: MainState,
    modifier: Modifier = Modifier,
    onAction: (MainAction) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
            .fillMaxSize()
    ) {
        items(
            items = state.items,
            key = { it.id }
        ) { item ->
            when (item) {
                is MainItems.CategoryItem -> {
                    CategoryItemContent(
                        category = item.category,
                        onAction = { onAction(MainAction.OnCategoryClick(category = it)) }
                    )
                }

                is MainItems.Loading -> LoadingScreen()
                is MainItems.SubcategoryItem -> {
                    SubcategoryItemContent(
                        subcategory = item.subcategory,
                        onAction = { onAction(MainAction.OnSubcategoryClick(subcategory = it)) }
                    )
                }

                is MainItems.QuestionItem -> {
                    QuestionItemContent(
                        question = item.question,
                        onAction = { onAction(MainAction.OnQuestionClick(it)) }
                    )
                }

                is MainItems.NotFoundItem -> {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp, top = 32.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.search_not_found),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}