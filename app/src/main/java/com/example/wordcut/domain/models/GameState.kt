package com.example.wordcut.domain.models

data class GameState(
    val word: String,
    val rows: List<GameRow>,
    val currentRowIndex: Int,
    val remainingLetterCounts: Map<Char, Int>
)

data class GameRow(
    val letters: List<Char>,
    val maxActiveLetters: Int,
    val committed: Boolean
)