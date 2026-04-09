package com.example.pants.utils

import com.example.pants.domain.ColorModel
import com.example.pants.service.ColorRepository

class GetColorBoardUseCase(
    private val colorRepository: ColorRepository,
) {

    suspend operator fun invoke(colorCount: Int): Result<Set<ColorModel>> =
        colorRepository.getRandomColors(colorCount)
}
