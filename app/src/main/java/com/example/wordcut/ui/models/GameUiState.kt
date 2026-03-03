package com.example.wordcut.ui.models

data class GameUiState(
    val word: String = "",
    val rows: List<WordCutRowModel> = emptyList(),
    val currentRowIndex: Int = 0
)