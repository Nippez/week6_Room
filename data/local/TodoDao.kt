package com.example.week1kotlinperusteet.data.local

import androidx.room.*
import com.example.week1kotlinperusteet.data.model.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    @Query("SELECT * FROM todos ORDER BY date ASC")
    fun getAllTodos(): Flow<List<TaskEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(todo: TaskEntity)

    @Update
    suspend fun update(todo: TaskEntity)

    @Delete
    suspend fun delete(todo: TaskEntity)
}
