package com.pavellukyanov.androidgym.ui.wiget

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pavellukyanov.androidgym.helper.AnalyticsClient
import com.pavellukyanov.androidgym.helper.Destinations
import com.pavellukyanov.androidgym.ui.feature.answer.AnswerScreen
import com.pavellukyanov.androidgym.ui.feature.error.ErrorScreen
import com.pavellukyanov.androidgym.ui.feature.main.MainScreen

@Composable
fun NavigationGraph(
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    NavHost(navController = navController, startDestination = Destinations.Main.MAIN) {
        composable(route = Destinations.Error.ERROR) {
            ErrorScreen(navController = navController)
        }
        composable(route = Destinations.Main.MAIN) {
            AnalyticsClient.trackScreen(screen = AnalyticsClient.ScreenNames.MAIN)
            MainScreen(navController = navController)
        }
        composable(route = Destinations.Answer.ANSWER) {
            AnalyticsClient.trackScreen(screen = AnalyticsClient.ScreenNames.ANSWER)
            AnswerScreen(navController = navController)
        }
    }
}