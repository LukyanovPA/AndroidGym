package com.pavellukyanov.androidgym.ui.feature.answer

import android.widget.TextView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import androidx.navigation.NavController
import com.pavellukyanov.androidgym.helper.ext.asUiState
import com.pavellukyanov.androidgym.helper.ext.receive
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
                is AnsweEffect.GoBack -> navController.popBackStack()
            }
        }
    }

    Scaffold { padding ->
        state.receive<AnswerState> { currentState ->
            AnswerScreenContent(state = currentState.answer, padding = padding, onAction = { vm.sendAction(it) })
        }
    }
}

@Composable
fun AnswerScreenContent(
    state: Answer?,
    padding: PaddingValues,
    onAction: (AnswerAction) -> Unit
) {

    Column(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
    ) {
        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            factory = { context -> TextView(context) },
            update = { it.text = HtmlCompat.fromHtml(state?.answer.orEmpty(), HtmlCompat.FROM_HTML_MODE_COMPACT) }
        )
    }
}