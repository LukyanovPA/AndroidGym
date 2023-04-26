package com.pavellukyanov.androidgym

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.pavellukyanov.androidgym.base.ErrorState
import com.pavellukyanov.androidgym.base.ErrorStorage
import com.pavellukyanov.androidgym.helper.Destinations
import com.pavellukyanov.androidgym.ui.theme.AndroidGymTheme
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidGymTheme {
                val errorStorage by inject<ErrorStorage>()
                val navController = rememberNavController()

                LaunchedEffect(key1 = true) {
                    lifecycleScope.launch {
                        errorStorage.onError
                            .receiveAsFlow()
                            .collect { state ->
                                when (state) {
                                    is ErrorState.ShowError -> navController.navigate(Destinations.Error.ERROR)
                                }
                            }
                    }
                }
            }
        }
    }
}