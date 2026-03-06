package com.example.wordcut

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.wordcut.ui.screens.GameScreen
import com.example.wordcut.ui.theme.WordCutTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Scaffold (modifier = Modifier.fillMaxSize()) { innerPadding ->
                WordCutTheme {
                    GameScreen(
                        modifier = Modifier
                            .padding(paddingValues = innerPadding)
                    )
                }
            }
        }
    }
}