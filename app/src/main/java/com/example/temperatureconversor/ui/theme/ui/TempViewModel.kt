package com.example.temperatureconversor.ui.theme.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.math.roundToInt

class TempViewModel : ViewModel(){
    var isFahrenheit by mutableStateOf(true)
    private var result by mutableStateOf("")

    private val _uiState = MutableStateFlow(TempUiState())
    val uiState: StateFlow<TempUiState> = _uiState.asStateFlow()

    fun convertTempImput(temp: String) {

        try {
            val tempInt = temp.toInt()

            if (isFahrenheit) {
                result = ((tempInt - 32) * 0.5556).roundToInt().toString()
                _uiState.update { currentState ->
                    currentState.copy(currentTemperature = result)
                }
                _uiState.update { currentState ->
                    currentState.copy(currentTempType = " ºF")
                }
                _uiState.update { currentState ->
                    currentState.copy(currentTempTypeOpposite = " ºC")
                }
            } else {
                result = ((tempInt * 1.8) + 32).roundToInt().toString()
                _uiState.update { currentState ->
                    currentState.copy(currentTemperature = result)
                }
                _uiState.update { currentState ->
                    currentState.copy(currentTempType = " ºC")
                }
                _uiState.update { currentState ->
                    currentState.copy(currentTempTypeOpposite = " ºF")
                }
            }
        } catch (e: Exception) {
            result = "Invalid Entry"
            _uiState.update { currentState ->
                currentState.copy(currentTemperature = result)
            }
        }
    }

    fun switchChange() {
        isFahrenheit = !isFahrenheit
        _uiState.update { currentState ->
            currentState.copy(switchTemp = false)
        }
    }
}