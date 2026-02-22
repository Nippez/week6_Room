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
import com.example.week1kotlinperusteet.ROUTE_HOME
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    navController: NavController,
    viewModel: TaskViewModel
)
{

    val tasks by viewModel.tasks.collectAsState()
    val grouped = tasks.groupBy { it.dueDate }

    var selectedTask by remember { mutableStateOf<TaskEntity?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Calendar") },
                actions = {
                    IconButton(
                        onClick = { navController.navigate(ROUTE_HOME) }
                    )
                    {
                        Icon(Icons.Default.DateRange, contentDescription = "List")
                    }
                }
            )
        }
    )
    { padding ->

        LazyColumn(
            modifier = Modifier.padding(padding)
        )
        {

            grouped.forEach { (date, tasksForDate) ->

                item {
                    Text(
                        text = date,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                items(tasksForDate) { task ->

                    Text(
                        text = task.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedTask = task }
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
            }
        }
//tehtävät vaaiti muokkausta.
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
                }
            )
        }
    }
}