package com.example.wordcut.domain.usecases

import com.example.wordcut.domain.repositories.WordRepository
import com.example.wordcut.domain.models.GameRow
import com.example.wordcut.domain.models.GameState
import com.example.wordcut.domain.utils.remainingLetterCounts

class StartGameUseCase(
    private val wordRepository: WordRepository
) {
    operator fun invoke(): GameState {
        val word = wordRepository.getRandomWord().uppercase()
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