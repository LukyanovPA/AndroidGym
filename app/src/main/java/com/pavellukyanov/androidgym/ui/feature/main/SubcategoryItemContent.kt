package com.pavellukyanov.androidgym.ui.feature.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.unit.dp
import entity.questions.Subcategory

@Composable
fun SubcategoryItemContent(
    subcategory: Subcategory,
    onQuestionClick: (Int) -> Unit
) {
    var subcategoriesIsExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .clickable { subcategoriesIsExpanded = !subcategoriesIsExpanded }
        ) {
            Text(
                text = subcategory.name,
                modifier = Modifier
                    .padding(start = 16.dp)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            this@Column.AnimatedVisibility(
                visible = subcategoriesIsExpanded,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                val height = subcategory.questions.size * 35

                LazyColumn(modifier = Modifier.height(height.dp)) {
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