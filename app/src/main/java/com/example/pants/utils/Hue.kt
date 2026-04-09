package com.example.pants.utils

import androidx.compose.ui.graphics.Color

val Color.hue: Float
    get() {
        val hsv = FloatArray(3)
        android.graphics.Color.RGBToHSV((red * 255).toInt(), (green * 255).toInt(), (blue * 255).toInt(), hsv)
        return hsv[0]
    }
