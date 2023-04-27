package com.pavellukyanov.androidgym.ui.feature.categories

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.pavellukyanov.androidgym.helper.ext.asUiState
import com.pavellukyanov.androidgym.helper.ext.receive
import entity.questions.Category
import org.koin.androidx.compose.koinViewModel

@Composable
fun CategoriesScreen(
    navController: NavController,
    vm: CategoriesReducer = koinViewModel()
) {
    val state by vm.state.asUiState()

    LaunchedEffect(key1 = true) {
        vm.sendAction(CategoriesAction.StartFetch)
    }

    Scaffold { padding ->
        state.receive<CategoriesState> { currentState ->
            Box(
                modifier = Modifier.padding(padding)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    CategoriesScreenContent(categories = currentState.categories) { vm.sendAction(it) }
                }
            }
        }
    }
}

@Composable
fun CategoriesScreenContent(
    categories: List<Category>,
    onAction: (CategoriesAction) -> Unit
) {
    LazyColumn {
        items(
            items = categories,
            key = { it.id }
        ) { category ->
//            Box(
//                modifier = Modifier
//                    .size(35.dp)
//                    .clip(shape = RoundedCornerShape(16.dp))
//                    .background(color = Color.White)
//                    .clickable { onAction(CategoriesAction.GoToCategory(category.id)) },
//                contentAlignment = Alignment.Center
//            ) {
            Text(
                text = category.name,
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { onAction(CategoriesAction.GoToCategory(category.id)) }
            )
//            }
        }
    }
}