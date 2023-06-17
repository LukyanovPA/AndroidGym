package com.pavellukyanov.androidgym.ui.wiget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pavellukyanov.androidgym.app.BuildConfig
import com.pavellukyanov.androidgym.app.R
import com.pavellukyanov.androidgym.base.Action
import com.pavellukyanov.androidgym.ui.theme.ColorLightGreen

@Composable
fun MenuContent(
    favouriteAction: Action,
    onAction: (Action) -> Unit
) {
    Text(
        modifier = Modifier.padding(16.dp),
        text = stringResource(id = R.string.app_name_with_version, BuildConfig.VERSION_NAME),
        color = Color.Black,
        fontSize = 14.sp
    )
    Box(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(color = ColorLightGreen)
            .clickable { onAction(favouriteAction) }
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = stringResource(id = R.string.favourites_title),
            color = Color.Black,
            fontSize = 18.sp
        )
    }
}