package com.example.wordcut.domain.utils

fun countLetters(letters: List<Char>): Map<Char, Int> =
    letters.groupingBy { it }.eachCount()

fun remainingLetterCounts(word: String, typed: List<Char>): Map<Char, Int> {
    val start = countLetters(word.uppercase().toList())
    val used = countLetters(typed.map { it.uppercaseChar() })

    return start.mapValues { (character, total) ->
        (total - (used[character] ?: 0)).coerceAtLeast(0)
    }
}