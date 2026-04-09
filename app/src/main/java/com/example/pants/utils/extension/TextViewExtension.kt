package com.example.pants.utils.extension

import android.text.Html
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.pants.R

fun TextView.setColoredText(text: String) {
    val mainColor = ContextCompat.getColor(this.context, R.color.main_color)
    val secondColor = ContextCompat.getColor(this.context, R.color.secondary)
    val htmlText = text
        .replace("#mainColor", String.format("#%06X", 0xFFFFFF and mainColor))
        .replace("#secondColor", String.format("#%06X", 0xFFFFFF and secondColor))
    this.text = Html.fromHtml(htmlText, Html.FROM_HTML_MODE_LEGACY)
}
