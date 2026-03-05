package com.example.wordcut.domain.usecases

import com.example.wordcut.domain.models.GameRow
import com.example.wordcut.domain.models.GameState
import com.example.wordcut.domain.utils.remainingLetterCounts

class StartGameUseCase {
    operator fun invoke(rawWord: String): GameState {
        val word = rawWord.uppercase()
        val rows = listOf(
            GameRow(
                letters = word.toList(),
                maxActiveLetters = word.length,
                committed = true
            ),
            GameRow(
                letters = emptyList(),
                maxActiveLetters = word.length - 1,
                committed = false
            )
        )

        return GameState(
            word = word,
            rows = rows,
            currentRowIndex = 1,
            remainingLetterCounts = remainingLetterCounts(word, rows[1].letters)
        )
    }
}