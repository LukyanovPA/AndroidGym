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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.pavellukyanov.androidgym.app.R
import com.pavellukyanov.androidgym.helper.Destinations
import com.pavellukyanov.androidgym.helper.ext.asUiState
import com.pavellukyanov.androidgym.helper.ext.receive
import com.pavellukyanov.androidgym.ui.theme.ColorLightGreen
import kotlinx.coroutines.flow.receiveAsFlow
import org.koin.androidx.compose.koinViewModel

@Composable
fun FavouritesScreen(
    navController: NavController,
    reducer: FavouritesReducer = koinViewModel()
) {
    val state by reducer.state.asUiState()

    LaunchedEffect(key1 = true) {
        reducer.sendAction(FavouritesAction.Fetch)
        reducer.effect.receiveAsFlow().collect { effect ->
            when (effect) {
                is FavouritesEffect.GoBack -> navController.popBackStack()
                is FavouritesEffect.GoToAnswer -> navController.navigate(Destinations.Answer.ANSWER)
            }
        }
    }

    Scaffold { padding ->
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
            .padding(padding)
            .padding(start = 16.dp, end = 16.dp)
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Button(
                modifier = Modifier.size(40.dp),
                onClick = { onAction(FavouritesAction.GoBack) },
                shape = CircleShape,
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = ColorLightGreen)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(id = R.string.decs_button_back)
                )
            }
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = stringResource(id = R.string.favourites_title),
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
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
            .fillMaxSize()
    ) {
        items(
            items = state.favourites,
            key = { it.id }
        ) { answer ->
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
                        .clickable { onAction(FavouritesAction.OnAnswerClick(questionId = answer.questionId)) }
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = answer.categoryName,
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
                            text = answer.subcategoryName,
                            fontSize = 14.sp,
                            color = Color.DarkGray
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = answer.question,
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                }
                Button(
                    modifier = Modifier
                        .padding(8.dp)
                        .size(30.dp),
                    onClick = { onAction(FavouritesAction.OnDeleteFromFavourites(answer = answer)) },
                    shape = CircleShape,
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Yellow)
                ) {
                    Icon(
                        modifier = Modifier
                            .alpha(0.7f),
                        imageVector = Icons.Default.Delete,
                        contentDescription = stringResource(id = R.string.any_image),
                        tint = Color.Red
                    )
                }
            }
        }
    }
}