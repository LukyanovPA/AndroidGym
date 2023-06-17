package com.pavellukyanov.androidgym.ui.wiget

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pavellukyanov.androidgym.app.BuildConfig
import com.pavellukyanov.androidgym.app.R
import com.pavellukyanov.androidgym.ui.theme.ColorLightGreen

@Composable
fun MenuContent(
    mainVisibility: Boolean = true,
    onAction: (MenuActions) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = stringResource(id = R.string.app_name_with_version, BuildConfig.VERSION_NAME),
            color = Color.Black,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(24.dp))
        AnimatedVisibility(
            visible = mainVisibility,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
                    .wrapContentHeight()
            ) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onAction(MenuActions.Main) }
                ) {
                    Icon(
                        modifier = Modifier.size(18.dp),
                        imageVector = Icons.Default.Home,
                        contentDescription = stringResource(id = R.string.any_image)
                    )
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = stringResource(id = R.string.main_screen_title),
                        color = Color.Black,
                        fontSize = 18.sp
                    )
                }
                Divider(
                    modifier = Modifier.padding(6.dp),
                    color = ColorLightGreen
                )
            }
        }
        Column(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
                .wrapContentHeight()
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onAction(MenuActions.Favourites) }
            ) {
                Icon(
                    modifier = Modifier.size(18.dp),
                    imageVector = Icons.Default.Favorite,
                    contentDescription = stringResource(id = R.string.any_image)
                )
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = stringResource(id = R.string.favourites_title),
                    color = Color.Black,
                    fontSize = 18.sp
                )
            }
            Divider(
                modifier = Modifier.padding(6.dp),
                color = ColorLightGreen
            )
        }
    }
}

sealed class MenuActions {
    object Main : MenuActions()
    object Favourites : MenuActions()
}