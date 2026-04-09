package com.example.pants.utils.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pants.domain.ColorModel
import com.example.pants.utils.ui.animatedGradientTransition
import com.example.pants.utils.hue

@Composable
internal fun PickerContent(
    selectedColor: Color,
    onHueChange: (Float) -> Unit,
    colors: List<ColorModel>,
) {
    val (animatedColor, animatedGradient) = animatedGradientTransition(selectedColor)

    Column(
        modifier = Modifier.width(IntrinsicSize.Min),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(48.dp),
    ) {
        Previews(
            modifier = Modifier.fillMaxWidth(),
            colors = colors,
            selectedColor = selectedColor,
            animatedColor = animatedColor,
            animatedGradient = animatedGradient,
        )
        HuePicker(hue = selectedColor.hue, animatedColor = animatedColor, onHueChange = onHueChange)
    }
}

@Preview
@Composable
fun PickerContentPreview() {
    val model = ColorModel(
        name = "Color of your pants on fire on saturday morning",
        realHue = 227.0f,
        saturation = 1.0f,
        value = 1.0f,
        guessHue = null,
    )

    PickerContent(
        selectedColor = Color.Yellow,
        onHueChange = { _ -> },
        colors = List(5) { model }
    )
}
