package com.example.wordcut.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.wordcut.domain.models.GameState
import com.example.wordcut.domain.usecases.DeleteLetterUseCase
import com.example.wordcut.domain.usecases.StartGameUseCase
import com.example.wordcut.domain.usecases.TypeLetterUseCase
import com.example.wordcut.ui.models.GameUiState
import com.example.wordcut.ui.models.toUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GameViewModel: ViewModel() {
    private val startGame = StartGameUseCase()
    private val typeLetter = TypeLetterUseCase()
    private val deleteLetter = DeleteLetterUseCase()

    private var domaineState: GameState = startGame(pickRandomStartWord())

    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    fun resetGame() {
        domaineState = startGame(pickRandomStartWord())
        _uiState.value = domaineState.toUiState()
    }

    fun typeLetter(rawCharacter: Char) {
        domaineState = typeLetter(domaineState, rawCharacter)
        _uiState.value = domaineState.toUiState()
    }

    fun delete() {
        domaineState = deleteLetter(domaineState)
        _uiState.value = domaineState.toUiState()
    }

    fun submitWord() {}

    fun pickRandomStartWord(): String {
        return "Matelas"
    }

    init {
        resetGame()
    }
}