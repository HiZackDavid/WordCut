package com.example.wordcut.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wordcut.ui.components.GameRow
import com.example.wordcut.ui.models.GameRowModel
import com.example.wordcut.ui.models.GameUiState
import com.example.wordcut.ui.theme.WordCutTheme
import com.example.wordcut.ui.viewmodels.GameViewModel

@Composable
fun GameScreen(modifier: Modifier = Modifier, gameViewModel: GameViewModel = viewModel()) {
    val gameUiState by gameViewModel.uiState.collectAsState()

    GameScreenContent(
        uiState = gameUiState,
        onRestart = { gameViewModel.resetGame() },
        onDelete = { gameViewModel.delete() },
        onSubmit = { gameViewModel.submitWord() },
        onKeyPressed = { gameViewModel.typeLetter(it) },
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
    onKeyPressed: (Char) -> Unit = {}
) {
    Scaffold (
        modifier = modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.safeDrawing,
        topBar = { GameTopBar(title = "WORDCUT") },
        bottomBar = {
            GameKeyboard(
                letters = uiState.availableLetters,
                onKeyPressed = onKeyPressed,
                onRestart = onRestart,
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
                        .weight(1f)      // prend tout l’espace dispo
                        .fillMaxWidth(),
                    contentAlignment = Alignment.TopCenter
                ) {
                    GameLayout(
                        modifier = modifier.padding(innerPadding),
                        rows = uiState.rows,
                        activeRowIndex = uiState.currentRowIndex
                    )
                }
            }
        }
    }
}

@Composable
fun GameTopBar(
    title: String,
) {
    Surface(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .height(64.dp)
                .padding(horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {}) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
            Text(
                text = title.uppercase(),
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = {}) {
                Icon(Icons.Outlined.Info, contentDescription = "Hint")
            }
        }
    }
}

@Composable
fun GameKeyboard(
    letters: List<Char>,
    onKeyPressed: (Char) -> Unit,
    onRestart: () -> Unit,
    onDelete: () -> Unit,
    onSubmit: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            letters.forEach { character ->
                KeyButton(
                    text = character.toString(),
                    modifier = Modifier.weight(1f),
                    onClick = { onKeyPressed(character) }
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ActionButton(
                text = "Delete",
                icon = Icons.Outlined.Delete,
                onClick = onDelete
            )
            ActionButton(
                text = "Submit",
                icon = Icons.Outlined.Check,
                onClick = onSubmit
            )
            ActionButton(
                text = "Restart",
                icon = Icons.Outlined.Refresh,
                onClick = onRestart
            )
        }
    }
}

@Composable
private fun KeyButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(14.dp))
            .background(Color(0xFFE45555))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

@Composable
fun GameLayout(
    activeRowIndex: Int,
    modifier: Modifier = Modifier,
    rows: List<GameRowModel> = emptyList()
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ){
        rows.forEachIndexed { index, row ->
            val isActive = (index == activeRowIndex && !row.hasCommitted)
            val cursorIndex = if (isActive) row.letters.size else -1
            GameRow(
                row = row,
                isActive = isActive,
                cursorIndex =cursorIndex
            )
        }
    }
}

@Composable
private fun ActionButton(
    text: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Button(
            onClick = onClick,
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2FB39A)
            ),
            modifier = Modifier.size(56.dp), // bouton carré
            contentPadding = PaddingValues(0.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = text
            )
        }

        Spacer(Modifier.height(4.dp))

        Text(
            text = text,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurface
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
                availableLetters = "METAL".toList(),
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
fun IncompleteGameLayoutPreview() {
    GameLayout(rows = buildDemoRows(), activeRowIndex = 4)
}

@Preview
@Composable
fun LoadingGamePreview() {
    GameScreenContent(uiState = GameUiState())
}
