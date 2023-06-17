package com.pavellukyanov.androidgym.ui.wiget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowCircleRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pavellukyanov.androidgym.app.R
import com.pavellukyanov.androidgym.ui.theme.ColorLightGreen
import entity.models.Subcategory

@Composable
fun SubcategoryItemContent(
    subcategory: Subcategory,
    onAction: (Subcategory) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 4.dp, end = 4.dp, top = 6.dp, bottom = 6.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(color = ColorLightGreen)
            .clickable { onAction(subcategory) }
    ) {
        Column(
            modifier = Modifier
                .weight(3f)
                .padding(10.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Top,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier
                        .weight(3f),
                    text = subcategory.categoryName,
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )
                Text(
                    text = stringResource(id = R.string.subcategory_title),
                    color = Color.DarkGray,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .alpha(0.6f)
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier
                        .weight(3f),
                    text = subcategory.name,
                    fontSize = 18.sp,
                    color = Color.Black
                )
                Button(
                    modifier = Modifier
                        .size(40.dp),
                    onClick = { onAction(subcategory) },
                    shape = CircleShape,
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Yellow)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowCircleRight,
                        contentDescription = stringResource(id = R.string.decs_button_search),
                        modifier = Modifier
                            .alpha(0.7f)
                            .rotate(-30f)
                    )
                }
            }
        }
    }
}