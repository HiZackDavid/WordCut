package com.example.wordcut.ui.layouts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wordcut.ui.components.GameRow
import com.example.wordcut.ui.models.GameRowModel

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
fun IncompleteGameLayoutPreview() {
    GameLayout(rows = buildDemoRows(), activeRowIndex = 4)
}