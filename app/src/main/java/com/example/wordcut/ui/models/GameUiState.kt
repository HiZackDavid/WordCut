package com.example.wordcut.ui.models

import com.example.wordcut.domain.models.Dictionary

data class GameUiState(
    val word: String = "",
    val rows: List<GameRowModel> = emptyList(),
    val remainingLetterCounts: Map<Char, Int> = emptyMap(),
    val currentRowIndex: Int = 0,
    val remainingTimeSeconds: Int = 0,
    val isTimeUp: Boolean = false,
    val isPaused: Boolean = false,
    val selectedDictionaryId: String = "francais.txt",
    val availableDictionaries: List<Dictionary> = emptyList(),
    val showInfoDialogOnStart: Boolean = false
)