package com.example.wordcut.ui.models

data class GameUiState(
    val startingWord: String,
    val rows: List<WordCutRowModel>,
    val currentRowIndex: Int
)