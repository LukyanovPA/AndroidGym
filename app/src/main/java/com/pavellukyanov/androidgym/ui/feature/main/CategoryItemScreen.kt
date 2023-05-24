package com.pavellukyanov.androidgym.ui.feature.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pavellukyanov.androidgym.app.R
import com.pavellukyanov.androidgym.ui.theme.Tesla
import com.pavellukyanov.androidgym.ui.wiget.BaseImage
import entity.questions.Category

@Composable
fun CategoryItemContent(
    category: Category,
    expendMap: HashMap<Int, Boolean>,
    onQuestionClick: (Int) -> Unit,
    onExpandedClick: (Int) -> Unit
) {
    var categoriesIsExpanded by remember { mutableStateOf(expendMap[category.id] ?: false) }

    Column(
        modifier = Modifier
            .padding(top = 4.dp, bottom = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    categoriesIsExpanded = !categoriesIsExpanded
                    onExpandedClick(category.id)
                },
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            BaseImage(
                url = when (category.id) {
                    1 -> R.drawable.ic_java
                    2 -> R.drawable.ic_kotlin
                    3 -> R.drawable.ic_android
                    else -> R.drawable.ic_other
                },
                size = 30.dp,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = category.name,
                fontWeight = if (categoriesIsExpanded) FontWeight.Bold else FontWeight.Normal,
                color = if (categoriesIsExpanded) Tesla else Color.Black,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(3f),
                textAlign = TextAlign.Start
            )
            Text(
                text = stringResource(R.string.question_count, category.questionsCount.toString()),
                fontSize = 12.sp,
                textAlign = TextAlign.End,
                modifier = Modifier.weight(2f)
            )
        }

        AnimatedVisibility(
            visible = categoriesIsExpanded,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                val height = category.subcategories.size * 30

                LazyColumn(
                    modifier = Modifier
                        .height(height.dp)
                        .padding(start = 48.dp)
                ) {
                    items(
                        items = category.subcategories,
                        key = { it.id }
                    ) { subcategory ->
                        BoxWithConstraints {
                            SubcategoryItemContent(
                                subcategory = subcategory,
                                isExpend = expendMap[subcategory.id] ?: false,
                                onQuestionClick = onQuestionClick,
                                onExpandedClick = onExpandedClick
                            )
                        }
                    }
                }
            }
        }
    }
}