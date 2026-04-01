package com.example.wordcut.domain.repositories

interface WordRepository {
    suspend fun getRandomWord(dictionaryId: String): String
    suspend fun isValidWord(dictionaryId: String, word: String): Boolean
}