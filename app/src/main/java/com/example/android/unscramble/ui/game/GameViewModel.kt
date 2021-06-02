package com.example.android.unscramble.ui.game

import android.util.Log
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel (){
    private var _score = 0
    private var _currentWordCount = 0
    private lateinit var _currentScrambledWord: String
    private var wordList: MutableList<String> = mutableListOf()
    private lateinit var currentWord: String

    /*
     * Getters used to safely access data
     */
    val score: Int
        get() = _score

    val currentWordCount: Int
        get() = _currentWordCount

    val currentScrambledWord: String
        get() = _currentScrambledWord

    /*
     * Sets up the first word of the game
     */
    init {
        Log.d("Game Fragment", "GameViewModel created!")
        getNextWord()
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("GameFragment", "GameViewModel destroyed!")
    }

    /*
     * Prepares next word in the game and prevents the same word from popping up again.
     */
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

    /*
     * Returns true if the current word count is less than MAX_NO_OF_WORDS.
     * Updates the next word
     */
    fun nextWord(): Boolean {
        return if(currentWordCount < MAX_NO_OF_WORDS) {
            getNextWord()
            true
        } else false
    }

    private fun increaseScore(){
        _score += SCORE_INCREASE
    }

    fun isUserWordCorrect(playerWord: String): Boolean {
        if (playerWord.equals(currentWord, true)) {
            increaseScore()
            return true
        }
        return false
    }

    /*
     * Re-initializes the game data to restart the game.
     */
    fun reinitializeData(){
        _score = 0
        _currentWordCount = 0
        wordList.clear()
        getNextWord()
    }
}