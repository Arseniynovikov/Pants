package com.example.pants.data.repository

import android.util.Log
import com.example.pants.data.network.ColorApiService
import com.example.pants.domain.model.ColorModel
import com.example.pants.domain.repository.ColorRepository
import com.example.pants.domain.utils.generateRandomColor
import com.example.pants.data.mappers.toColorModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import java.util.Locale

class ColorRepositoryImpl(
    private val apiService: ColorApiService,
) : ColorRepository {

    override suspend fun getRandomColors(count: Int): Result<Set<ColorModel>> = withContext(
        Dispatchers.IO
    ) {
        runCatching {
            val colorList = mutableListOf<ColorModel>()

            while (colorList.size < count) {

                val neededCount = count - colorList.size

                val jobs = (1..neededCount * 2).map {
                    async {
                        val response = apiService.getColor(generateRandomColor())
                        response.toColorModel()
                    }
                }

                jobs.awaitAll()
                    .filter { it.isValid() }
                    .forEach { if (colorList.size < count) colorList.add(it) }

            }
            colorList.toSet()

        }
    }

    private fun ColorModel.isValid(): Boolean {
        val nameLower = name.lowercase(Locale.getDefault())
        val isNameValid = !COMMON_USE_NAMES.any { nameLower.contains(it) }
        val isSaturationValid = saturation > 0.3f
        val isValueValid = value > 0.4f
        return isNameValid && isSaturationValid && isValueValid
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


