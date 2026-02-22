package com.example.week1kotlinperusteet.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.week1kotlinperusteet.viewmodel.WeatherViewModel

@Composable
fun WeatherScreen(
    navController: NavController,
    vm: WeatherViewModel = viewModel()
)
{

    val state by vm.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    )
    {

        Button(onClick = { navController.popBackStack() }) {
            Text("Takaisin")
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = state.city,
            onValueChange = { vm.updateCity(it) },
            label = { Text("Kaupunki") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { vm.fetchWeather() }) {
            Text("Hae sää")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (state.isLoading) {
            CircularProgressIndicator()
        }
        state.error?.let {
            Text(it, color = MaterialTheme.colorScheme.error)
        }

        if (state.temperature.isNotEmpty()) {
            Text("Lämpötila: ${state.temperature}")
            Text("Kuvaus: ${state.description}")
        }
    }
}