package com.example.wordcut.data.repositories

import com.example.wordcut.data.datasources.LocalWordDataSource
import com.example.wordcut.domain.repositories.WordRepository

class LocalWordRepository(
    private val local: LocalWordDataSource
): WordRepository {
    override fun getRandomWord(): String = local.randomWord()
    override fun isValidWord(word: String): Boolean = local.contains(word)
}