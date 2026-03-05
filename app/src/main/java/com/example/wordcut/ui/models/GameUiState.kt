package com.example.wordcut.ui.models

data class GameUiState(
    val word: String = "",
    val rows: List<GameRowModel> = emptyList(),
    val remainingLetterCounts: Map<Char, Int> = emptyMap(),
    val currentRowIndex: Int = 0,
)