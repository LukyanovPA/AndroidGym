package com.pavellukyanov.androidgym.ui.feature.answer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.FormatBold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mohamedrejeb.richeditor.model.RichTextStyle
import com.mohamedrejeb.richeditor.model.RichTextValue
import com.mohamedrejeb.richeditor.ui.material.RichText
import com.mohamedrejeb.richeditor.ui.material.RichTextEditor
import com.pavellukyanov.androidgym.helper.ext.asUiState
import com.pavellukyanov.androidgym.helper.ext.receive
import entity.answer.Answer
import kotlinx.coroutines.flow.receiveAsFlow
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber

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
    var richTextValue by remember { mutableStateOf(RichTextValue(/*text = state?.answer.orEmpty()*/)) }

    Column(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
    ) {
        Button(onClick = { onAction(AnswerAction.GoBack) }) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close"
            )
        }
        IconButton(
            onClick = {
                richTextValue = richTextValue.toggleStyle(RichTextStyle.Bold)
            }
        ) {
            Icon(
                imageVector = Icons.Outlined.FormatBold,
                contentDescription = "Bold"
            )
        }

        RichTextEditor(
            value = richTextValue,
            onValueChange = {
                richTextValue = it
                Timber.d("Smotrim ${it}")
            },
        )
        Spacer(modifier = Modifier.height(16.dp))
        RichText(value = richTextValue)

//        val source = state?.answer.orEmpty()
//        val rules = MarkdownRules.toList() /*+ HtmlRules.toList()*/
//        val parser = SimpleMarkupParser()
//        val content = parser
//            .parse(source, rules)
//            .render()
//            .toAnnotatedString()
//
//        Text(text = content)

//        AndroidView(
//            modifier = Modifier.fillMaxSize(),
//            factory = { context -> TextView(context) },
//            update = { it.text = HtmlCompat.fromHtml(state?.answer.orEmpty(), HtmlCompat.FROM_HTML_MODE_COMPACT) }
//        )

//        Text(
//            text = stringResource(id = R.string.html_text, state?.answer.orEmpty()),
//            fontWeight = FontWeight.Bold,
//            color = Color.White,
//            fontSize = 20.sp
//        )
    }
}