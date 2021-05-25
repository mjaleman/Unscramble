package com.example.android.unscramble.ui.game

import android.util.Log
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel (){
    private var score = 0
    private var _currentWordCount = 0
    private lateinit var _currentScrambledWord: String
    private var wordList: MutableList<String> = mutableListOf()
    private lateinit var currentWord: String

    val currentWordCount: Int
        get() = _currentWordCount

    val currentScrambledWord: String
        get() = _currentScrambledWord

    init {
        Log.d("Game Fragment", "GameViewModel created!")
        getNextWord()
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("GameFragment", "GameViewModel destroyed!")
    }

    fun getNextWord() {
        currentWord = allWordsList.random()
        val tempWord = currentWord.toCharArray()
        tempWord.shuffle()

        // Ensures the tempWord is actually shuffled
        while(tempWord.toString().equals(currentWord, false)){
            tempWord.shuffle()
        }

        // Checks to see if word has been used before to prevent repeating words in the game
        if (wordList.contains(currentWord)){
            getNextWord()
        } else {
            _currentScrambledWord = String(tempWord)
            ++_currentWordCount
            wordList.add(currentWord)
        }
    }

    fun nextWord(): Boolean {
        return if(currentWordCount < MAX_NO_OF_WORDS) {
            getNextWord()
            true
        } else false
    }
}