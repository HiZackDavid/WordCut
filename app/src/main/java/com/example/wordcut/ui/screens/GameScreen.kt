package com.example.wordcut.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.wordcut.ui.components.DictionaryPickerDialog
import com.example.wordcut.ui.components.LanguageButton
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
) {
    var showDictionaryDialog by remember { mutableStateOf(false) }
    
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
            KeyboardLayout(
                availableLetterCounts = uiState.remainingLetterCounts,
                onKeyPressed = onKeyPressed,
                onSubmit = onSubmit,
                onDelete = onDelete
            )
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

@Composable
fun GameTopBar(
    title: String,
    remainingTimeSeconds: Int,
    selectedDictionaryCode: String,
    onDictionaryClick: () -> Unit,
    onBack: () -> Unit = {},
    onInfo: () -> Unit = {}
) {
    Surface(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }

                Text(
                    text = title.uppercase(),
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = onInfo) {
                    Icon(Icons.Outlined.Info, contentDescription = "Hint")
                }
            }
            Row {
                LanguageButton(
                    languageCode = selectedDictionaryCode,
                    onClick = onDictionaryClick
                )
            }
            Row {
                Text(
                    text = formatTime(remainingTimeSeconds),
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    fontSize = 24.sp
                )
            }
        }
    }
}

private fun formatTime(totalSeconds: Int): String {
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return "%02d:%02d".format(minutes, seconds)
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
