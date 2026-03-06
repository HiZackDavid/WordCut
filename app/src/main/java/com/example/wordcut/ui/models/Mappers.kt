package com.example.wordcut.ui.models

import com.example.wordcut.domain.models.GameState

fun GameState.toUiState(): GameUiState {
    val nbCells = startWord.length

    return GameUiState(
        word = startWord,
        currentRowIndex = currentRowIndex,
        remainingLetterCounts = remainingLetterCounts,
        rows = rows.mapIndexed { index, row ->
            GameRowModel(
                letters = row.letters,
                nbCells = nbCells,
                nbActiveCells = row.maxActiveLetters,
                hasCommitted = row.committed,
                isScoreDisabled = (index == 0),
                point1 = row.point1,
                point2 = row.point2
            )
        }
    )
}