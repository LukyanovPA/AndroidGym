package com.pavellukyanov.androidgym.ui.feature.error

import Constants.EMPTY_STRING
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import com.pavellukyanov.androidgym.ui.wiget.ErrorDialog
import error.ErrorStorage
import error.Errors
import org.koin.androidx.compose.get

@Composable
fun ErrorScreen(
    navController: NavController
) {
    val errorStorage = get<ErrorStorage>()
    val error = errorStorage.error.collectAsState()

    Scaffold { padding ->
        when (val currentError = (error.value as Errors)) {
            is Errors.ExpectedError -> ErrorDialog(
                errorCode = currentError.code.toString(),
                errorText = currentError.message,
                navController = navController,
                padding = padding
            )

            is Errors.OtherError -> ErrorDialog(
                errorText = currentError.error.message ?: EMPTY_STRING,
                navController = navController,
                padding = padding
            )

            is Errors.UndefinedError -> ErrorDialog(
                errorText = currentError.message,
                navController = navController,
                padding = padding
            )
        }
    }
}