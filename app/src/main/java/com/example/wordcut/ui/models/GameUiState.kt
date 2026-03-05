package com.example.wordcut.ui.models

data class GameUiState(
    val word: String = "",
    val rows: List<GameRowModel> = emptyList(),
    val availableLetterCounts: Map<Char, Int> = emptyMap(),
    val currentRowIndex: Int = 0,
)