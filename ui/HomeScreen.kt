package com.example.week1kotlinperusteet.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.week1kotlinperusteet.data.model.TaskEntity
import androidx.compose.foundation.clickable
import androidx.navigation.NavController
import com.example.week1kotlinperusteet.viewmodel.TaskViewModel
import com.example.week1kotlinperusteet.ROUTE_CALENDAR
import com.example.week1kotlinperusteet.ROUTE_WEATHER
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange

@OptIn(ExperimentalMaterial3Api::class)
//Kaytan experimental kuten topBar ja scaffold.
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: TaskViewModel,
)
{

    val tasks by viewModel.tasks.collectAsState()

    var newTaskTitle by remember { mutableStateOf("") }
    var selectedTask by remember { mutableStateOf<TaskEntity?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tasks") },
                actions = {
                    IconButton(
                        onClick = { navController.navigate(ROUTE_CALENDAR) }
                    )
                    {
                        Icon(Icons.Default.DateRange, contentDescription = "Calendar")
                    }
                }
            )
        }
    )
    { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        )
        {
            Text("Tehtävälista", style = MaterialTheme.typography.headlineMedium)

            Spacer(Modifier.height(16.dp))

            Row {
                TextField(
                    value = newTaskTitle,
                    onValueChange = { newTaskTitle = it },
                    modifier = Modifier.weight(1f)
                )

                Spacer(Modifier.width(8.dp))

                Button(onClick = {
                    if (newTaskTitle.isNotBlank()) {
                        viewModel.addTask(
                            title = newTaskTitle,
                            description = "",
                            date = System.currentTimeMillis()
                        )
                        newTaskTitle = ""
                    }
                })

                {
                    Text("Lisää")
                }
                Button(
                    onClick = { navController.navigate(ROUTE_WEATHER) }
                ) {
                    Text("Sää")
                }

            }

            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            )
            {
                Button(
                    onClick = { viewModel.filterByDone(true) },
                    modifier = Modifier.weight(1f)
                )
                {
                    Text("Valmiit tehtävät")
                }

                Button(
                    onClick = {viewModel.filterByDone(false) },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Keskeneräiset")
                }

                Button(
                    onClick = { viewModel.showAllTasks() },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Kaikki")
                }
            }

            Spacer(Modifier.height(8.dp))

            Button(
                onClick = { viewModel.sortByDueDate() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Järjestä päivämäärän mukaan")
            }

            LazyColumn {
                items(tasks) { task ->

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedTask = task }
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    )
                    {

                        Row {
                            Checkbox(
                                checked = task.isDone,
                                onCheckedChange = {
                                    viewModel.toggleDone(task)
                                }
                            )

                            Text(task.title)
                        }

                        Text(task.dueDate)
                    }
                    Text(
                        text = task.description,
                    )
                }
            }

            selectedTask?.let { task ->
                DetailDialog(
                    task = task,
                    onDismiss = { selectedTask = null },
                    onSave = {
                        viewModel.updateTask(it)
                        selectedTask = null
                    },
                    onDelete = {
                        viewModel.removeTask(it)
                        selectedTask = null
                    },
                )
            }
        }
    }
}