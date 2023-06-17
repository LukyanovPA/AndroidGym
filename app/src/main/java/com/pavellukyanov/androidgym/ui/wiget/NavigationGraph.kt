package com.pavellukyanov.androidgym.ui.wiget

import Constants.EMPTY_STRING
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.pavellukyanov.androidgym.helper.AnalyticsClient
import com.pavellukyanov.androidgym.helper.Destinations
import com.pavellukyanov.androidgym.ui.feature.answer.AnswerScreen
import com.pavellukyanov.androidgym.ui.feature.category.CategoryScreen
import com.pavellukyanov.androidgym.ui.feature.error.ErrorScreen
import com.pavellukyanov.androidgym.ui.feature.favourites.FavouritesScreen
import com.pavellukyanov.androidgym.ui.feature.main.MainScreen
import com.pavellukyanov.androidgym.ui.feature.subcategory.SubcategoryScreen

@Composable
fun NavigationGraph(
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    NavHost(
        modifier = Modifier.padding(paddingValues),
        navController = navController,
        startDestination = Destinations.Main.MAIN
    ) {
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
        composable(route = Destinations.Favourites.FAVOURITES) {
            AnalyticsClient.trackScreen(screen = AnalyticsClient.ScreenNames.FAVOURITES)
            FavouritesScreen(navController = navController)
        }
        composable(
            route = Destinations.Category.CATEGORY_ROUTE,
            arguments = listOf(
                navArgument(name = Destinations.Arguments.CATEGORY_ARG) {
                    type = NavType.StringType
                    defaultValue = EMPTY_STRING
                }
            )
        ) { backStackEntry ->
            val categoryName = backStackEntry.arguments?.getString(Destinations.Arguments.CATEGORY_ARG) ?: EMPTY_STRING
            AnalyticsClient.trackScreen(screen = AnalyticsClient.ScreenNames.CATEGORY)
            CategoryScreen(categoryName = categoryName, navController = navController)
        }
        composable(
            route = Destinations.Subcategory.SUBCATEGORY_ROUTE,
            arguments = listOf(
                navArgument(name = Destinations.Arguments.SUBCATEGORY_ARG) {
                    type = NavType.StringType
                    defaultValue = EMPTY_STRING
                }
            )
        ) { backStackEntry ->
            val subcategoryName = backStackEntry.arguments?.getString(Destinations.Arguments.SUBCATEGORY_ARG) ?: EMPTY_STRING
            AnalyticsClient.trackScreen(screen = AnalyticsClient.ScreenNames.SUBCATEGORY)
            SubcategoryScreen(subcategoryName = subcategoryName, navController = navController)
        }
    }
}