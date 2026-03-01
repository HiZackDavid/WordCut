package com.example.wordcut.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wordcut.ui.components.cells.CommittedCell
import com.example.wordcut.ui.components.cells.DisabledCell
import com.example.wordcut.ui.components.cells.DisabledScoreCell
import com.example.wordcut.ui.components.cells.ScoreCell
import com.example.wordcut.ui.components.cells.WriteCell
import com.example.wordcut.ui.models.WordCutRowModel

@Composable
fun WordCutRow(row: WordCutRowModel, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val cellModifier = Modifier
            .weight(1f)
            .aspectRatio(1f)

        for (i in 0 until row.nbCells) {
            when {
                i >= row.nbActiveCells ->
                    DisabledCell(cellModifier)
                row.hasCommitted && i < row.letters.size ->
                    CommittedCell(row.letters[i], cellModifier)
                !row.hasCommitted && i < row.letters.size ->
                    WriteCell(letter = row.letters[i], modifier = cellModifier)
                !row.hasCommitted && i < row.nbActiveCells ->
                    WriteCell(modifier = cellModifier)
                else -> DisabledCell(cellModifier)
            }
        }

        if (row.isScoreDisabled) {
            DisabledScoreCell(cellModifier)
        } else {
            ScoreCell(
                cellModifier,
                point1 = row.point1,
                point2 = row.point2
            )
        }
    }
}

private object DemoRows {
    const val WORD = "MATELAS"
    val letters = WORD.uppercase().toList()

    val firstRow = WordCutRowModel(
        letters = letters,
        nbCells = letters.size,
        nbActiveCells = letters.size,
        hasCommitted = true,
        isScoreDisabled = true
    )
    val dualScore = WordCutRowModel(
        letters = "METAL".toList(),
        nbCells = letters.size,
        nbActiveCells = letters.size-2,
        hasCommitted = true,
        point1 = 2,
        point2 = 2
    )
    val singleScore = WordCutRowModel(
        letters = "AME".toList(),
        nbCells = letters.size,
        nbActiveCells = letters.size-4,
        hasCommitted = true,
        point1 = 3
    )
    val playableRow = WordCutRowModel(
        letters = "M".toList(),
        nbCells = letters.size,
        nbActiveCells = letters.size-5,
        hasCommitted = false
    )
}

@Preview
@Composable
fun FirstRowPreview() {
   WordCutRow(DemoRows.firstRow)
}

@Preview
@Composable
fun DualScorePreview() {
    WordCutRow(DemoRows.dualScore)
}

@Preview
@Composable
fun SingleScorePreview() {
    WordCutRow(DemoRows.singleScore)
}

@Preview
@Composable
fun PlayablePreview() {
    WordCutRow(DemoRows.playableRow)
}