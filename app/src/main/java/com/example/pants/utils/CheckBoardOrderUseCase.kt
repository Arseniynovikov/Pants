package com.example.pants.utils

import com.example.pants.domain.ColorModel

class CheckBoardOrderUseCase {

    operator fun invoke(board: List<ColorModel>): Boolean {
        return board.zipWithNext { a, b -> a.realHue <= b.realHue }.all { it }
    }
}
