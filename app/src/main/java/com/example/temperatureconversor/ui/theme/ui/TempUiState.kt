package com.example.temperatureconversor.ui.theme.ui

data class TempUiState (
    val currentTemperature: String = "",
    val currentTempType: String = " ºF",
    val currentTempTypeOpposite: String = "",
    val switchTemp: Boolean = true
)