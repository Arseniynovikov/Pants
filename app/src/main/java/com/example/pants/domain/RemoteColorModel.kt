package com.example.pants.domain

data class ColorResponse(
    val name: Name,
    val hsv: Hsv
)

data class Name(val value: String)

data class Hsv(val fraction: Fraction) {
    data class Fraction(val h: Float, val s: Float, val v: Float)
}
