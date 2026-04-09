package com.example.pants.service

import com.example.pants.domain.ColorModel

interface ColorRepository {

    suspend fun getRandomColors(count: Int): Result<Set<ColorModel>>
}
