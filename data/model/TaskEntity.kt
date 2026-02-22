package com.example.week1kotlinperusteet.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todos")
data class TaskEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val dueDate: String,
    val title: String,
    val description: String,
    val date: Long,
    val isDone: Boolean = false
)