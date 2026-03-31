package com.example.wordcut.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import flagkit.Flag

@Composable
fun DictionarySelectorButton(
    languageCode: String,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFFDCE1ED))
            .clickable(onClick = onClick)
            .padding(horizontal = 10.dp, vertical = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Flag(
                code = languageCode,
                shape = RoundedCornerShape(2.dp),
                size = DpSize(24.dp, 16.dp),
            )
            Text(
                text = languageCode,
                color = Color(0xFF5A6376),
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@Preview
@Composable
fun FrenchPreview() {
    DictionarySelectorButton("FR")
}

@Preview
@Composable
fun EnglishPreview() {
    DictionarySelectorButton("US")
}

@Preview(
    name = "Phone Preview",
    showBackground = true,
    showSystemUi = true
)
@Composable
fun PhonePreview() {
    Box (
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),

        ) {
            DictionarySelectorButton("FR")
            DictionarySelectorButton("US")
        }
    }

}