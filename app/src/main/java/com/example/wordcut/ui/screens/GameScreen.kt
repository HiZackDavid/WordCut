package com.example.wordcut.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wordcut.ui.components.GameRow
import com.example.wordcut.ui.models.GameRowModel
import com.example.wordcut.ui.models.GameUiState
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

@Composable
fun GameLayout(modifier: Modifier = Modifier, rows: List<GameRowModel> = emptyList()) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ){
        for (row in rows) {
            GameRow(row)
        }
    }
}

private fun buildDemoRows(): List<GameRowModel> {
    val letters: List<Char> = "MATELAS".toList()
    val nbLetters = letters.size

    return listOf(
        GameRowModel(
            letters = letters,
            nbCells = nbLetters,
            nbActiveCells = nbLetters,
            hasCommitted = true,
            isScoreDisabled = true
        ),
        GameRowModel(
            letters = "METAL".toList(),
            nbCells = nbLetters,
            nbActiveCells = nbLetters-2,
            hasCommitted = true,
            point1 = 2,
            point2 = 2
        ),
        GameRowModel(
            letters = "LAME".toList(),
            nbCells = nbLetters,
            nbActiveCells = nbLetters-3,
            hasCommitted = true,
            point1 = 3,
            point2 = 2
        ),
        GameRowModel(
            letters = "AME".toList(),
            nbCells = nbLetters,
            nbActiveCells = nbLetters-4,
            hasCommitted = true,
            point1 = 3
        ),
        GameRowModel(
            letters = "M".toList(),
            nbCells = nbLetters,
            nbActiveCells = nbLetters-5,
            hasCommitted = false
        )
    )
}

@Preview
@Composable
fun IncompleteGamePreview() {
    GameLayout(rows = buildDemoRows())
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
                rows = buildDemoRows().subList(0, 2) + listOf(
                    GameRowModel(
                        letters = emptyList(),
                        nbCells = letters.size,
                        nbActiveCells = letters.size-3,
                        hasCommitted = false
                    )
                )
            )
        )
    }
}