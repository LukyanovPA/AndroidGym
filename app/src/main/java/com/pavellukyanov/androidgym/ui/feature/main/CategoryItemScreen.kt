package com.pavellukyanov.androidgym.ui.feature.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import entity.questions.Category

@Composable
fun CategoryItemContent(
    category: Category,
    onExpandedClick: (Category) -> Unit
) {
    var categoriesIsExpanded by remember { mutableStateOf(category.isExpand) }

    Box(
        modifier = Modifier
            .padding(start = 8.dp, end = 8.dp)
            .fillMaxSize()
            .clip(RoundedCornerShape(20.dp))
            .background(color = if (categoriesIsExpanded) Color.Yellow else Color.Green)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .clickable {
                    categoriesIsExpanded = !categoriesIsExpanded
                    onExpandedClick(category.copy(isExpand = categoriesIsExpanded))
                }
        ) {
            Text(
                text = category.name,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontSize = 24.sp,
                letterSpacing = 1.sp,
                modifier = Modifier
                    .padding(start = 16.dp),
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
                    fontSize = 18.sp,
                    color = Color.DarkGray
                )
                Text(
                    text = stringResource(R.string.question_count),
                    fontSize = 12.sp,
                    color = Color.DarkGray
                )
            }
        }
    }
}