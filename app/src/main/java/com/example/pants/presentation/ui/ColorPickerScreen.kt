package com.example.pants.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pants.R
import com.example.pants.domain.ColorModel
import com.example.pants.main.SharedGameViewModel
import com.example.pants.utils.components.PickerContent
import com.example.pants.utils.components.SaveButton
import com.example.pants.utils.hue


@Composable
fun ColorPickerScreen(viewModel: SharedGameViewModel, onSave: () -> Unit) {
    val selectedColor by viewModel.selectedColor.collectAsStateWithLifecycle()
    val currentColorName by viewModel.currentColorName.collectAsStateWithLifecycle()
    val colorBoard by viewModel.colorBoard.collectAsStateWithLifecycle()
    ColorPicker(
        selectedColor = selectedColor,
        colorName = currentColorName,
        onSaveColor = {
            viewModel.saveColor(selectedColor.hue)
            onSave()
        },
        onUpdateColorSettings = viewModel::updateColorSettings,
        colors = colorBoard,
    )
}

@Composable
private fun ColorPicker(
    selectedColor: Color,
    colorName: String?,
    onUpdateColorSettings: (Float) -> Unit,
    onSaveColor: () -> Unit,
    colors: List<ColorModel>,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.main_color)),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Header(colorName.orEmpty())

        PickerContent(
            selectedColor = selectedColor,
            onHueChange = onUpdateColorSettings,
            colors = colors,
        )

        SaveButton(
            onClick = onSaveColor,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )
    }
}

@Composable
private fun Header(name: String) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 48.dp, start = 16.dp, end = 16.dp),
        text = name,
        style = TextStyle(
            color = Color.Black,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )
    )
}


@Preview(showBackground = true)
@Composable
private fun ColorPickerPreview() {
    val model = ColorModel(
        name = "Color of your pants on fire on saturday morning",
        realHue = 227.0f,
        saturation = 1.0f,
        value = 1.0f,
        guessHue = null,
    )
    val selected = Color.hsv(model.realHue, model.saturation, model.value)
    ColorPicker(selectedColor = selected,
        colorName = model.name,
        onUpdateColorSettings = { _ -> },
        onSaveColor = {},
        colors = List(5) { model },
    )
}
