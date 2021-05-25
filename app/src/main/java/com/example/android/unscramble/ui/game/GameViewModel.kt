package com.example.android.unscramble.ui.game

import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel (){
    private var score = 0
    private var _currentWordCount = 0
    private var _currentScrambledWord = "test"

    val currentWordCount: Int
        get() = _currentWordCount

    val currentScrambledWord: String
        get() = _currentScrambledWord
}