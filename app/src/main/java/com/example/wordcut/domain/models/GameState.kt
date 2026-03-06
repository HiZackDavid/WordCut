package com.example.wordcut.domain.models

data class GameState(
    val startWord: String,
    val activeWord: String,
    val rows: List<GameRow>,
    val currentRowIndex: Int,
    val remainingLetterCounts: Map<Char, Int>,
    val isGameOver: Boolean = false
)

data class GameRow(
    val letters: List<Char>,
    val maxActiveLetters: Int,
    val committed: Boolean,
    val point1: Int = 0,
    val point2: Int = 0
)