package com.example.wordcut.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.wordcut.ui.models.GameUiState
import com.example.wordcut.ui.models.GameRowModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GameViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    fun resetGame() {
        val word = pickRandomStartWord().uppercase()
        val letters = word.toList()
        val counts = letters.groupingBy { it }.eachCount()
        _uiState.value = GameUiState(
            word = word,
            currentRowIndex = 1,
            availableLetterCounts = counts,
            rows = listOf(
                GameRowModel(
                    letters = letters,
                    nbCells = letters.size,
                    nbActiveCells = letters.size,
                    hasCommitted = true,
                    isScoreDisabled = true
                ),
                GameRowModel(
                    letters = emptyList(),
                    nbCells = letters.size,
                    nbActiveCells = letters.size-1,
                    hasCommitted = false
                )
            )
        )
    }

    fun pickRandomStartWord(): String {
        return "Matelas"
    }

    fun delete() {}

    fun submitWord() {}

    fun typeLetter(character: Char) {}

    init {
        resetGame()
    }
}