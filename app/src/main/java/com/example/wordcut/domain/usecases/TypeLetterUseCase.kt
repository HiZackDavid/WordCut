package com.example.wordcut.domain.usecases

import com.example.wordcut.domain.models.GameState
import com.example.wordcut.domain.utils.remainingLetterCounts

class TypeLetterUseCase {
    operator fun invoke(state: GameState, rawCharacter: Char): GameState {
        if (state.isGameOver) return state

        val letter = rawCharacter.uppercaseChar()

        val remaining = state.remainingLetterCounts[letter] ?: 0
        if (remaining <= 0) return state

        val rowIndex = state.currentRowIndex
        val row = state.rows[rowIndex]
        if (row.committed) return state
        if (row.letters.size >= row.maxActiveLetters) return state

        val updatedRow = row.copy(letters = row.letters + letter)
        val updatedRows = state.rows.toMutableList().apply { this[rowIndex] = updatedRow }

        return state.copy(
            rows = updatedRows,
            remainingLetterCounts = remainingLetterCounts(state.activeWord, updatedRow.letters)
        )
    }
}