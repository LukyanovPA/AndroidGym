package com.pavellukyanov.androidgym.ui.wiget

import Constants.EMPTY_STRING
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.pavellukyanov.androidgym.app.R
import com.pavellukyanov.androidgym.ui.theme.AndroidGymTheme

@Composable
fun ErrorDialog(
    errorCode: String = EMPTY_STRING,
    errorText: String = EMPTY_STRING,
    navController: NavController,
    padding: PaddingValues
) {
    val showDialog = remember { mutableStateOf(true) }

    if (showDialog.value) {
        AlertDialog(
            modifier = Modifier.padding(padding),
            onDismissRequest = {
                showDialog.value = false
            },
            title = {
                Text(text = stringResource(R.string.any_screen_error, errorCode))
                    },
            text = { Text(errorText) },
            buttons = {
                Button(
                    modifier = Modifier.padding(8.dp),
                    onClick = {
                        showDialog.value = false
                        navController.popBackStack()
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Yellow)
                ) {
                    Text(text = stringResource(id = R.string.any_screen_ok), fontSize = 22.sp)
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