package com.example.wordcut.ui.models

import com.example.wordcut.domain.models.GameState

fun GameState.toUiState(): GameUiState {
    val nbCells = word.length

    return GameUiState(
        word = word,
        currentRowIndex = currentRowIndex,
        remainingLetterCounts = remainingLetterCounts,
        rows = rows.mapIndexed { index, row ->
            GameRowModel(
                letters = row.letters,
                nbCells = nbCells,
                nbActiveCells = row.maxActiveLetters,
                hasCommitted = row.committed,
                isScoreDisabled = (index == 0)
            )
        }
    )
}