package com.example.wordcut.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wordcut.ui.components.WordCutRow
import com.example.wordcut.ui.models.WordCutRowModel
import com.example.wordcut.ui.theme.WordCutTheme

@Composable
fun GameScreen(word: String, modifier: Modifier = Modifier) {
    val rows = remember(word) { buildDemoRows(word) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ){
        for (row in rows) {
            WordCutRow(row)
        }
    }
}

private fun buildDemoRows(word: String): List<WordCutRowModel> {
    val letters: List<Char> = word.uppercase().toList()
    val nbLetters = letters.size

    return listOf(
        WordCutRowModel(
            letters = letters,
            nbCells = nbLetters,
            nbActiveCells = nbLetters,
            hasCommitted = true,
            isScoreDisabled = true
        ),
        WordCutRowModel(
            letters = "METAL".toList(),
            nbCells = nbLetters,
            nbActiveCells = nbLetters-2,
            hasCommitted = true,
            point1 = 2,
            point2 = 2
        ),
        WordCutRowModel(
            letters = "LAME".toList(),
            nbCells = nbLetters,
            nbActiveCells = nbLetters-3,
            hasCommitted = true,
            point1 = 3,
            point2 = 2
        ),
        WordCutRowModel(
            letters = "AME".toList(),
            nbCells = nbLetters,
            nbActiveCells = nbLetters-4,
            hasCommitted = true,
            point1 = 3
        ),
        WordCutRowModel(
            letters = "M".toList(),
            nbCells = nbLetters,
            nbActiveCells = nbLetters-5,
            hasCommitted = false
        )
    )
}

@Preview(
    name = "Composable Preview",
    showBackground = false
)
@Composable
fun GameScreenPreview() {
    WordCutTheme {
        GameScreen(word = "Matelas")
    }
}

@Preview(
    name = "Phone Preview",
    showBackground = true,
    showSystemUi = true
)
@Composable
fun GameScreenPhonePreview() {
    WordCutTheme {
        GameScreen(word = "Matelas")
    }
}