package com.pavellukyanov.androidgym.ui.feature.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pavellukyanov.androidgym.app.R
import com.pavellukyanov.androidgym.ui.theme.ColorLightGreen
import entity.questions.Category

@Composable
fun CategoryItemContent(
    category: Category,
    onExpandedClick: (Category) -> Unit
) {
    Box(
        modifier = Modifier
            .padding(start = 4.dp, end = 4.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(color = if (category.isExpand) Color.Yellow else ColorLightGreen)
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .clickable {
                    onExpandedClick(category.copy(isExpand = !category.isExpand))
                }
        ) {
            Text(
                text = category.name,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontSize = 26.sp,
                letterSpacing = 1.sp,
                textAlign = TextAlign.Start
            )
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = category.questionsCount.toString(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.DarkGray
                )
                Text(
                    text = stringResource(R.string.question_count),
                    fontSize = 10.sp,
                    color = Color.DarkGray
                )
            }
        }
    }
}