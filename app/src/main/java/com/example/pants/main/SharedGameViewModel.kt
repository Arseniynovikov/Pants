package com.example.pants.main

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pants.domain.ColorModel
import com.example.pants.utils.CheckBoardOrderUseCase
import com.example.pants.utils.GetColorBoardUseCase
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SharedGameViewModel(
    private val getColorBoardUseCase: GetColorBoardUseCase,
    private val checkBoardOrderUseCase: CheckBoardOrderUseCase
) : ViewModel() {

    private val _colorBoard = MutableStateFlow(EMPTY_BOARD)
    val colorBoard: StateFlow<List<ColorModel>> = _colorBoard.asStateFlow()

    private val _currentColorName = MutableStateFlow<String?>(null)
    val currentColorName: StateFlow<String?> = _currentColorName.asStateFlow()

    private val _selectedColor = MutableStateFlow(Color.Black)
    val selectedColor: StateFlow<Color> = _selectedColor.asStateFlow()

    private val _errorMessage = MutableSharedFlow<String>()
    val errorMessage: SharedFlow<String> = _errorMessage.asSharedFlow()

    init {
        initColorBoard()
    }

    fun setColorModelByName(name: String) {
        _colorBoard.value.find { it.name == name }?.let { colorModel ->
            _currentColorName.value = colorModel.name
            updateColorSettings(colorModel.guessHue ?: 0f)
        }
    }

    fun saveColor(newHue: Float) {
        viewModelScope.launch {
            if (_colorBoard.value.isEmpty()) return@launch
            val updatedColors = _colorBoard.value.map {
                if (it.name == currentColorName.value) it.updateHue(newHue) else it
            }
            _colorBoard.value = updatedColors
        }
    }

    fun updateColorSettings(hue: Float) {
        _selectedColor.value = Color.hsv(hue, 1f, 1f)
        Log.e("debug", "bonjour")
        _colorBoard.value = _colorBoard.value.map { color ->
            if(Color.hsv(color.guessHue ?: 0f, color.saturation, color.value) != _selectedColor.value) {
                color.updateHue(color.guessHue)
            } else {
                color.updateHue(hue)
            }
        }
    }

    fun checkColorOrder(board: List<ColorModel>): List<ColorModel>? {
        when {
            board.isEmpty() -> {
                initColorBoard()
                return board
            }

            checkBoardOrderUseCase(board) -> {
                initColorBoard()
                return null
            }

            else -> {
                return board.sortedBy { it.realHue }
            }
        }
    }

    private fun initColorBoard() {
        viewModelScope.launch {
            getColorBoardUseCase(BOARD_SIZE).fold(
                onSuccess = { _colorBoard.value = it.toPersistentList() },
                onFailure = { _errorMessage.emit(it.message.orEmpty()) }
            )
        }
    }

    private companion object {
        const val BOARD_SIZE = 5
        val EMPTY_BOARD = emptyList<ColorModel>()
    }
}
