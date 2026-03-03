package com.example.wordcut.ui.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wordcut.ui.models.GameUiState
import com.example.wordcut.ui.models.WordCutRowModel
import com.example.wordcut.ui.theme.WordCutTheme
import com.example.wordcut.ui.viewmodels.GameViewModel

@Composable
fun GameScreen(modifier: Modifier = Modifier, gameViewModel: GameViewModel = viewModel()) {
    val gameUiState by gameViewModel.uiState.collectAsState()
    GameScreenContent(uiState = gameUiState, modifier = modifier)
}

@Composable
fun GameScreenContent(
    modifier: Modifier = Modifier,
    uiState: GameUiState = GameUiState()
) {
    GameLayout(
        modifier = modifier.fillMaxWidth(),
        rows = uiState.rows
    )
}

@Preview(
    name = "Phone Preview",
    showBackground = true,
    showSystemUi = true
)
@Composable
fun GameScreenPhonePreview() {
    val word = "MATELAS"
    val letters = word.toList()
    WordCutTheme {
        GameScreenContent(
            uiState = GameUiState(
                word = "Matelas",
                currentRowIndex = 1,
                rows = listOf(
                    WordCutRowModel(
                        letters = letters,
                        nbCells = letters.size,
                        nbActiveCells = letters.size,
                        hasCommitted = true,
                        isScoreDisabled = true
                    ),
                    WordCutRowModel(
                        letters = emptyList(),
                        nbCells = letters.size,
                        nbActiveCells = letters.size-1,
                        hasCommitted = false
                    )
                )
            )
        )
    }
}