package com.example.weatherapp2.viewmodel

import androidx.compose.runtime.mutableStateOf
import com.example.weatherapp2.ui.weatherUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class WeatherViewModel {
    private val _uiState = MutableStateFlow(weatherUIState(0f,0f,0f,0f,0,0,0f,"0"))
    val uiState: StateFlow<weatherUIState> = _uiState.asStateFlow()
    private lateinit var currentWord: String
    private var usedWords: MutableSet<String> = mutableSetOf()
    var userLatitude by mutableStateOf(0f)
        private set
    var userLongitude by mutableStateOf(0f)
        private set


    fun updateLatitude(latitude:Float) {
        userLatitude = latitude
    }

    fun updateLongitude(longitude:Float) {
        userLongitude = longitude
    }

    fun fetchWeather() {
        _uiState.value = weatherUIState(0f,0f,0f,0f,0,0,0f,"0")
    }
    init {
        fetchWeather()
    }



    /*private fun updateGameState(updatedScore: Int) {
        if (usedWords.size == MAX_NO_OF_WORDS){
            //Last round in the game, update isGameOver to true, don't pick a new word
            _uiState.update { currentState ->
                currentState.copy(
                    isGuessedWordWrong = false,
                    score = updatedScore,
                    isGameOver = true
                )
            }
        } else{
            // Normal round in the game
            _uiState.update { currentState ->
                currentState.copy(
                    isGuessedWordWrong = false,
                    currentScrambledWord = pickRandomWordAndShuffle(),
                    currentWordCount = currentState.currentWordCount.inc(),
                    score = updatedScore
                )
            }
        }
    }*/

}