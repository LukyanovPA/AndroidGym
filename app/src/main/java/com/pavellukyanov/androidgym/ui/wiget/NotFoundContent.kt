package com.pavellukyanov.androidgym.ui.wiget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pavellukyanov.androidgym.app.R

@Composable
fun NotFoundContent() {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 32.dp)
    ) {
        Text(
            text = stringResource(id = R.string.search_not_found),
            textAlign = TextAlign.Center
        )
    }
}