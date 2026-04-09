package com.example.pants.utils.components

import android.graphics.Bitmap
import android.graphics.Color.HSVToColor
import android.graphics.Paint
import android.graphics.RectF
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toRect
import com.example.pants.R
import com.example.pants.utils.hue

private const val PICKER_WIDTH = 300

@Composable
fun HuePicker(
    modifier: Modifier = Modifier,
    hue: Float,
    animatedColor: Color,
    onHueChange: (Float) -> Unit,
) {
    onHueChange(hue)
    val colorHue = animatedColor.hue
    Box(
        modifier = modifier
            .height(40.dp)
            .width(PICKER_WIDTH.dp)
    ) {
        Canvas(
            modifier = Modifier
                .matchParentSize()
                .clip(CircleShape)
                .pointerInput(Unit) {
                    detectTapGestures { offset ->
                        val newHue = (offset.x / size.width) * 360f
                        onHueChange(newHue)
                    }
                }
        ) {
            drawHueBitmap()
        }
        var cursorWidth by remember { mutableIntStateOf(0) }
        Image(
            painter = painterResource(id = R.drawable.rectangle_cursor),
            contentDescription = null,
            modifier = Modifier
                .height(40.dp)
                .onSizeChanged { cursorWidth = it.width }
                .then(
                    with(LocalDensity.current) {
                        Modifier.offset(x = ((hue / 360f) * (PICKER_WIDTH - cursorWidth.toDp().value)).dp)
                    }
                )
        )
    }
}

private fun DrawScope.drawHueBitmap() {
    val bitmap =
        Bitmap.createBitmap(size.width.toInt(), size.height.toInt(), Bitmap.Config.ARGB_8888)
    val hueCanvas = android.graphics.Canvas(bitmap)
    val huePanel = RectF(0f, 0f, bitmap.width.toFloat(), bitmap.height.toFloat())
    val hueColors = IntArray(bitmap.width)
    var hue = 0f
    for (i in hueColors.indices) {
        hueColors[i] = HSVToColor(floatArrayOf(hue, 1f, 1f))
        hue += 360f / hueColors.size
    }
    val linePaint = Paint()
    linePaint.strokeWidth = 0F
    for (i in hueColors.indices) {
        linePaint.color = hueColors[i]
        hueCanvas.drawLine(i.toFloat(), 0F, i.toFloat(), huePanel.bottom, linePaint)
    }
    drawIntoCanvas {
        it.nativeCanvas.drawBitmap(bitmap, null, huePanel.toRect(), null)
    }
}
