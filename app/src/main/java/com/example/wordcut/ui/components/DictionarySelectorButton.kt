package com.example.wordcut.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import flagkit.Flag

private val DictionaryButtonShape = RoundedCornerShape(8.dp)
private val DictionaryButtonBg = Color(0xFFDCE1ED)
private val DictionaryButtonText = Color(0xFF5A6376)

@Composable
fun DictionarySelectorButton(
    flagCode: String,
    label: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Surface(
        modifier = modifier
            .defaultMinSize(minHeight = 36.dp),
        shape = DictionaryButtonShape,
        color = DictionaryButtonBg,
        onClick = onClick,
    ) {
        Row (
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Flag(
                code = flagCode,
                shape = RoundedCornerShape(3.dp),
                size = DpSize(20.dp, 14.dp),
            )
            Text(
                text = label,
                fontWeight = FontWeight.Bold,
                color = DictionaryButtonText,
                style = MaterialTheme.typography.labelLarge,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FrenchPreview() {
    DictionarySelectorButton(
        flagCode = "FR",
        label = "FR"
    )
}

@Preview(showBackground = true)
@Composable
fun EnglishPreview() {
    DictionarySelectorButton(
        flagCode = "US",
        label = "EN"
    )
}

@Preview(
    name = "Phone Preview",
    showBackground = true,
    showSystemUi = true
)
@Composable
fun PhonePreview() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            DictionarySelectorButton(
                flagCode = "FR",
                label = "FR"
            )
            DictionarySelectorButton(
                flagCode = "US",
                label = "EN"
            )
        }
    }
}