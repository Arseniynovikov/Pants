package com.example.pants.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
internal fun ColorPreview(
    modifier: Modifier = Modifier,
    color: Color,
    animatedColor: Color,
    animatedGradient: Brush,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ColorDetails(Modifier.weight(1f), animatedColor)
        ColorBox(animatedGradient)
    }
}

@Composable
private fun ColorBox(animatedGradient: Brush) {
    var width by remember { mutableIntStateOf(0) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(animatedGradient)
            .onSizeChanged { width = it.width }
            .then(
                with(LocalDensity.current) {
                    Modifier.height(width.toDp())
                }
            )
    )
}

@Preview
@Composable
fun ColorPreviewPreview(){
    ColorPreview(
        color = Color.Yellow,
        animatedColor = Color.Yellow,
        animatedGradient = Brush.linearGradient(0f to Color.Yellow,  0.5f to Color.Green,  1f to Color.Blue),
    )
}
