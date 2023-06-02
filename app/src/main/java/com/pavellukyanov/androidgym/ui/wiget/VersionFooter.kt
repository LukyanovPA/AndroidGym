package com.pavellukyanov.androidgym.ui.wiget

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.pavellukyanov.androidgym.app.BuildConfig

@Composable
fun VersionFooter() {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "version " + BuildConfig.VERSION_NAME,
            textAlign = TextAlign.Center,
            color = Color.LightGray,
            fontSize = 12.sp
        )
    }
}