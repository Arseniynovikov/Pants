package com.example.pants.utils

import kotlin.random.Random

private const val MIN_SATURATION = 0.3
private const val MIN_VALUE = 0.4

fun generateRandomColor(): String {
    val hue = Random.nextDouble()
    val saturation = Random.nextDouble(MIN_SATURATION, 1.0)
    val value = Random.nextDouble(MIN_VALUE, 1.0)
    return "$hue,$saturation,$value"
}
