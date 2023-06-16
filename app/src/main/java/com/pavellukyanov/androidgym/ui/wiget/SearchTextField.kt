package com.pavellukyanov.androidgym.ui.wiget

import Constants.EMPTY_STRING
import Constants.INT_ONE
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchTextField(
    searchQuery: String,
    modifier: Modifier = Modifier,
    placeholderText: Int,
    onSearchClick: (String) -> Unit,
    onClearClick: () -> Unit
) {
    var text by remember { mutableStateOf(searchQuery) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    Box(modifier = modifier) {
        OutlinedTextField(
            value = text,
            onValueChange = {
                text = it

                if (text.isEmpty() || text.isBlank()) {
                    focusManager.clearFocus()
                    keyboardController?.hide()
                    onClearClick()
                } else {
                    onSearchClick(it)
                }
            },
            maxLines = INT_ONE,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                    keyboardController?.hide()
                    onSearchClick(text)
                }
            ),
            shape = RoundedCornerShape(40.dp),
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
                    text = stringResource(id = placeholderText),
                    color = Color.LightGray,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            },
            trailingIcon = {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(end = 16.dp)
                ) {
                    this.AnimatedVisibility(
                        visible = text.isNotEmpty(),
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = stringResource(id = R.string.decs_button_clear),
                            tint = Color.Black,
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .clickable {
                                    focusManager.clearFocus()
                                    text = EMPTY_STRING
                                    keyboardController?.hide()
                                    onClearClick()
                                }
                        )
                    }
                }
            },
            modifier = Modifier
                .background(color = Color.White)
                .fillMaxWidth()
                .focusRequester(focusRequester),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Yellow,
                unfocusedBorderColor = Color.Black
            )
        )
    }
}