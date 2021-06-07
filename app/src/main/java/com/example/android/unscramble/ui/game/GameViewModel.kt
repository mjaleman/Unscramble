package com.example.android.unscramble.ui.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel (){
    private val _score = MutableLiveData(0)
    private val _currentWordCount = MutableLiveData(0)
    private val _currentScrambledWord = MutableLiveData<String>()
    private var wordList: MutableList<String> = mutableListOf()
    private lateinit var currentWord: String

    /*
     * Getters used to safely access data
     */
    val score: LiveData<Int>
        get() = _score

    val currentWordCount: LiveData<Int>
        get() = _currentWordCount

    val currentScrambledWord: LiveData<String>
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
            _currentScrambledWord.value = String(tempWord)
            _currentWordCount.value = (_currentWordCount.value)?.inc()
            wordList.add(currentWord)
        }
    }

    /*
     * Returns true if the current word count is less than MAX_NO_OF_WORDS.
     * Updates the next word
     */
    fun nextWord(): Boolean {
        return if(currentWordCount.value!! < MAX_NO_OF_WORDS) {
            getNextWord()
            true
        } else false
    }

    private fun increaseScore(){
        _score.value = (_score.value)?.plus(SCORE_INCREASE)
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
        _score.value = 0
        _currentWordCount.value = 0
        wordList.clear()
        getNextWord()
    }
}