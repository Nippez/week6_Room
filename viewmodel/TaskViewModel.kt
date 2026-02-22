package com.example.week1kotlinperusteet.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.week1kotlinperusteet.data.model.TaskEntity
import com.example.week1kotlinperusteet.data.repository.TaskRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class TaskViewModel(
    private val repository: TaskRepository
    ) : ViewModel() {

    private val allTasks = repository.allTodos
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _tasks = MutableStateFlow<List<TaskEntity>>(emptyList())
    val tasks: StateFlow<List<TaskEntity>> = _tasks

    init {
        viewModelScope.launch {
            allTasks.collect { list ->
                _tasks.value = list
            }
        }
    }
    fun filterByDone(done: Boolean) {
        _tasks.value = allTasks.value.filter { it.isDone == done }
    }
    fun showAllTasks() {
        _tasks.value = allTasks.value
    }
    fun sortByDueDate() {
        _tasks.value = _tasks.value.sortedBy { it.date }
    }
    fun addTask(title: String, description: String, date: Long) {
        viewModelScope.launch {
            repository.insert(
                TaskEntity(
                    title = title,
                    description = description,
                    dueDate = date.toString(),
                    date = date
                )
            )
        }
    }
    fun toggleDone(task: TaskEntity) {
        viewModelScope.launch {
            repository.update(task.copy(isDone = !task.isDone))
        }
    }
    fun removeTask(task: TaskEntity) {
        viewModelScope.launch {
            repository.delete(task)
        }
    }
    fun updateTask(task: TaskEntity) {
        viewModelScope.launch {
            repository.update(task)
        }
    }
}