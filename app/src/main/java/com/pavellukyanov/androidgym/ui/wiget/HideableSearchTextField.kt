package com.pavellukyanov.androidgym.ui.wiget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pavellukyanov.androidgym.app.R
import com.pavellukyanov.androidgym.ui.theme.Tesla

@Composable
fun HideableSearchTextField(
    text: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        OutlinedTextField(
            value = text,
            onValueChange = onTextChange,
            shape = RoundedCornerShape(40.dp),
            textStyle = TextStyle(
                color = Tesla,
                fontFamily = FontFamily(
                    Font(R.font.roboto_slab_regular)
                ),
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
            ),
            placeholder = { Text(text = stringResource(id = R.string.search_placeholder)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Tesla
                )
            },
            modifier = Modifier
                .background(color = Color.White)
                .fillMaxWidth()
        )
    }
}

@Preview
@Composable
fun HideableSearchTextFieldPreview() {
    HideableSearchTextField(
        text = "tt",
        onTextChange = {},
        modifier = Modifier.background(color = Color.White)
    )
}