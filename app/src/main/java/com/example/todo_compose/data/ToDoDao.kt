package com.example.todo_compose.data

import androidx.room.*
import com.example.todo_compose.data.model.ToDoTask
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDao {
    @Query("SELECT * FROM todo_table ORDER BY id ASC")
    fun getAllTask(): Flow<List<ToDoTask>>

    @Query("SELECT * FROM todo_table WHERE id=:id")
    fun getSelectedTask(id: Int): Flow<ToDoTask>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(toDoTask: ToDoTask)

    @Update
    suspend fun updateTask(toDoTask: ToDoTask)

    @Delete
    suspend fun deleteTask(toDoTask: ToDoTask)

    @Query("DELETE FROM todo_table")
    suspend fun deleteAllTask()

    @Query("SELECT * FROM todo_table WHERE title LIKE '%' || :query || '%' or description LIKE '%' || :query || '%'")
    fun searchTask(query: String): Flow<List<ToDoTask>>

    @Query("SELECT * FROM todo_table ORDER BY CASE WHEN priority LIKE 'L%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'H%' THEN 3 END")
    fun sortByLowestPriority(): Flow<List<ToDoTask>>

    @Query("SELECT * FROM todo_table ORDER BY CASE WHEN priority LIKE 'H%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'L%' THEN 3 END")
    fun sortByHighestPriority(): Flow<List<ToDoTask>>
}