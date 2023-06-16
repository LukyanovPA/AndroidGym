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
import androidx.compose.material.icons.filled.ArrowForward
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
import entity.questions.Question

@Composable
fun QuestionItemContent(
    question: Question,
    onAction: (Question) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 4.dp, end = 4.dp, top = 6.dp, bottom = 6.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(color = ColorLightGreen)
            .clickable { onAction(question) }
    ) {
        Column(
            modifier = Modifier
                .weight(3f)
                .padding(10.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = question.categoryName,
                    fontSize = 14.sp,
                    color = Color.DarkGray,
                    textAlign = TextAlign.Start
                )
                Icon(
                    modifier = Modifier
                        .padding(start = 2.dp, end = 2.dp)
                        .size(14.dp),
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = stringResource(id = R.string.any_image),
                    tint = Color.DarkGray
                )
                Text(
                    text = question.subcategoryName,
                    fontSize = 14.sp,
                    color = Color.DarkGray,
                    textAlign = TextAlign.Start
                )
                Text(
                    text = stringResource(id = R.string.question_title),
                    color = Color.DarkGray,
                    fontSize = 12.sp,
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .weight(3f)
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
                        .weight(3f)
                        .padding(end = 6.dp),
                    text = question.question,
                    fontSize = 18.sp,
                    color = Color.Black
                )
                Button(
                    modifier = Modifier
                        .size(40.dp),
                    onClick = { onAction(question) },
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


//    Column(
//        modifier = Modifier
//            .padding(top = 4.dp, bottom = 4.dp)
//    ) {
//        Row(
//            modifier = Modifier
//                .clickable { onAction(question) }
//        ) {
//            Text(
//                text = question.question,
//                modifier = Modifier
//                    .padding(start = 16.dp)
//            )
//        }
//    }
}