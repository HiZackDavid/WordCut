package com.example.wordcut.data.repositories

import com.example.wordcut.data.datasources.LocalWordDataSource
import com.example.wordcut.domain.repositories.WordRepository

class FakeWordRepository(
    private val local: LocalWordDataSource
): WordRepository {
    override fun getRandomWord(): String = local.randomWord()
}