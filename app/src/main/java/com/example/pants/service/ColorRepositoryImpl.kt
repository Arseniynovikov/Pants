package com.example.pants.service

import com.example.pants.domain.ColorModel
import com.example.pants.utils.generateRandomColor
import com.example.pants.utils.toColorModel
import java.util.Locale

class ColorRepositoryImpl(
    private val apiService: ColorApiService,
) : ColorRepository {

    override suspend fun getRandomColors(count: Int): Result<Set<ColorModel>> = runCatching {
        val colorList = mutableListOf<ColorModel>()

        while (colorList.size < count) {
            val color = apiService.getColor(generateRandomColor()).toColorModel()
            val doesntContainCommon = color.name.lowercase(Locale.getDefault()) !in COMMON_USE_NAMES
            val isDistinct = color !in colorList
            if (doesntContainCommon && isDistinct) {
                colorList.add(color)
            }
        }
        colorList.toSet()
    }

    private companion object {
        val COMMON_USE_NAMES = setOf(
            "beige",
            "black",
            "blue violet",
            "blue",
            "brown",
            "crimson",
            "cyan",
            "gold",
            "gray",
            "green",
            "indigo",
            "khaki",
            "lavender",
            "lime green",
            "magenta",
            "maroon",
            "navy blue",
            "olive",
            "orange",
            "pink",
            "plum",
            "purple",
            "red",
            "salmon",
            "silver",
            "sky blue",
            "teal",
            "violet",
            "white",
            "yellow",
        )
    }
}
