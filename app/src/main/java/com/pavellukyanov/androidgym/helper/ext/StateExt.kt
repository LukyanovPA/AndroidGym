package com.pavellukyanov.androidgym.helper.ext

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.Flow
import com.pavellukyanov.androidgym.base.State as UiState

@Composable
internal fun Flow<UiState>.asUiState(): State<UiState> =
    this.collectAsStateWithLifecycle(initialValue = UiState())

@Composable
internal inline fun <reified STATE : UiState> UiState.receive(currentState: (STATE) -> Unit) {
    when (this) {
        is STATE -> currentState(this)
    }
}