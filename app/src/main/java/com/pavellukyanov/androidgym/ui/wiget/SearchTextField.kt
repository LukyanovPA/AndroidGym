package com.pavellukyanov.androidgym.ui.wiget

import Constants.EMPTY_STRING
import Constants.INT_ONE
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pavellukyanov.androidgym.app.R
import com.pavellukyanov.androidgym.ui.theme.Tesla

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchTextField(
    onSearchClick: (String) -> Unit,
    onClearClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var text by remember { mutableStateOf(EMPTY_STRING) }
    val keyboardController = LocalSoftwareKeyboardController.current

    Box(modifier = modifier) {
        OutlinedTextField(
            value = text,
            onValueChange = {
                text = it
                if (it.isEmpty()) onClearClick() else onSearchClick(it)
            },
            maxLines = INT_ONE,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                    onSearchClick(text)
                }
            ),
            shape = RoundedCornerShape(20.dp),
            textStyle = TextStyle(
                color = Tesla,
                fontFamily = FontFamily(
                    Font(R.font.roboto_slab_regular)
                ),
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
            ),
            placeholder = { Text(text = stringResource(id = R.string.search_placeholder), color = Tesla) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Tesla,
                    modifier = Modifier
                        .clickable {
                            if (text.isNotBlank()) {
                                keyboardController?.hide()
                                onSearchClick(text)
                            }
                        }
                )
            },
            trailingIcon = {
                AnimatedVisibility(
                    visible = text.isNotEmpty(),
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "clear text",
                        tint = Tesla,
                        modifier = Modifier
                            .clickable {
                                text = EMPTY_STRING
                                keyboardController?.hide()
                                onClearClick()
                            }
                    )
                }
            },
            modifier = Modifier
                .background(color = Color.White)
                .fillMaxWidth()
        )
    }
}