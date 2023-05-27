package com.pavellukyanov.androidgym.ui.feature.answer

import android.widget.TextView
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import androidx.navigation.NavController
import com.pavellukyanov.androidgym.app.R
import com.pavellukyanov.androidgym.helper.ext.asUiState
import com.pavellukyanov.androidgym.helper.ext.receive
import com.pavellukyanov.androidgym.ui.theme.ColorLightGreen
import entity.answer.Answer
import kotlinx.coroutines.flow.receiveAsFlow
import org.koin.androidx.compose.koinViewModel

@Composable
fun AnswerScreen(
    navController: NavController,
    vm: AnswerReducer = koinViewModel()
) {
    val state by vm.state.asUiState()

    LaunchedEffect(key1 = true) {
        vm.sendAction(AnswerAction.FetchAnswer)
        vm.effect.receiveAsFlow().collect { effect ->
            when (effect) {
                is AnswerEffect.GoBack -> navController.popBackStack()
            }
        }
    }

    Scaffold { padding ->
        state.receive<AnswerState> { currentState ->
            AnswerScreenContent(answer = currentState.answer, padding = padding, onAction = { vm.sendAction(it) })
        }
    }
}

@Composable
private fun AnswerScreenContent(
    answer: Answer?,
    padding: PaddingValues,
    onAction: (AnswerAction) -> Unit
) {

    Column(
        modifier = Modifier
            .padding(padding)
            .padding(start = 16.dp, end = 16.dp)
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Button(
                modifier = Modifier.size(40.dp),
                onClick = { onAction(AnswerAction.GoBack) },
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
                textAlign = TextAlign.Center,
                text = stringResource(id = R.string.answer_title),
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp
            )
            Button(
                modifier = Modifier.size(40.dp),
                onClick = { onAction(AnswerAction.OnFavouritesClick(state = if (answer != null) !answer.isFavourites else false)) },
                shape = CircleShape,
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = ColorLightGreen)
            ) {
                Icon(
                    modifier = Modifier
                        .size(30.dp)
                        .padding(bottom = 2.dp),
                    painter = painterResource(id = if (answer?.isFavourites == true) R.drawable.ic_favourites else R.drawable.ic_is_not_favourites),
                    contentDescription = stringResource(id = R.string.decs_button_favourites),
                    tint = Color.Yellow
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = answer?.question.orEmpty(),
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray,
            textAlign = TextAlign.Start,
            fontSize = 22.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(color = ColorLightGreen)
        ) {
            AndroidView(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                factory = { context -> TextView(context) },
                update = { it.text = HtmlCompat.fromHtml(answer?.answer.orEmpty(), HtmlCompat.FROM_HTML_MODE_COMPACT) }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(color = Color.Yellow)
        ) {
            Text(
                text = stringResource(id = R.string.answer_category_title, answer?.categoryName.orEmpty()),
                fontWeight = FontWeight.Normal,
                color = Color.DarkGray,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp)
            )
            Text(
                text = stringResource(id = R.string.answer_subcategory_title, answer?.subcategoryName.orEmpty()),
                fontWeight = FontWeight.Normal,
                color = Color.DarkGray,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(start = 16.dp, top = 8.dp, bottom = 16.dp)
            )
        }
    }
}