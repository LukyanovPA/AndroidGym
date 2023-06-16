package com.pavellukyanov.androidgym.ui.feature.answer

import Constants.EMPTY_STRING
import android.widget.TextView
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
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
import kotlinx.coroutines.flow.receiveAsFlow
import org.koin.androidx.compose.koinViewModel

private const val SEND_COMMENT_COMPLETE = "Ваш комментарий отправлен"

@Composable
fun AnswerScreen(
    navController: NavController,
    reducer: AnswerReducer = koinViewModel()
) {
    val state by reducer.state.asUiState()
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        reducer.sendAction(AnswerAction.FetchAnswer)
        reducer.effect.receiveAsFlow().collect { effect ->
            when (effect) {
                is AnswerEffect.GoBack -> navController.popBackStack()
                is AnswerEffect.ShowSendCommentCompleteNotify -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = SEND_COMMENT_COMPLETE,
                    )
                }
            }
        }
    }

    Scaffold(scaffoldState = scaffoldState) { padding ->
        state.receive<AnswerState> { currentState ->
            AnswerScreenContent(state = currentState, padding = padding, onAction = { reducer.sendAction(it) })
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun AnswerScreenContent(
    state: AnswerState,
    padding: PaddingValues,
    onAction: (AnswerAction) -> Unit
) {
    var commentIsOpen by remember { mutableStateOf(false) }
    var comment by remember { mutableStateOf(EMPTY_STRING) }
    val keyboardController = LocalSoftwareKeyboardController.current

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
                onClick = { onAction(AnswerAction.OnFavouritesClick(state = if (state.answer != null) !state.answer.isFavourites else false)) },
                shape = CircleShape,
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Yellow)
            ) {
                Icon(
                    modifier = Modifier
                        .size(25.dp)
                        .padding(bottom = 2.dp)
                        .alpha(0.6f),
                    painter = painterResource(id = if (state.answer?.isFavourites == true) R.drawable.ic_favourites else R.drawable.ic_is_not_favourites),
                    contentDescription = stringResource(id = R.string.decs_button_favourites)
                )
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = state.answer?.question.orEmpty(),
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray,
            textAlign = TextAlign.Center,
            fontSize = 22.sp,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(color = Color.Yellow)
        ) {
            AndroidView(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                factory = { context -> TextView(context) },
                update = { it.text = HtmlCompat.fromHtml(state.answer?.answer.orEmpty(), HtmlCompat.FROM_HTML_MODE_COMPACT) }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(color = ColorLightGreen)
        ) {
            Text(
                text = stringResource(id = R.string.answer_category_title, state.answer?.categoryName.orEmpty()),
                fontWeight = FontWeight.Normal,
                color = Color.DarkGray,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp)
            )
            Text(
                text = stringResource(id = R.string.answer_subcategory_title, state.answer?.subcategoryName.orEmpty()),
                fontWeight = FontWeight.Normal,
                color = Color.DarkGray,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(start = 16.dp, top = 8.dp, bottom = 16.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            textAlign = TextAlign.End,
            text = stringResource(id = R.string.answer_comment_title),
            color = Color.Blue,
            fontWeight = if (!commentIsOpen) FontWeight.Bold else FontWeight.Normal,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { commentIsOpen = !commentIsOpen }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            this@Column.AnimatedVisibility(
                visible = commentIsOpen,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = comment,
                        onValueChange = {
                            comment = it
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done,
                            capitalization = KeyboardCapitalization.Sentences
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                keyboardController?.hide()
                            }
                        ),
                        shape = RoundedCornerShape(16.dp),
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontFamily = FontFamily(
                                Font(R.font.ubuntu_regular)
                            ),
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp
                        ),
                        placeholder = {
                            Text(
                                text = stringResource(id = R.string.answer_comment_placeholder),
                                color = Color.LightGray
                            )
                        },
                        modifier = Modifier
                            .background(color = Color.White)
                            .fillMaxWidth()
                            .height(150.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.Black,
                            unfocusedBorderColor = Color.Black
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = {
                            onAction(AnswerAction.OnCreateFeedbackClick(comment = comment))
                            commentIsOpen = !commentIsOpen
                            comment = EMPTY_STRING
                        },
                        enabled = comment.isNotEmpty(),
                        shape = RoundedCornerShape(16.dp),
                        contentPadding = PaddingValues(0.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = if (comment.isNotEmpty()) Color.Yellow else ColorLightGreen)
                    ) {
                        Text(
                            modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                            text = stringResource(id = R.string.send_comment_button_title),
                            fontWeight = if (comment.isNotEmpty()) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }
            }
        }
    }
}