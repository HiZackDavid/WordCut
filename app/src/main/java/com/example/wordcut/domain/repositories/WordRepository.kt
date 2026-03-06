package com.example.wordcut.domain.repositories

interface WordRepository {
    fun getRandomWord(): String
}