package com.example.wordcut.ui.models

data class GameUiState(
    val word: String = "",
    val rows: List<GameRowModel> = emptyList(),
    val currentRowIndex: Int = 0
)