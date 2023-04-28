package com.pavellukyanov.androidgym.ui.feature.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.pavellukyanov.androidgym.app.R
import com.pavellukyanov.androidgym.helper.ext.asUiState
import com.pavellukyanov.androidgym.helper.ext.receive
import com.pavellukyanov.androidgym.ui.wiget.BaseImage
import com.pavellukyanov.androidgym.ui.wiget.SearchTextField
import entity.questions.MainItems
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen(
    navController: NavController,
    vm: MainReducer = koinViewModel()
) {
    val state by vm.state.asUiState()

    LaunchedEffect(key1 = true) {
        vm.sendAction(MainAction.FetchMain)
    }

    Scaffold { padding ->
        state.receive<MainState> { currentState ->
            MainScreenContent(state = currentState, padding = padding, onAction = { vm.sendAction(it) })
        }
    }
}

@Composable
fun MainScreenContent(
    state: MainState,
    padding: PaddingValues,
    onAction: (MainAction) -> Unit
) {
    Box(
        modifier = Modifier.padding(padding)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Spacer(modifier = Modifier.height(26.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.app_name),
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 20.sp
                )
            }
            Spacer(modifier = Modifier.height(26.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                SearchTextField(
                    text = state.searchQuery,
                    onTextChange = { onAction(MainAction.Search(query = it)) },
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp)
                        .clip(RoundedCornerShape(20.dp))
                )
            }
            Spacer(modifier = Modifier.height(26.dp))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .fillMaxSize()
                    .background(color = Color.White)
            ) {
                QuestionsList(
                    categories = state.items
                ) { onAction(it) }
            }
        }
    }
}

@Composable
fun QuestionsList(
    categories: List<MainItems>,
    onAction: (MainAction) -> Unit
) {
    LazyColumn {
        items(
            items = categories,
            key = { it.id }
        ) { item ->
            when (item) {
                is MainItems.CategoryItem -> {
                    Row(
                        modifier = Modifier
                            .clickable { onAction(MainAction.OnCategoryClick(categoryId = item.category.id)) }
                            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
                    ) {
                        BaseImage(
                            url = when (item.category.id) {
                                1 -> R.drawable.ic_java
                                2 -> R.drawable.ic_kotlin
                                3 -> R.drawable.ic_android
                                else -> R.drawable.ic_other
                            },
                            size = 30.dp,
                        )
                        Text(
                            text = item.category.name,
                            modifier = Modifier
                                .padding(start = 16.dp)
                        )
                    }
                }

                is MainItems.SubcategoryItem -> {}
                is MainItems.QuestionItem -> {}
                is MainItems.NotFoundItem -> {}
            }
        }
    }
}