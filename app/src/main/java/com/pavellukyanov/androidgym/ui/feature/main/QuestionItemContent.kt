package com.pavellukyanov.androidgym.ui.feature.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import entity.questions.Question

@Composable
fun QuestionItemContent(
    question: Question,
    onQuestionClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(top = 4.dp, bottom = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .clickable { onQuestionClick(question.id) }
        ) {
            Text(
                text = question.question,
                modifier = Modifier
                    .padding(start = 16.dp)
            )
        }
    }
}