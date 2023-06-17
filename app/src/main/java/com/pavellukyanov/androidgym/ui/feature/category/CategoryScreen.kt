package com.pavellukyanov.androidgym.ui.feature.category

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
import com.pavellukyanov.androidgym.ui.wiget.MenuActions
import com.pavellukyanov.androidgym.ui.wiget.MenuContent
import com.pavellukyanov.androidgym.ui.wiget.NotFoundContent
import com.pavellukyanov.androidgym.ui.wiget.SubcategoryItemContent
import entity.main.MainItems
import kotlinx.coroutines.flow.receiveAsFlow
import org.koin.androidx.compose.koinViewModel

@Composable
fun CategoryScreen(
    categoryName: String,
    categoryId: Int,
    navController: NavController,
    reducer: CategoryReducer = koinViewModel()
) {
    val state by reducer.state.asUiState()
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        reducer.sendAction(CategoryAction.SetCategoryValues(categoryName = categoryName, categoryId = categoryId))
        reducer.effect.receiveAsFlow().collect { effect ->
            when (effect) {
                is CategoryEffect.GoToSubcategory -> navController.navigate(
                    Destinations.Subcategory.nav(
                        subcategoryName = effect.subcategory.name,
                        subcategoryId = effect.subcategory.id
                    )
                )

                is CategoryEffect.GoToMain -> navController.navigate(Destinations.Main.MAIN)
                is CategoryEffect.GoBack -> navController.popBackStack()
                is CategoryEffect.OnMenuClicked -> scaffoldState.drawerState.open()
                is CategoryEffect.GoToFavourites -> {
                    scaffoldState.drawerState.close()
                    navController.navigate(Destinations.Favourites.FAVOURITES)
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = {
            MenuContent { action ->
                when (action) {
                    is MenuActions.Favourites -> reducer.sendAction(CategoryAction.OnFavouriteClick)
                    is MenuActions.Main -> reducer.sendAction(CategoryAction.OnMainClick)
                }
            }
        }
    ) { padding ->
        state.receive<CategoryState> { currentState ->
            CategoryContent(state = currentState, paddingValues = padding, onAction = { reducer.sendAction(it) })
        }
    }
}

@Composable
private fun CategoryContent(
    state: CategoryState,
    paddingValues: PaddingValues,
    onAction: (CategoryAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        HeaderContent(
            title = state.categoryName,
            searchQuery = state.searchQuery,
            placeholderText = R.string.category_search_placeholder,
            onBackClick = { onAction(CategoryAction.OnBackClick) },
            onMenuClick = { onAction(CategoryAction.OnMenuClick) },
            onSearchClick = { onAction(CategoryAction.Search(query = it)) },
            onClearClick = { onAction(CategoryAction.ClearSearch) }
        )
        CategoriesListContent(
            state = state,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .wrapContentHeight()
        ) { onAction(it) }
    }
}

@Composable
private fun CategoriesListContent(
    state: CategoryState,
    modifier: Modifier = Modifier,
    onAction: (CategoryAction) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
            .fillMaxSize()
    ) {
        items(
            items = state.subcategories,
            key = { it.id }
        ) { item ->
            when (item) {
                is MainItems.SubcategoryItem -> {
                    SubcategoryItemContent(
                        subcategory = item.subcategory,
                        onAction = { onAction(CategoryAction.OnSubcategoryClick(subcategory = it)) }
                    )
                }

                is MainItems.NotFoundItem -> NotFoundContent()
                else -> LoadingScreen()
            }
        }
    }
}