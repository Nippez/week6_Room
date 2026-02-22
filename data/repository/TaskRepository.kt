package com.example.week1kotlinperusteet.data.repository

import com.example.week1kotlinperusteet.data.local.TodoDao
import com.example.week1kotlinperusteet.data.model.TaskEntity
import kotlinx.coroutines.flow.Flow

class TaskRepository(
    private val dao: TodoDao
) {

    val allTodos: Flow<List<TaskEntity>> = dao.getAllTodos()

    suspend fun insert(todo: TaskEntity) {
        dao.insert(todo)
    }

    suspend fun update(todo: TaskEntity) {
        dao.update(todo)
    }

    suspend fun delete(todo: TaskEntity) {
        dao.delete(todo)
    }
}
