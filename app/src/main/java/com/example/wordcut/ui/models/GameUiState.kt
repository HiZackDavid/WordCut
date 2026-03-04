package com.example.wordcut.ui.models

data class GameUiState(
    val word: String = "",
    val rows: List<GameRowModel> = emptyList(),
    val availableLetters: List<Char> = emptyList(),
    val currentRowIndex: Int = 0,
    val currentCellIndex: Int = 0
)