package com.example.wordcut.domain.usecases

import com.example.wordcut.domain.repositories.WordRepository
import com.example.wordcut.domain.models.GameRow
import com.example.wordcut.domain.models.GameState
import com.example.wordcut.domain.utils.remainingLetterCounts

class StartGameUseCase(
    private val wordRepository: WordRepository
) {
    suspend operator fun invoke(dictionaryId: String): GameState {
        val word = wordRepository.getRandomWord(dictionaryId).uppercase()
        val letters = word.toList()

        val rows = listOf(
            GameRow(
                letters = letters,
                maxActiveLetters = letters.size,
                committed = true
            ),
            GameRow(
                letters = emptyList(),
                maxActiveLetters = (letters.size - 1).coerceAtLeast(0),
                committed = false
            )
        )

        return GameState(
            startWord = word,
            activeWord = word,
            rows = rows,
            currentRowIndex = 1,
            remainingLetterCounts = remainingLetterCounts(word, emptyList()),
            isGameOver = false
        )
    }
}