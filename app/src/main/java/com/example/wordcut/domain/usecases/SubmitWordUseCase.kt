package com.example.wordcut.domain.usecases

import com.example.wordcut.domain.models.GameRow
import com.example.wordcut.domain.models.GameState
import com.example.wordcut.domain.repositories.WordRepository
import com.example.wordcut.domain.utils.remainingLetterCounts

class SubmitWordUseCase(
    private val wordRepository: WordRepository
) {
    suspend operator fun invoke(dictionaryId: String, state: GameState): GameState {
        if (state.isGameOver) return state

        val rowIndex = state.currentRowIndex
        val row = state.rows.getOrNull(rowIndex) ?: return state
        if (row.committed) return state
        if (row.letters.isEmpty()) return state

        val sourceWord = state.activeWord.uppercase()
        val typedLetters = row.letters.map { it.uppercaseChar() }
        val typed = typedLetters.joinToString("")

        val removedCount = sourceWord.length - typed.length
        if (removedCount !in 1..3) return state

        if (!isConstructedFromSource(typedLetters, sourceWord.toList())) return state
        if (!wordRepository.isValidWord(dictionaryId = dictionaryId, word = typed)) return state

        val point1 = when (removedCount) {
            1 -> 3
            2 -> 2
            3 -> 1
            else -> 0
        }

        val point2 = if (isSubsequenceOfSource(
                subSequence = typedLetters,
                fullSequence = sourceWord.toList()
            )) 0 else 2

        val committedRow = row.copy(
            committed = true,
            point1 = point1,
            point2 = point2
        )

        val updatedRows = state.rows.toMutableList().apply {
            this[rowIndex] = committedRow
        }

        if (typed.length <= 1) {
            return state.copy(
                activeWord = typed,
                rows = updatedRows,
                remainingLetterCounts = emptyMap(),
                isGameOver = true
            )
        }

        val nextRow = GameRow(
            letters = emptyList(),
            maxActiveLetters = (typed.length - 1).coerceAtLeast(0),
            committed = false
        )

        updatedRows.add(nextRow)

        return state.copy(
            activeWord = typed,
            rows = updatedRows,
            currentRowIndex = rowIndex + 1,
            remainingLetterCounts = remainingLetterCounts(typed, emptyList()),
        )
    }

    private fun isConstructedFromSource(target: List<Char>, source: List<Char>): Boolean {
        val targetCounts = target.groupingBy { it }.eachCount()
        val sourceCounts = source.groupingBy { it }.eachCount()

        return targetCounts.all { (character, count) ->
            val availableCount = sourceCounts[character] ?: 0
            availableCount >= count
        }
    }

    private fun isSubsequenceOfSource(subSequence: List<Char>, fullSequence: List<Char>): Boolean {
        var subIndex = 0
        var fullIndex = 0

        while (fullIndex < fullSequence.size && subIndex < subSequence.size) {
            if (fullSequence[fullIndex] == subSequence[subIndex]) {
                subIndex++
            }
            fullIndex++
        }

        return subIndex == subSequence.size
    }
}