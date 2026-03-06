package com.example.wordcut.domain.repositories

interface WordRepository {
    fun getRandomWord(): String
    fun isValidWord(word: String): Boolean
}