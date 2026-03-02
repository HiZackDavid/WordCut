package com.example.wordcut.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.wordcut.ui.layouts.GameLayout
import com.example.wordcut.ui.theme.WordCutTheme

@Composable
fun GameScreen(word: String, modifier: Modifier = Modifier) {
    GameLayout(word, modifier = modifier)
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