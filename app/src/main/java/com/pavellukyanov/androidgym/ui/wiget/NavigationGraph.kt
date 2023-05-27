package com.pavellukyanov.androidgym.ui.wiget

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pavellukyanov.androidgym.helper.Destinations
import com.pavellukyanov.androidgym.ui.feature.answer.AnswerScreen
import com.pavellukyanov.androidgym.ui.feature.error.ErrorScreen
import com.pavellukyanov.androidgym.ui.feature.main.MainScreen

@Composable
fun NavigationGraph(
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    NavHost(navController = navController, startDestination = Destinations.Categories.CATEGORIES) {
        composable(route = Destinations.Error.ERROR) {
            ErrorScreen(navController = navController)
        }
        composable(route = Destinations.Categories.CATEGORIES) {
            MainScreen(navController = navController)
        }
        composable(route = Destinations.Answer.ANSWER) {
            AnswerScreen(navController = navController)
        }
    }
}