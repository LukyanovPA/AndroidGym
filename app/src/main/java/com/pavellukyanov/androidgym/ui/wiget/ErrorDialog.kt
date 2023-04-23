package com.pavellukyanov.androidgym.ui.wiget

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.pavellukyanov.androidgym.ui.theme.AndroidGymTheme
import helper.Constants.EMPTY_STRING

@Composable
fun ErrorDialog(
    errorCode: String = EMPTY_STRING,
    errorText: String = EMPTY_STRING,
    navController: NavController,
    padding: PaddingValues
) {
    val showDialog = remember {
        mutableStateOf(true)
    }

    if (showDialog.value) {
        AlertDialog(
            modifier = Modifier.padding(padding),
            onDismissRequest = {
                showDialog.value = false
            },
            title = { Text(text = "Ошибка $errorCode") },
            text = { Text(errorText) },
            buttons = {
                Button(
                    onClick = {
                        showDialog.value = false
                        navController.popBackStack()
                    }
                ) {
                    Text(text = "OK", fontSize = 22.sp)
                }
            }
        )
    }
}

@Preview
@Composable
fun ErrorDialogPreview() {
    AndroidGymTheme {
        ErrorDialog(
            errorText = "sdfklgkldsfjs ksjfkdsfsdjf ksdjfsdfjsd",
            navController = rememberNavController(),
            padding = PaddingValues()
        )
    }
}