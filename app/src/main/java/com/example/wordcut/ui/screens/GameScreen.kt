package com.example.wordcut.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.wordcut.domain.models.Dictionary
import com.example.wordcut.domain.models.DictionarySource
import com.example.wordcut.ui.components.DictionaryPickerDialog
import com.example.wordcut.ui.components.GameTopBar
import com.example.wordcut.ui.layouts.GameLayout
import com.example.wordcut.ui.layouts.KeyboardLayout
import com.example.wordcut.ui.models.GameRowModel
import com.example.wordcut.ui.models.GameUiState
import com.example.wordcut.ui.theme.WordCutTheme
import com.example.wordcut.ui.viewmodels.GameViewModel

@Composable
fun GameScreen(modifier: Modifier = Modifier, gameViewModel: GameViewModel = hiltViewModel()) {
    val gameUiState by gameViewModel.uiState.collectAsState()

    GameScreenContent(
        uiState = gameUiState,
        onRestart = { gameViewModel.resetGame() },
        onDelete = { gameViewModel.delete() },
        onSubmit = { gameViewModel.submitWord() },
        onKeyPressed = { gameViewModel.typeLetter(it) },
        onDictionarySelected = { gameViewModel.changeDictionary(it) },
        modifier = modifier
    )
}

@Composable
fun GameScreenContent(
    modifier: Modifier = Modifier,
    uiState: GameUiState = GameUiState(),
    onDelete: () -> Unit = {},
    onSubmit: () -> Unit = {},
    onRestart: () -> Unit = {},
    onKeyPressed: (Char) -> Unit = {},
    onDictionarySelected: (String) -> Unit = {},
    initialShowDictionaryDialog: Boolean = false
) {
    var showDictionaryDialog by remember { mutableStateOf(initialShowDictionaryDialog) }
    
    Scaffold (
        modifier = modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.safeDrawing,
        topBar = {
            GameTopBar(
                title = "WORDCUT",
                remainingTimeSeconds = uiState.remainingTimeSeconds,
                selectedDictionaryCode = uiState.availableDictionaries
                    .firstOrNull { it.id == uiState.selectedDictionaryId }
                    ?.languageCode ?: "FR",
                onDictionaryClick = { showDictionaryDialog = true }
            )
        },
        bottomBar = {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Surface(
                       onClick = onRestart
                    ) {
                        Box(
                            modifier = Modifier
                                .background(
                                    color = Color(0xFFf2e4e7),
                                    shape = RoundedCornerShape(4.dp)
                                )
                                .padding(8.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = "Restart",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFae7b8d)
                            )
                        }
                    }
                }
                KeyboardLayout(
                    availableLetterCounts = uiState.remainingLetterCounts,
                    onKeyPressed = onKeyPressed,
                    onSubmit = onSubmit,
                    onDelete = onDelete
                )
            }
        }
    ) { innerPadding ->
        if (uiState.rows.isEmpty()) {
            Box (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
            ){
                Text("Loading...")
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.TopCenter
                ) {
                    GameLayout(
                        rows = uiState.rows,
                        activeRowIndex = uiState.currentRowIndex
                    )
                }
            }
        }
    }

    if (showDictionaryDialog) {
        DictionaryPickerDialog(
            dictionaries = uiState.availableDictionaries,
            selectedDictionaryId = uiState.selectedDictionaryId,
            onDismiss = { showDictionaryDialog = false },
            onDictionarySelected = { dictionaryId ->
                showDictionaryDialog = false
                onDictionarySelected(dictionaryId)
            }
        )
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
                word = word,
                currentRowIndex = 2,
                remainingLetterCounts = mapOf(
                    'M' to 1, 'E' to 1, 'T' to 1,
                    'A' to 1, 'L' to 1
                ),
                rows = buildDemoRows().subList(0, 2) + listOf(
                    GameRowModel(
                        letters = "ME".toList(),
                        nbCells = letters.size,
                        nbActiveCells = letters.size-3,
                        hasCommitted = false
                    )
                )
            )
        )
    }
}

@Preview
@Composable
fun LoadingGamePreview() {
    GameScreenContent(uiState = GameUiState())
}

@Preview(
    name = "Dictionary Picker Preview",
    showBackground = true,
    showSystemUi = true
)
@Composable
fun DictionaryPickerPreview() {
    WordCutTheme {
        GameScreenContent(
            uiState = GameUiState(
                word = "MATELAS",
                currentRowIndex = 1,
                remainingTimeSeconds = 101,
                selectedDictionaryId = "francais.txt",
                availableDictionaries = listOf(
                    Dictionary(
                        id = "francais.txt",
                        displayName = "French",
                        languageCode = "FR",
                        source = DictionarySource.Asset("francais.txt")
                    ),
                    Dictionary(
                        id = "english.txt",
                        displayName = "English",
                        languageCode = "EN",
                        source = DictionarySource.Asset("english.txt")
                    )
                ),
                remainingLetterCounts = mapOf(
                    'M' to 1, 'E' to 1, 'T' to 1,
                    'A' to 1, 'L' to 1
                ),
                rows = buildDemoRows().subList(0, 2)
            ),
            initialShowDictionaryDialog = true
        )
    }
}
