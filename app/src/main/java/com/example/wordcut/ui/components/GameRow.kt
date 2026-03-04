package com.example.wordcut.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wordcut.ui.components.cells.CommittedCell
import com.example.wordcut.ui.components.cells.DisabledCell
import com.example.wordcut.ui.components.cells.DisabledScoreCell
import com.example.wordcut.ui.components.cells.ScoreCell
import com.example.wordcut.ui.components.cells.WriteCell
import com.example.wordcut.ui.models.GameRowModel

@Composable
fun GameRow(
    row: GameRowModel,
    isActive: Boolean,
    cursorIndex: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val cellModifier = Modifier
            .weight(1f)
            .aspectRatio(1f)

        for (i in 0 until row.nbCells) {
            val highlight = isActive
                    && i == cursorIndex
                    && !row.hasCommitted
                    && i < row.nbActiveCells
            val highlightColor = Color.Black

            val finalCellModifier = cellModifier.then(
                if (highlight) Modifier.border(
                    2.dp,
                    highlightColor,
                    RoundedCornerShape(12.dp)
                ) else Modifier
            )

            when {
                i >= row.nbActiveCells ->
                    DisabledCell(cellModifier)
                row.hasCommitted && i < row.letters.size ->
                    CommittedCell(row.letters[i], finalCellModifier)
                !row.hasCommitted && i < row.letters.size ->
                    WriteCell(letter = row.letters[i], modifier = finalCellModifier)
                !row.hasCommitted && i < row.nbActiveCells ->
                    WriteCell(modifier = finalCellModifier)
                else -> DisabledCell(finalCellModifier)
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

    val firstRow = GameRowModel(
        letters = letters,
        nbCells = letters.size,
        nbActiveCells = letters.size,
        hasCommitted = true,
        isScoreDisabled = true
    )
    val dualScore = GameRowModel(
        letters = "METAL".toList(),
        nbCells = letters.size,
        nbActiveCells = letters.size-2,
        hasCommitted = true,
        point1 = 2,
        point2 = 2
    )
    val singleScore = GameRowModel(
        letters = "AME".toList(),
        nbCells = letters.size,
        nbActiveCells = letters.size-4,
        hasCommitted = true,
        point1 = 3
    )
    val playableRow = GameRowModel(
        letters = "M".toList(),
        nbCells = letters.size,
        nbActiveCells = letters.size-2,
        hasCommitted = false
    )
}

@Preview
@Composable
fun FirstRowPreview() {
   GameRow(DemoRows.firstRow, isActive = false, cursorIndex = DemoRows.firstRow.letters.lastIndex)
}

@Preview
@Composable
fun DualScorePreview() {
    GameRow(DemoRows.dualScore, isActive = false, cursorIndex = DemoRows.dualScore.letters.lastIndex)
}

@Preview
@Composable
fun SingleScorePreview() {
    GameRow(DemoRows.singleScore, isActive = false, cursorIndex = DemoRows.firstRow.letters.lastIndex)
}

@Preview
@Composable
fun PlayablePreview() {
    GameRow(DemoRows.playableRow, isActive = true, cursorIndex = 1, )
}