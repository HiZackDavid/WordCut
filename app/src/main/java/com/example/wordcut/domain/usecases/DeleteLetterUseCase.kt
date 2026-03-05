package com.example.wordcut.domain.usecases

import com.example.wordcut.domain.models.GameState
import com.example.wordcut.domain.utils.remainingLetterCounts

class DeleteLetterUseCase {
    operator fun invoke(state: GameState): GameState {
        val rowIndex = state.currentRowIndex
        val row = state.rows[rowIndex]
        if (row.committed) return state
        if (row.letters.isEmpty()) return state

        val updatedRow = row.copy(letters = row.letters.dropLast(1))
        val updatedRows = state.rows.toMutableList().apply { this[rowIndex] = updatedRow }

        return state.copy(
            rows = updatedRows,
            remainingLetterCounts = remainingLetterCounts(state.word, updatedRow.letters)
        )
    }
}