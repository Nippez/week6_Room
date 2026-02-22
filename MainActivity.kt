package com.example.week1kotlinperusteet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.navigation.compose.*
import com.example.week1kotlinperusteet.ui.HomeScreen
import com.example.week1kotlinperusteet.ui.CalendarScreen
import com.example.week1kotlinperusteet.ui.WeatherScreen
import com.example.week1kotlinperusteet.viewmodel.TaskViewModel
import com.example.week1kotlinperusteet.data.local.AppDatabase
import com.example.week1kotlinperusteet.data.repository.TaskRepository

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val navController = rememberNavController()
            val context = applicationContext

            val viewModel = remember {

                val database = AppDatabase.getDatabase(context)
                val repository = TaskRepository(database.todoDao())

                TaskViewModel(repository)
            }

            NavHost(
                navController = navController,
                startDestination = ROUTE_HOME
            ) {

                composable(ROUTE_HOME) {
                    HomeScreen(navController, viewModel)
                }

                composable(ROUTE_CALENDAR) {
                    CalendarScreen(navController, viewModel)
                }

                composable(ROUTE_WEATHER) {
                    WeatherScreen(navController)
                }
            }
        }
    }
}