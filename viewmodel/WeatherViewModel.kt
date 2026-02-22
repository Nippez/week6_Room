package com.example.week1kotlinperusteet.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.week1kotlinperusteet.BuildConfig
import com.example.week1kotlinperusteet.data.remote.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class WeatherUiState(
    val city: String = "",
    val temperature: String = "",
    val description: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)

class WeatherViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(WeatherUiState())
    val uiState: StateFlow<WeatherUiState> = _uiState

    fun updateCity(city: String) {
        _uiState.value = _uiState.value.copy(city = city)
    }
    fun fetchWeather() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            try {
                val response = RetrofitInstance.api.getWeather(
                    city = _uiState.value.city,
                    apiKey = BuildConfig.WEATHER_API_KEY
                )
                _uiState.value = _uiState.value.copy(
                    temperature = "${response.main.temp} °C",
                    description = response.weather.firstOrNull()?.description ?: "",
                    isLoading = false
                )

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Virhe hakemisesa"
                )
            }
        }
    }
}