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
        val rows = listOf(
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
        val currentRowTypedLetters = rows[1].letters

        _uiState.value = GameUiState(
            word = word,
            rows = rows,
            currentRowIndex = 1,
            remainingLetterCounts = remainingCounts(word, currentRowTypedLetters),
        )
    }

    fun pickRandomStartWord(): String {
        return "Matelas"
    }

    fun typeLetter(value: Char) {
        val state = _uiState.value
        val rowIndex = state.currentRowIndex
        val rows = state.rows.toMutableList()
        val row = rows[rowIndex]

        val letter = value.uppercaseChar()
        val remaining = state.remainingLetterCounts[letter] ?: 0

        if (remaining <= 0) return
        if (row.letters.size >= row.nbActiveCells) return

        val newLetters = row.letters + letter
        rows[rowIndex] = row.copy(letters = newLetters)

        _uiState.value = state.copy(
            rows = rows,
            remainingLetterCounts = remainingCounts(state.word, newLetters)
        )
    }

    fun delete() {
        val state = _uiState.value
        val rowIndex = state.currentRowIndex
        val rows = state.rows.toMutableList()
        val row = rows[rowIndex]

        val newLetters = row.letters.dropLast(1)
        rows[rowIndex] = row.copy(letters = newLetters)

        _uiState.value = state.copy(
            rows = rows,
            remainingLetterCounts = remainingCounts(state.word, newLetters)
        )
    }

    fun submitWord() {}

    private fun countsOf(letters: List<Char>): Map<Char, Int> =
        letters.groupingBy { it }.eachCount()

    private fun remainingCounts(word: String, typed: List<Char>): Map<Char, Int> {
        val start = countsOf(word.uppercase().toList())
        val used = countsOf(typed.map { it.uppercaseChar() })

        return start.mapValues { (c, total) ->
            (total - (used[c] ?: 0)).coerceAtLeast(0)
        }
    }

    init {
        resetGame()
    }
}