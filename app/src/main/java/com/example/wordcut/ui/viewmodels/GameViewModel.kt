package com.example.wordcut.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wordcut.domain.models.GameState
import com.example.wordcut.domain.usecases.DeleteLetterUseCase
import com.example.wordcut.domain.usecases.StartGameUseCase
import com.example.wordcut.domain.usecases.SubmitWordUseCase
import com.example.wordcut.domain.usecases.TypeLetterUseCase
import com.example.wordcut.ui.models.GameUiState
import com.example.wordcut.ui.models.toUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val startGame: StartGameUseCase,
    private val typeLetter: TypeLetterUseCase,
    private val deleteLetter: DeleteLetterUseCase,
    private val submitWord: SubmitWordUseCase
): ViewModel() {
    private lateinit var domaineState: GameState

    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    private var countdown: Job? = null
    private var gameDurationSeconds = 120

    private var selectedDictionaryId: String = "francais.txt"

    fun resetGame() {
        viewModelScope.launch {
            domaineState = startGame(selectedDictionaryId)
            _uiState.value = domaineState.toUiState().copy(
                remainingTimeSeconds = gameDurationSeconds,
                isTimeUp = false
            )
            startTimer()
        }
    }

    fun typeLetter(rawCharacter: Char) {
        if (_uiState.value.isTimeUp) return
        if (!::domaineState.isInitialized) return

        domaineState = typeLetter(domaineState, rawCharacter)
        updateUiState()
    }

    fun delete() {
        if (_uiState.value.isTimeUp) return
        if (!::domaineState.isInitialized) return

        domaineState = deleteLetter(domaineState)
        updateUiState()
    }

    fun submitWord() {
        if (_uiState.value.isTimeUp) return
        if (!::domaineState.isInitialized) return

        viewModelScope.launch {
            domaineState = submitWord(
                state = domaineState,
                dictionaryId = selectedDictionaryId
            )
            updateUiState()
        }
    }

    private fun startTimer() {
        countdown?.cancel()

        countdown = viewModelScope.launch {
            while (_uiState.value.remainingTimeSeconds > 0) {
                delay(1000) // 1 second
                val current = _uiState.value
                val next = current.remainingTimeSeconds - 1

                _uiState.value = current.copy(
                    remainingTimeSeconds = next,
                    isTimeUp = next <= 0
                )
            }
        }
    }

    private fun updateUiState() {
        val current = _uiState.value

        _uiState.value = domaineState.toUiState().copy(
            remainingTimeSeconds = current.remainingTimeSeconds,
            isTimeUp = current.isTimeUp
        )
    }

    init {
        resetGame()
    }
}