package com.example.wordcut.ui.models

data class WordCutRowModel(
    val letters: List<Char>,
    val nbCells: Int,
    val nbActiveCells: Int,
    val hasCommitted: Boolean = false,
    val isScoreDisabled: Boolean = false,
    val point1: Int = 0,
    val point2: Int = 0
)