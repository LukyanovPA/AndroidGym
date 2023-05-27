package com.pavellukyanov.androidgym.ui.wiget

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun BaseImage(
    url: Any?,
    size: Dp,
    isCircle: Boolean = false,
    modifier: Modifier = Modifier
) {
    GlideImage(
        model = url,
        contentDescription = "Image",
        modifier = Modifier
            .clip(RoundedCornerShape(2.dp))
            .size(size),
        contentScale = ContentScale.Crop
    ) {
        if (isCircle) it.circleCrop() else it.centerCrop()
    }
}