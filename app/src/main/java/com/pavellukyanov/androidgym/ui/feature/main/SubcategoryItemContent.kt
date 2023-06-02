package com.pavellukyanov.androidgym.ui.feature.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import entity.questions.Subcategory

@Composable
fun SubcategoryItemContent(
    subcategory: Subcategory,
    isExpend: Boolean,
    onQuestionClick: (Int) -> Unit,
    onExpandedClick: (String) -> Unit
) {
    var subcategoriesIsExpanded by remember { mutableStateOf(isExpend) }

    Column(
        modifier = Modifier
            .padding(top = 4.dp, bottom = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(if (subcategoriesIsExpanded) Color.Yellow else Color.Transparent)
                .clickable {
                    subcategoriesIsExpanded = !subcategoriesIsExpanded
                    onExpandedClick(subcategory.name)
                }
        ) {
            Text(
                text = subcategory.name,
                fontWeight = if (subcategoriesIsExpanded) FontWeight.Bold else FontWeight.Normal,
                modifier = Modifier
                    .padding(start = 16.dp)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val height = subcategory.questions.size * 32

            this@Column.AnimatedVisibility(
                visible = subcategoriesIsExpanded,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                LazyColumn(
                    modifier = Modifier
                        .height(height.dp)
                        .padding(start = 16.dp)
                ) {
                    items(
                        items = subcategory.questions,
                        key = { it.id }
                    ) { question ->
                        BoxWithConstraints {
                            QuestionItemContent(
                                question = question,
                                onQuestionClick = onQuestionClick
                            )
                        }
                    }
                }
            }
        }
    }
}